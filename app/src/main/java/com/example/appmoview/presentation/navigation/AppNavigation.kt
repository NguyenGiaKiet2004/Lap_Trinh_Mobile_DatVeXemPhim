package com.example.appmoview.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.appmoview.presentation.screens.HomeScreen
import com.example.appmoview.presentation.screens.LoginScreen
import com.example.appmoview.presentation.screens.RegisterScreen
import com.example.appmoview.presentation.screens.SplashScreen

@Composable
fun AppNavigation(navController: NavHostController) {
    NavHost(navController = navController, startDestination = "splash") {
        composable("splash") { SplashScreen(navController) }
        composable("login") { LoginScreen(navController) }
        composable("register") { RegisterScreen(navController) }
        composable("home") { HomeScreen(navController) }
    }
}