package com.example.moobleandroid.data.repository

import com.example.moobleandroid.data.api.GradeReportResponse
import com.example.moobleandroid.data.api.MoodleApiService
import retrofit2.Call

class GradeRepository(private val apiService: MoodleApiService) {

    fun getUserGrades(
        token: String,
        userId: Int,
        onSuccess: (GradeReportResponse) -> Unit,
        onFail: () -> Unit,
        onFailure: () -> Unit
    ) {
        apiService.getUserGrades(token, userId).enqueue(AppRetrofitRequest.getResponse({
            onSuccess(it)
        }, { _, _ ->
            onFail()
        }, { _, _ ->
            onFailure()
        }))
    }
}
