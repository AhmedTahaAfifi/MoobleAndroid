package com.example.moobleandroid.presentation.ui.navigation

import kotlinx.serialization.Serializable

@Serializable
sealed interface Route {
    @Serializable
    data object Login : Route
    
    @Serializable
    data object CourseList : Route
    
    @Serializable
    data class CourseDetails(
        val courseId: Int,
        val courseName: String,
        val courseImage: String?
    ) : Route

    @Serializable
    data object Grades : Route
}
