package com.example.moobleandroid.presentation.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.example.moobleandroid.presentation.ui.screens.course.CoursesScreen
import com.example.moobleandroid.presentation.ui.screens.coursedetails.CourseDetailScreen
import com.example.moobleandroid.presentation.ui.screens.grades.GradesScreen
import com.example.moobleandroid.presentation.ui.screens.login.LoginScreen

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Route.Login
    ) {
        composable<Route.Login> {
            LoginScreen(
                onLoginSuccess = {
                    navController.navigate(Route.CourseList) {
                        popUpTo(Route.Login) { inclusive = true }
                    }
                }
            )
        }

        composable<Route.CourseList> {
            CoursesScreen(
                onCourseClick = { courseId, name, image ->
                    navController.navigate(Route.CourseDetails(courseId, name, image))
                },
                onGradesClick = {
                    navController.navigate(Route.Grades)
                }
            )
        }
        
        composable<Route.CourseDetails> { backStackEntry ->
            val args = backStackEntry.toRoute<Route.CourseDetails>()
            CourseDetailScreen(
                courseId = args.courseId,
                courseName = args.courseName,
                courseImage = args.courseImage,
                onBackClick = { navController.popBackStack() }
            )
        }

        composable<Route.Grades> {
            GradesScreen(
                onBackClick = { navController.popBackStack() }
            )
        }
    }
}
