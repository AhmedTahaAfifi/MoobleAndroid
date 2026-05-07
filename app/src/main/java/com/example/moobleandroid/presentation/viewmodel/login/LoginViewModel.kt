package com.example.moobleandroid.presentation.viewmodel.login

import androidx.lifecycle.viewModelScope
import com.example.moobleandroid.data.repository.AuthRepository
import com.example.moobleandroid.presentation.viewmodel.BaseViewModel
import kotlinx.coroutines.launch

class LoginViewModel(private val repository: AuthRepository):
    BaseViewModel<LoginState, LoginEffect>(LoginState()) {

    fun onUserNameChange(username: String) {
        updateState { it.copy(username = username) }
    }

    fun onPasswordChange(password: String) {
        updateState { it.copy(password = password) }
    }

    fun onLoginClick() {
        val currentState = viewState.value
        if (currentState.username.isBlank() || currentState.password.isBlank()) {
            updateState { it.copy(errorMessage = "Please enter credentials") }
            return
        }

        updateState { it.copy(isLoading = true, errorMessage = null) }

        repository.login(currentState.username, currentState.password,
            onSuccess = { tokenResponse ->
                if (tokenResponse.token != null) {
                    fetchUserInfo(tokenResponse.token)
                } else {
                    updateState {
                        it.copy(
                            isLoading = false,
                            errorMessage = tokenResponse.error ?: "Login failed"
                        )
                    }
                }
            },
            onFail = {
                updateState {
                    it.copy(
                        isLoading = false,
                        errorMessage = "Invalid credentials or network error"
                    )
                }
            }
        )
    }

    private fun fetchUserInfo(token: String) {
        repository.getUserInfo(token,
            onSuccess = { userInfo ->
                viewModelScope.launch {
                    repository.saveUserInfo(userInfo, token)
                    updateState { it.copy(isLoading = false) }
                    sendEffect(LoginEffect.NavigateToHome)
                }
            },
            onFail = {
                updateState {
                    it.copy(
                        isLoading = false,
                        errorMessage = "Failed to fetch user profile"
                    )
                }
            }
        )
    }

}