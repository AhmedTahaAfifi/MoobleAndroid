package com.example.moobleandroid.presentation.ui.screens.course

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BarChart
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.moobleandroid.presentation.ui.components.CourseItem
import com.example.moobleandroid.presentation.ui.components.ErrorView
import com.example.moobleandroid.presentation.viewmodel.course.CourseViewModel
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CoursesScreen(
    viewModel: CourseViewModel = koinViewModel(),
    onCourseClick: (Int, String, String?) -> Unit,
    onGradesClick: () -> Unit
) {
    val state by viewModel.viewState.collectAsStateWithLifecycle()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("My Courses") },
                actions = {
                    IconButton(onClick = onGradesClick) {
                        Icon(Icons.Default.BarChart, contentDescription = "Grades")
                    }
                }
            )
        }
    ) { padding ->
        Box(modifier = Modifier
            .fillMaxSize()
            .padding(padding)) {
            if (state.isLoading) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            } else if (state.errorMessage != null) {
                ErrorView(
                    message = state.errorMessage!!,
                    onRetry = { viewModel.loadCourses() },
                    modifier = Modifier.align(Alignment.Center)
                )
            } else {
                LazyColumn(modifier = Modifier.fillMaxSize()) {
                    items(state.courses) { course ->
                        CourseItem(
                            course = course,
                            token = state.token,
                            onClick = { onCourseClick(course.id, course.fullName, course.courseImage) }
                        )
                        HorizontalDivider()
                    }
                }
            }
        }
    }
}
