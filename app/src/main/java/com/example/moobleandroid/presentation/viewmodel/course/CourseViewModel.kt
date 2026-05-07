package com.example.moobleandroid.presentation.viewmodel.course

import androidx.lifecycle.viewModelScope
import com.example.moobleandroid.data.repository.AuthRepository
import com.example.moobleandroid.data.repository.CourseRepository
import com.example.moobleandroid.presentation.viewmodel.BaseViewModel
import kotlinx.coroutines.launch

class CourseViewModel(
    private val authRepository: AuthRepository,
    private val courseRepository: CourseRepository
) : BaseViewModel<CourseState, CourseEffect>(CourseState()) {

    init {
        loadCourses()
    }

    fun loadCourses() {
        updateState { it.copy(isLoading = true, errorMessage = null) }

        viewModelScope.launch {
            val user = authRepository.getCurrentUser()
            if (user != null) {
                courseRepository.getEnrolledCourses(
                    token = user.token,
                    userId = user.userId,
                    onSuccess = { courses ->
                        updateState { it.copy(isLoading = false, courses = courses, token = user.token) }
                    },
                    onFail = {
                        updateState { it.copy(isLoading = false, errorMessage = "Failed to load courses") }
                    },
                    onFailure = {
                        updateState { it.copy(isLoading = false, errorMessage = "Something went wrong!") }
                    }
                )
            } else {
                updateState { it.copy(isLoading = false, errorMessage = "User session not found") }
            }
        }
    }

}