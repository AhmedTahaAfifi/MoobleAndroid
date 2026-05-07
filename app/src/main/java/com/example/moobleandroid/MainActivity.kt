package com.example.moobleandroid

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.moobleandroid.presentation.ui.navigation.AppNavigation
import com.example.moobleandroid.presentation.ui.theme.MoobleAndroidTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MoobleAndroidTheme {
                AppNavigation()
                /*Scaffold(modifier = Modifier.fillMaxSize()) {
                }*/
            }
        }
    }
}