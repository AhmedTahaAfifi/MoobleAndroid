package com.example.moobleandroid.presentation.viewmodel.course

sealed class CourseEffect {
    data class NavigateToDetails(val courseId: Int) : CourseEffect()
}