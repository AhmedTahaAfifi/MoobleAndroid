package com.example.moobleandroid.presentation.viewmodel.coursedetails

import com.example.moobleandroid.data.api.CourseContentResponse

data class CourseDetailState(
    val courseName: String = "",
    val courseImage: String? = null,
    val sections: List<CourseContentResponse> = emptyList(),
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val token: String = ""
)
