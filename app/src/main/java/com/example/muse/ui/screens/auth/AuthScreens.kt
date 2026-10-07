package com.example.muse.ui.screens.auth

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ArrowBack
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.muse.ui.auth.AuthViewModel
import com.example.muse.ui.theme.MuseBackground
import com.example.muse.ui.theme.MuseBlue
import com.example.muse.ui.theme.MuseTextPrimary
import com.example.muse.ui.theme.MuseTextSecondary

/** Общий каркас экранов авторизации: фон, кнопка назад, заголовок. */
@Composable
private fun AuthScaffold(
    title: String,
    subtitle: String,
    onBack: () -> Unit,
    content: @Composable () -> Unit
) {
    Scaffold { padding ->
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
            color = MuseBackground
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 24.dp)
            ) {
                Spacer(modifier = Modifier.height(12.dp))
                IconButton(onClick = onBack) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Outlined.ArrowBack,
                        contentDescription = "Назад",
                        tint = MuseTextPrimary
                    )
                }
                Spacer(modifier = Modifier.height(24.dp))
                Text(
                    text = title,
                    color = MuseTextPrimary,
                    fontSize = 26.sp,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(6.dp))
                Text(text = subtitle, color = MuseTextSecondary, fontSize = 15.sp)
                Spacer(modifier = Modifier.height(28.dp))
                content()
            }
        }
    }
}

@Composable
private fun AuthTextField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    icon: ImageVector,
    isPassword: Boolean = false,
    keyboardType: KeyboardType = KeyboardType.Text
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(label) },
        leadingIcon = { Icon(icon, contentDescription = null, tint = MuseTextSecondary) },
        singleLine = true,
        visualTransformation =
            if (isPassword) PasswordVisualTransformation() else VisualTransformation.None,
        keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
        shape = RoundedCornerShape(14.dp),
        modifier = Modifier.fillMaxWidth()
    )
}

@Composable
private fun AuthButton(text: String, enabled: Boolean, isLoading: Boolean, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        enabled = enabled && !isLoading,
        shape = RoundedCornerShape(14.dp),
        colors = ButtonDefaults.buttonColors(containerColor = MuseBlue),
        modifier = Modifier
            .fillMaxWidth()
            .height(52.dp)
    ) {
        if (isLoading) {
            CircularProgressIndicator(
                color = Color.White,
                modifier = Modifier.size(22.dp),
                strokeWidth = 2.dp
            )
        } else {
            Text(text, fontSize = 16.sp)
        }
    }
}

@Composable
private fun ErrorText(error: String?) {
    if (error != null) {
        Spacer(modifier = Modifier.height(12.dp))
        Text(
            text = error,
            color = Color(0xFFDC2626),
            fontSize = 14.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )
    }
}

// ---------- Вход ----------

@Composable
fun LoginScreen(
    onBack: () -> Unit = {},
    onRegisterClick: () -> Unit = {},
    onSuccess: () -> Unit = {},
    authViewModel: AuthViewModel = viewModel(
        factory = AuthViewModel.factory(LocalContext.current.applicationContext)
    )
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    val isLoading by authViewModel.isLoading.collectAsStateWithLifecycle()
    val error by authViewModel.error.collectAsStateWithLifecycle()

    AuthScaffold(
        title = "Вход",
        subtitle = "Войдите, чтобы синхронизировать прогресс",
        onBack = onBack
    ) {
        AuthTextField(email, { email = it }, "Email", Icons.Outlined.Email,
            keyboardType = KeyboardType.Email)
        Spacer(modifier = Modifier.height(14.dp))
        AuthTextField(password, { password = it }, "Пароль", Icons.Outlined.Lock,
            isPassword = true)
        Spacer(modifier = Modifier.height(24.dp))
        AuthButton(
            text = "Войти",
            enabled = email.isNotBlank() && password.isNotBlank(),
            isLoading = isLoading,
            onClick = { authViewModel.signIn(email, password, onSuccess) }
        )
        ErrorText(error)
        Spacer(modifier = Modifier.height(16.dp))
        Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
            TextButton(onClick = onRegisterClick) {
                Text("Нет аккаунта? Зарегистрироваться", color = MuseBlue)
            }
        }
    }
}

