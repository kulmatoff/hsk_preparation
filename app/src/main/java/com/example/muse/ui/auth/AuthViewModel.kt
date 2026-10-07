package com.example.muse.ui.auth

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.muse.data.auth.AuthException
import com.example.muse.data.auth.AuthRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

/**
 * Состояние авторизации. Экземпляр создаётся на каждом экране,
 * но все они читают одну и ту же SharedPreferences-сессию.
 */
class AuthViewModel(private val repository: AuthRepository) : ViewModel() {

    /** Email вошедшего пользователя или null. */
    private val _email = MutableStateFlow(repository.email)
    val email: StateFlow<String?> = _email

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    fun clearError() {
        _error.value = null
    }

    fun signIn(email: String, password: String, onSuccess: () -> Unit) {
        run({
            repository.signIn(email.trim(), password)
            _email.value = repository.email
        }, onSuccess)
    }

    /** Регистрация. onNeedsConfirmation вызывается, когда письмо с кодом отправлено. */
    fun signUp(email: String, password: String, onNeedsConfirmation: () -> Unit) {
        run({
            repository.signUp(email.trim(), password)
        }, onNeedsConfirmation)
    }

    /** Подтверждение кода + автоматический вход. */
    fun confirmAndSignIn(email: String, code: String, password: String, onSuccess: () -> Unit) {
        run({
            repository.confirm(email.trim(), code.trim())
            repository.signIn(email.trim(), password)
            _email.value = repository.email
        }, onSuccess)
    }

    fun signOut() {
        repository.signOut()
        _email.value = null
    }

    private fun run(action: suspend () -> Unit, onSuccess: () -> Unit) {
        viewModelScope.launch {
            _isLoading.value = true
            _error.value = null
            try {
                action()
                onSuccess()
            } catch (e: AuthException) {
                _error.value = e.message
            } catch (e: Exception) {
                _error.value = "Что-то пошло не так. Попробуйте ещё раз"
            } finally {
                _isLoading.value = false
            }
        }
    }

    companion object {
        fun factory(context: Context): ViewModelProvider.Factory = viewModelFactory {
            initializer {
                AuthViewModel(AuthRepository(context.applicationContext))
            }
        }
    }
}
