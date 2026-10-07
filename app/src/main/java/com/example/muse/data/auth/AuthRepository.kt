package com.example.muse.data.auth

import android.content.Context
import android.util.Base64
import com.google.gson.Gson
import org.json.JSONObject
import retrofit2.HttpException
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/** Ошибка авторизации с уже переведённым на русский сообщением. */
class AuthException(message: String) : Exception(message)

/**
 * Регистрация, вход и хранение сессии пользователя.
 * Токены Cognito сохраняются в SharedPreferences.
 */
class AuthRepository(context: Context) {

    private val prefs = context.getSharedPreferences("muse_auth", Context.MODE_PRIVATE)
    private val gson = Gson()

    private val api: CognitoAuthApi = Retrofit.Builder()
        .baseUrl(CognitoConfig.BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(CognitoAuthApi::class.java)

    val isLoggedIn: Boolean
        get() = prefs.getString(KEY_ID_TOKEN, null) != null

    /** Email текущего пользователя (из сохранённой сессии). */
    val email: String?
        get() = prefs.getString(KEY_EMAIL, null)

    /** Регистрация: Cognito отправит код подтверждения на email. */
    suspend fun signUp(email: String, password: String) = callCognito {
        api.signUp(
            SignUpRequest(
                clientId = CognitoConfig.CLIENT_ID,
                username = email,
                password = password,
                userAttributes = listOf(UserAttribute("email", email))
            )
        )
    }

    /** Подтверждение аккаунта кодом из письма. */
    suspend fun confirm(email: String, code: String) = callCognito {
        api.confirmSignUp(
            ConfirmSignUpRequest(
                clientId = CognitoConfig.CLIENT_ID,
                username = email,
                confirmationCode = code
            )
        )
    }

    /** Вход: при успехе токены и email сохраняются локально. */
    suspend fun signIn(email: String, password: String) = callCognito {
        val response = api.initiateAuth(
            InitiateAuthRequest(
                authFlow = "USER_PASSWORD_AUTH",
                clientId = CognitoConfig.CLIENT_ID,
                authParameters = mapOf("USERNAME" to email, "PASSWORD" to password)
            )
        )
        val tokens = response.authenticationResult
            ?: throw AuthException("Пустой ответ сервера авторизации")
        prefs.edit()
            .putString(KEY_ID_TOKEN, tokens.idToken)
            .putString(KEY_ACCESS_TOKEN, tokens.accessToken)
            .putString(KEY_REFRESH_TOKEN, tokens.refreshToken)
            .putString(KEY_EMAIL, emailFromJwt(tokens.idToken) ?: email)
            .apply()
    }

    fun signOut() {
        prefs.edit().clear().apply()
    }

    /** Токен для будущих запросов к защищённым эндпоинтам API. */
    val idToken: String?
        get() = prefs.getString(KEY_ID_TOKEN, null)

    private fun emailFromJwt(jwt: String): String? = runCatching {
        val payload = jwt.split(".").getOrNull(1) ?: return null
        val decoded = String(Base64.decode(payload, Base64.URL_SAFE or Base64.NO_WRAP))
        JSONObject(decoded).optString("email").takeIf { it.isNotBlank() }
    }.getOrNull()

    /** Оборачивает вызов Cognito и переводит типовые ошибки на русский. */
    private suspend fun <T> callCognito(block: suspend () -> T): T {
        try {
            return block()
        } catch (e: HttpException) {
            val error = runCatching {
                gson.fromJson(e.response()?.errorBody()?.string(), CognitoError::class.java)
            }.getOrNull()
            throw AuthException(russianMessage(error?.type, error?.message))
        } catch (e: AuthException) {
            throw e
        } catch (e: Exception) {
            throw AuthException("Нет соединения с сервером. Проверьте интернет")
        }
    }

    private fun russianMessage(type: String?, original: String?): String {
        val shortType = type?.substringBefore(',')
        return when (shortType) {
            "UsernameExistsException" -> "Аккаунт с таким email уже существует"
            "InvalidPasswordException" ->
                "Пароль слишком простой: минимум 8 символов, нужны строчная буква и цифра"
            "InvalidParameterException" -> original ?: "Проверьте правильность введённых данных"
            "CodeMismatchException" -> "Неверный код подтверждения"
            "ExpiredCodeException" -> "Код устарел — зарегистрируйтесь ещё раз"
            "NotAuthorizedException" -> "Неверный email или пароль"
            "UserNotFoundException" -> "Пользователь с таким email не найден"
            "UserNotConfirmedException" -> "Email не подтверждён — введите код из письма"
            "TooManyRequestsException" -> "Слишком много попыток, подождите минуту"
            else -> original ?: "Ошибка авторизации"
        }
    }

    private companion object {
        const val KEY_ID_TOKEN = "id_token"
        const val KEY_ACCESS_TOKEN = "access_token"
        const val KEY_REFRESH_TOKEN = "refresh_token"
        const val KEY_EMAIL = "email"
    }
}