// ---------- Регистрация (2 шага: форма → код из письма) ----------

@Composable
fun RegisterScreen(
    onBack: () -> Unit = {},
    onSuccess: () -> Unit = {},
    authViewModel: AuthViewModel = viewModel(
        factory = AuthViewModel.factory(LocalContext.current.applicationContext)
    )
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var code by remember { mutableStateOf("") }
    var codeSent by remember { mutableStateOf(false) }
    val isLoading by authViewModel.isLoading.collectAsStateWithLifecycle()
    val error by authViewModel.error.collectAsStateWithLifecycle()

    if (!codeSent) {
        AuthScaffold(
            title = "Регистрация",
            subtitle = "Минимум 8 символов, строчная буква и цифра",
            onBack = onBack
        ) {
            AuthTextField(email, { email = it }, "Email", Icons.Outlined.Email,
                keyboardType = KeyboardType.Email)
            Spacer(modifier = Modifier.height(14.dp))
            AuthTextField(password, { password = it }, "Пароль", Icons.Outlined.Lock,
                isPassword = true)
            Spacer(modifier = Modifier.height(24.dp))
            AuthButton(
                text = "Создать аккаунт",
                enabled = email.contains("@") && password.length >= 8,
                isLoading = isLoading,
                onClick = { authViewModel.signUp(email, password) { codeSent = true } }
            )
            ErrorText(error)
        }
    } else {
        AuthScaffold(
            title = "Код из письма",
            subtitle = "Мы отправили 6-значный код на $email",
            onBack = onBack
        ) {
            AuthTextField(code, { code = it }, "Код подтверждения", Icons.Outlined.Lock,
                keyboardType = KeyboardType.Number)
            Spacer(modifier = Modifier.height(24.dp))
            AuthButton(
                text = "Подтвердить и войти",
                enabled = code.length == 6,
                isLoading = isLoading,
                onClick = { authViewModel.confirmAndSignIn(email, code, password, onSuccess) }
            )
            ErrorText(error)
        }
    }
}

// ---------- Профиль ----------

@Composable
fun ProfileScreen(
    onBack: () -> Unit = {},
    onLoginClick: () -> Unit = {},
    onRegisterClick: () -> Unit = {},
    authViewModel: AuthViewModel = viewModel(
        factory = AuthViewModel.factory(LocalContext.current.applicationContext)
    )
) {
    val email by authViewModel.email.collectAsStateWithLifecycle()

    AuthScaffold(
        title = "Профиль",
        subtitle = if (email != null) "Ваш аккаунт Muse" else "Войдите, чтобы синхронизировать прогресс",
        onBack = onBack
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top,
            modifier = Modifier.fillMaxWidth()
        ) {
            Box(
                modifier = Modifier
                    .size(88.dp)
                    .clip(CircleShape)
                    .background(MuseBlue),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Outlined.Person,
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier.size(44.dp)
                )
            }
            Spacer(modifier = Modifier.height(16.dp))

            if (email != null) {
                Text(
                    text = email!!,
                    color = MuseTextPrimary,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Medium
                )
                Spacer(modifier = Modifier.height(32.dp))
                AuthButton(
                    text = "Выйти",
                    enabled = true,
                    isLoading = false,
                    onClick = { authViewModel.signOut() }
                )
            } else {
                Text(
                    text = "Вы не вошли в аккаунт",
                    color = MuseTextSecondary,
                    fontSize = 16.sp
                )
                Spacer(modifier = Modifier.height(32.dp))
                AuthButton(
                    text = "Войти",
                    enabled = true,
                    isLoading = false,
                    onClick = onLoginClick
                )
                Spacer(modifier = Modifier.height(12.dp))
                TextButton(onClick = onRegisterClick) {
                    Text("Создать аккаунт", color = MuseBlue)
                }
            }
        }
    }
}
