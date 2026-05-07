package com.example.moobleandroid.presentation.ui.screens.grades

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.moobleandroid.presentation.ui.components.ErrorView
import com.example.moobleandroid.presentation.viewmodel.grades.GradeEffect
import com.example.moobleandroid.presentation.viewmodel.grades.GradeUiModel
import com.example.moobleandroid.presentation.viewmodel.grades.GradeViewModel
import ir.kaaveh.sdpcompose.sdp
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GradesScreen(
    viewModel: GradeViewModel = koinViewModel(),
    onBackClick: () -> Unit
) {
    val state by viewModel.viewState.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.viewEffect.collect { effect ->
            when (effect) {
                is GradeEffect.NavigateBack -> onBackClick()
            }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("My Grades") },
                navigationIcon = {
                    IconButton(onClick = { viewModel.onBackClick() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            if (state.isLoading) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            } else if (state.errorMessage != null) {
                ErrorView(
                    message = state.errorMessage!!,
                    onRetry = { viewModel.loadGrades() },
                    modifier = Modifier.align(Alignment.Center)
                )
            } else if (state.grades.isEmpty()) {
                Text(
                    text = "No grades found.",
                    modifier = Modifier.align(Alignment.Center)
                )
            } else {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(16.sdp),
                    verticalArrangement = Arrangement.spacedBy(12.sdp)
                ) {
                    items(state.grades) { grade ->
                        GradeCard(grade)
                    }
                }
            }
        }
    }
}

@Composable
fun GradeCard(grade: GradeUiModel) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.sdp)
    ) {
        Column(
            modifier = Modifier.padding(16.sdp)
        ) {
            Text(
                text = grade.courseName,
                style = MaterialTheme.typography.titleMedium,
                maxLines = 2
            )
            
            Spacer(modifier = Modifier.height(8.sdp))
            
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Grade: ${grade.grade}",
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.primary
                )
                

                if (grade.percentage > 0f) {
                    Text(
                        text = "${(grade.percentage * 100).toInt()}%",
                        style = MaterialTheme.typography.labelLarge
                    )
                }
            }
            
            Spacer(modifier = Modifier.height(8.sdp))

            if (grade.percentage > 0f) {
                LinearProgressIndicator(
                    progress = { grade.percentage },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(8.sdp)
                        .clip(RoundedCornerShape(4.sdp)),
                    color = if (grade.percentage >= 0.5f) Color(0xFF4CAF50) else Color(0xFFF44336),
                    trackColor = MaterialTheme.colorScheme.surfaceVariant
                )
            } else {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(8.sdp)
                        .clip(RoundedCornerShape(4.sdp))
                        .background(MaterialTheme.colorScheme.surfaceVariant)
                )
            }
        }
    }
}
