package com.example.moobleandroid.presentation.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import coil.compose.AsyncImage
import com.example.moobleandroid.data.api.CourseResponse
import ir.kaaveh.sdpcompose.sdp

@Composable
fun CourseItem(
    course: CourseResponse,
    token: String,
    onClick: () -> Unit
) {
    val imageUrl = if (!course.courseImage.isNullOrEmpty()) {
        val separator = if (course.courseImage.contains("?")) "&" else "?"
        "${course.courseImage}${separator}token=$token"
    } else {
        null
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        elevation = CardDefaults.cardElevation(defaultElevation = 2.sdp)
    ) {
        Row(
            modifier = Modifier
                .padding(8.sdp)
                .height(80.sdp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            AsyncImage(
                model = imageUrl,
                contentDescription = "Course Image",
                modifier = Modifier
                    .size(80.sdp)
                    .clip(RoundedCornerShape(8.sdp)),
                contentScale = ContentScale.Crop,
                placeholder = androidx.compose.ui.res.painterResource(id = android.R.drawable.ic_menu_gallery),
                error = androidx.compose.ui.res.painterResource(id = android.R.drawable.stat_notify_error)
            )

            Spacer(modifier = Modifier.width(16.sdp))

            Column(
                modifier = Modifier.fillMaxHeight(),
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = course.fullName,
                    style = MaterialTheme.typography.titleMedium,
                    maxLines = 2
                )
                Text(
                    text = course.shortName,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.outline
                )

                Spacer(modifier = Modifier.height(12.sdp))

                // Progress Bar Section
                if (course.progress != null) {
                    val progressValue = course.progress / 100f

                    Column {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                text = "Progress",
                                style = MaterialTheme.typography.labelSmall
                            )
                            Text(
                                text = "${course.progress.toInt()}%",
                                style = MaterialTheme.typography.labelSmall,
                                color = MaterialTheme.colorScheme.primary
                            )
                        }

                        Spacer(modifier = Modifier.height(4.sdp))

                        LinearProgressIndicator(
                            progress = { progressValue },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(6.sdp)
                                .clip(androidx.compose.foundation.shape.RoundedCornerShape(3.sdp)),
                            trackColor = MaterialTheme.colorScheme.surfaceVariant,
                            color = MaterialTheme.colorScheme.primary
                        )
                    }
                }
            }
        }
    }
}