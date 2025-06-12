package com.example.appmoview.presentation.screens

import android.content.Context
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(navController: NavController) {
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        delay(2000)
        val sharedPref = context.getSharedPreferences("auth_prefs", Context.MODE_PRIVATE)
        val token = sharedPref.getString("token", null)

        if (token.isNullOrEmpty()) {
            navController.navigate("login") {
                popUpTo("splash") { inclusive = true } // Xóa Splash khỏi backstack
            }
        } else {
            navController.navigate("home") {
                popUpTo("splash") { inclusive = true }
            }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "APPhim",
            style = MaterialTheme.typography.displayLarge,
            color = MaterialTheme.colorScheme.onBackground
        )
    }
}

