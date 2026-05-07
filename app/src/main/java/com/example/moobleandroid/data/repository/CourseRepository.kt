package com.example.moobleandroid.data.repository

import com.example.moobleandroid.data.api.CourseResponse
import com.example.moobleandroid.data.api.CourseContentResponse
import com.example.moobleandroid.data.api.MoodleApiService

class CourseRepository(private val apiService: MoodleApiService) {

    fun getEnrolledCourses(
        token: String,
        userId: Int,
        onSuccess: (List<CourseResponse>) -> Unit,
        onFail: () -> Unit,
        onFailure: () -> Unit
    ) {
        apiService.getEnrolledCourses(token, userId).enqueue(AppRetrofitRequest.getResponse({
            onSuccess(it)
        }, { _, _ ->
            onFail()
        }, { _, _ ->
            onFailure()
        }))
    }

    fun getCourseContents(
        token: String,
        courseId: Int,
        onSuccess: (List<CourseContentResponse>) -> Unit,
        onFail: () -> Unit,
        onFailure: () -> Unit
    ) {
        apiService.getCourseContents(token, courseId).enqueue(AppRetrofitRequest.getResponse({
            onSuccess(it)
        }, { _, _ ->
            onFail()
        }, { _, _ ->
            onFailure()
        }))
    }

}