package com.example.moobleandroid.presentation.ui.screens.coursedetails

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import coil.compose.AsyncImage
import com.example.moobleandroid.presentation.ui.components.SectionItem
import com.example.moobleandroid.presentation.viewmodel.coursedetails.CourseDetailEffect
import com.example.moobleandroid.presentation.viewmodel.coursedetails.CourseDetailState
import com.example.moobleandroid.presentation.viewmodel.coursedetails.CourseDetailViewModel
import ir.kaaveh.sdpcompose.sdp
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CourseDetailScreen(
    courseId: Int,
    courseName: String,
    courseImage: String?,
    viewModel: CourseDetailViewModel = koinViewModel(),
    onBackClick: () -> Unit
) {
    val state by viewModel.viewState.collectAsState()

    LaunchedEffect(courseId) {
        viewModel.init(courseId, courseName, courseImage)
    }

    LaunchedEffect(Unit) {
        viewModel.viewEffect.collect { effect ->
            when (effect) {
                is CourseDetailEffect.NavigateBack -> onBackClick()
            }
        }
    }

    CourseDetailContent(viewModel, state)

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CourseDetailContent(
    viewModel: CourseDetailViewModel,
    state: CourseDetailState
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(state.courseName, maxLines = 1) },
                navigationIcon = {
                    IconButton(onClick = { viewModel.onBackClick() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            // Header: Course Image and Name
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.sdp)
            ) {
                val imageUrl = if (!state.courseImage.isNullOrEmpty()) {
                    val separator = if (state.courseImage.contains("?")) "&" else "?"
                    "${state.courseImage}${separator}token=${state.token}"
                } else {
                    null
                }

                AsyncImage(
                    model = imageUrl,
                    contentDescription = null,
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop,
                    placeholder = androidx.compose.ui.res.painterResource(id = android.R.drawable.ic_menu_gallery),
                    error = androidx.compose.ui.res.painterResource(id = android.R.drawable.stat_notify_error)
                )

                Surface(
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.BottomStart),
                    color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.7f)
                ) {
                    Text(
                        text = state.courseName,
                        style = MaterialTheme.typography.headlineSmall,
                        modifier = Modifier.padding(16.sdp)
                    )
                }
            }

            if (state.isLoading) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            } else if (state.errorMessage != null) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text(text = state.errorMessage, color = MaterialTheme.colorScheme.error)
                }
            } else {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(16.sdp),
                    verticalArrangement = Arrangement.spacedBy(8.sdp)
                ) {
                    items(state.sections) { section ->
                        if (section.modules.isNotEmpty() || section.name.isNotBlank()) {
                            SectionItem(section.name)
                        }
                    }
                }
            }
        }
    }
}

