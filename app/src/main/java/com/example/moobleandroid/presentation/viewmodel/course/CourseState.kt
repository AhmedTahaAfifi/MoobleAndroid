package com.example.moobleandroid.presentation.viewmodel.course

import com.example.moobleandroid.data.api.CourseResponse

data class CourseState(
    val courses: List<CourseResponse> = emptyList(),
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val token: String = ""
)
