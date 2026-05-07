package com.example.moobleandroid.presentation.viewmodel.grades

import androidx.lifecycle.viewModelScope
import com.example.moobleandroid.data.api.CourseResponse
import com.example.moobleandroid.data.api.GradeItem
import com.example.moobleandroid.data.repository.AuthRepository
import com.example.moobleandroid.data.repository.CourseRepository
import com.example.moobleandroid.data.repository.GradeRepository
import com.example.moobleandroid.presentation.viewmodel.BaseViewModel
import kotlinx.coroutines.launch

class GradeViewModel(
    private val authRepository: AuthRepository,
    private val courseRepository: CourseRepository,
    private val gradeRepository: GradeRepository
) : BaseViewModel<GradeState, GradeEffect>(GradeState()) {

    init {
        loadGrades()
    }

    fun loadGrades() {
        updateState { it.copy(isLoading = true, errorMessage = null) }

        viewModelScope.launch {
            val user = authRepository.getCurrentUser()
            if (user != null) {
                courseRepository.getEnrolledCourses(
                    token = user.token,
                    userId = user.userId,
                    onSuccess = { courses ->
                        fetchGrades(user.token, user.userId, courses)
                    },
                    onFail = {
                        updateState { it.copy(isLoading = false, errorMessage = "Failed to load courses") }
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

    private fun fetchGrades(token: String, userId: Int, courses: List<CourseResponse>?) {
        gradeRepository.getUserGrades(token, userId,
            onSuccess = { response ->
                val uiModels = response.userGrades?.mapNotNull { userGrade ->
                    val course = courses?.find { it.id == userGrade.courseId }
                    val overallGradeItem = userGrade.gradeItems?.find { it.itemType == "course" }
                    
                    if (overallGradeItem != null) {
                        GradeUiModel(
                            courseId = userGrade.courseId,
                            courseName = course?.fullName ?: "Course ${userGrade.courseId}",
                            grade = overallGradeItem.gradeFormatted ?: "-",
                            percentage = calculatePercentage(overallGradeItem)
                        )
                    } else {
                        null
                    }
                } ?: emptyList()
                updateState { it.copy(isLoading = false, grades = uiModels) }
            },
            onFail = {
                updateState { it.copy(isLoading = false, errorMessage = "Failed to load grades") }
            },
            onFailure = {
                updateState { it.copy(isLoading = false, errorMessage = "Something went wrong!") }
            }
        )
    }

    private fun calculatePercentage(item: GradeItem): Float {
        val raw = item.rawGrade ?: return 0f
        val max = item.gradeMax ?: return 0f
        if (max == 0.0) return 0f
        return (raw / max).toFloat()
    }

    fun onBackClick() {
        sendEffect(GradeEffect.NavigateBack)
    }
}
