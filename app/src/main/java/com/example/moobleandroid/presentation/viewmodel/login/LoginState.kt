package com.example.moobleandroid.presentation.viewmodel.login

data class LoginState(
    val username: String = "student1",
    val password: String = "Demo@12345",
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)