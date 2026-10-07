package com.example.muse.data.auth

import com.google.gson.annotations.SerializedName
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

/** Параметры подключения к AWS Cognito (регион и пул из вывода sam deploy). */
object CognitoConfig {
    const val REGION = "eu-north-1"
    const val CLIENT_ID = "4ohmhl7bop28b9fn4f0g8otj0b"
    const val BASE_URL = "https://cognito-idp.$REGION.amazonaws.com/"
}

// ---------- Модели запросов/ответов Cognito ----------

data class UserAttribute(
    @SerializedName("Name") val name: String,
    @SerializedName("Value") val value: String
)

data class SignUpRequest(
    @SerializedName("ClientId") val clientId: String,
    @SerializedName("Username") val username: String,
    @SerializedName("Password") val password: String,
    @SerializedName("UserAttributes") val userAttributes: List<UserAttribute>
)

data class SignUpResponse(
    @SerializedName("UserSub") val userSub: String?,
    @SerializedName("UserConfirmed") val userConfirmed: Boolean?
)

data class ConfirmSignUpRequest(
    @SerializedName("ClientId") val clientId: String,
    @SerializedName("Username") val username: String,
    @SerializedName("ConfirmationCode") val confirmationCode: String
)

data class InitiateAuthRequest(
    @SerializedName("AuthFlow") val authFlow: String,
    @SerializedName("ClientId") val clientId: String,
    @SerializedName("AuthParameters") val authParameters: Map<String, String>
)

data class AuthTokens(
    @SerializedName("IdToken") val idToken: String,
    @SerializedName("AccessToken") val accessToken: String,
    @SerializedName("RefreshToken") val refreshToken: String?,
    @SerializedName("ExpiresIn") val expiresIn: Int
)

data class InitiateAuthResponse(
    @SerializedName("AuthenticationResult") val authenticationResult: AuthTokens?
)

data class CognitoError(
    @SerializedName("__type") val type: String?,
    @SerializedName("message") val message: String?
)

/**
 * Прямые вызовы публичного API Cognito (без AWS SDK).
 * SignUp/ConfirmSignUp/InitiateAuth не требуют подписи запроса —
 * достаточно ClientId, поэтому подключаемся обычным Retrofit.
 */
interface CognitoAuthApi {

    @Headers(
        "Content-Type: application/x-amz-json-1.1",
        "X-Amz-Target: AWSCognitoIdentityProviderService.SignUp"
    )
    @POST("/")
    suspend fun signUp(@Body body: SignUpRequest): SignUpResponse

    @Headers(
        "Content-Type: application/x-amz-json-1.1",
        "X-Amz-Target: AWSCognitoIdentityProviderService.ConfirmSignUp"
    )
    @POST("/")
    suspend fun confirmSignUp(@Body body: ConfirmSignUpRequest)

    @Headers(
        "Content-Type: application/x-amz-json-1.1",
        "X-Amz-Target: AWSCognitoIdentityProviderService.InitiateAuth"
    )
    @POST("/")
    suspend fun initiateAuth(@Body body: InitiateAuthRequest): InitiateAuthResponse
}
