package com.example.moobleandroid.data.repository

import com.example.moobleandroid.data.api.MoodleApiService
import com.example.moobleandroid.data.api.TokenResponse
import com.example.moobleandroid.data.api.UserInfoResponse
import com.example.moobleandroid.data.local.UserDao
import com.example.moobleandroid.data.local.UserEntity

class AuthRepository(
    private val apiService: MoodleApiService,
    private val userDao: UserDao
) {

    fun login(
        userName: String,
        password: String,
        onSuccess: (TokenResponse) -> Unit,
        onFail: () -> Unit
    ) {
        apiService.login(userName, password).enqueue(AppRetrofitRequest.getResponse({
            onSuccess(it)
        }, { _, _ ->
            onFail()
        }))
    }

    fun getUserInfo(
        token: String,
        onSuccess: (UserInfoResponse) -> Unit,
        onFail: () -> Unit
    ) {
        apiService.getUserInfo(token).enqueue(AppRetrofitRequest.getResponse({
            onSuccess(it)
        }, {_, _ ->
            onFail()
        }))
    }

    suspend fun saveUserInfo(userInfo: UserInfoResponse, token: String) {
        val userEntity = UserEntity(
            userId = userInfo.userId,
            token = token
        )

        userDao.insertUser(userEntity)
    }

    suspend fun getCurrentUser(): UserEntity? = userDao.getUser()
    suspend fun logout() = userDao.clearUser()
}