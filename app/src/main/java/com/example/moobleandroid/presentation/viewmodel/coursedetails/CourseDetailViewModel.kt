package com.example.moobleandroid.presentation.viewmodel.coursedetails

import androidx.lifecycle.viewModelScope
import com.example.moobleandroid.data.repository.AuthRepository
import com.example.moobleandroid.data.repository.CourseRepository
import com.example.moobleandroid.presentation.viewmodel.BaseViewModel
import kotlinx.coroutines.launch

class CourseDetailViewModel(
    private val authRepository: AuthRepository,
    private val courseRepository: CourseRepository
) : BaseViewModel<CourseDetailState, CourseDetailEffect>(CourseDetailState()) {

    fun init(courseId: Int, courseName: String, courseImage: String?) {
        updateState { 
            it.copy(
                courseName = courseName, 
                courseImage = courseImage,
                isLoading = true 
            ) 
        }
        loadCourseContents(courseId)
    }

    private fun loadCourseContents(courseId: Int) {
        viewModelScope.launch {
            val user = authRepository.getCurrentUser()
            if (user != null) {
                updateState { it.copy(token = user.token) }
                courseRepository.getCourseContents(
                    token = user.token,
                    courseId = courseId,
                    onSuccess = { contents ->
                        updateState { it.copy(isLoading = false, sections = contents) }
                    },
                    onFail = {
                        updateState { it.copy(isLoading = false, errorMessage = "Failed to load content") }
                    },
                    onFailure = {
                        updateState { it.copy(isLoading = false, errorMessage = "Something went wrong!") }
                    }
                )
            } else {
                updateState { it.copy(isLoading = false, errorMessage = "Session expired") }
            }
        }
    }

    fun onBackClick() {
        sendEffect(CourseDetailEffect.NavigateBack)
    }
}
