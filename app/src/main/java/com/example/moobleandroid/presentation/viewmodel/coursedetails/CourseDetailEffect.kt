package com.example.moobleandroid.presentation.viewmodel.coursedetails

sealed interface CourseDetailEffect {
    object NavigateBack : CourseDetailEffect
}