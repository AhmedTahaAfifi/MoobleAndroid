package com.example.moobleandroid.presentation.viewmodel.grades

import com.example.moobleandroid.data.api.GradeItem

data class GradeState(
    val grades: List<GradeUiModel> = emptyList(),
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)

data class GradeUiModel(
    val courseId: Int,
    val courseName: String,
    val grade: String,
    val percentage: Float
)

sealed interface GradeEffect {
    object NavigateBack : GradeEffect
}
