package com.example.appmoview.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.appmoview.presentation.screens.AccountScreen
import com.example.appmoview.presentation.screens.DetailScreen
import com.example.appmoview.presentation.screens.HomeScreen1
import com.example.appmoview.presentation.screens.ListTicketScreen
import com.example.appmoview.presentation.screens.LoginScreen
import com.example.appmoview.presentation.screens.PaymentScreen
import com.example.appmoview.presentation.screens.RegisterScreen
import com.example.appmoview.presentation.screens.SearchScreen
import com.example.appmoview.presentation.screens.SeatBookingScreen
import com.example.appmoview.presentation.screens.ShowtimeScreen
import com.example.appmoview.presentation.screens.SplashScreen
import com.example.appmoview.presentation.screens.SuccessScreen
import com.example.appmoview.presentation.viewmodels.MovieViewModel

@Composable
fun AppNavigation(navController: NavHostController) {
    val viewModel: MovieViewModel = viewModel()
    NavHost(navController = navController, startDestination = "splash") {
        composable("splash") { SplashScreen(navController) }
        composable("login") { LoginScreen(navController) }
        composable("register") { RegisterScreen(navController) }
        composable("home") { HomeScreen1(navController) }
        composable("search") { SearchScreen(navController) }
        composable("account") { AccountScreen(navController) }
        composable(
            route = "detail/{movieId}",
            arguments = listOf(navArgument("movieId") { type = NavType.IntType })
        ) {
            val movieId = it.arguments?.getInt("movieId") ?: -1
            DetailScreen(movieId = movieId, navController = navController)
        }
        composable(
            route = "showtime_booking_screen/{movieId}",
            arguments = listOf(navArgument("movieId") { type = NavType.IntType })
        ) {
            val movieId = it.arguments?.getInt("movieId") ?: -1
            ShowtimeScreen(movieId = movieId, viewModel = viewModel, navController = navController)
        }
        composable("seat_booking_screen") {
            val movieId = it.arguments?.getInt("movieId") ?: -1
            SeatBookingScreen(movieId = movieId, viewModel = viewModel, navController = navController)
        }
        composable("payment_screen") {
            val movieId = it.arguments?.getInt("movieId") ?: -1
            PaymentScreen(movieId = movieId, viewModel = viewModel, navController = navController)
        }
        composable("success_screen") {
            val movieId = it.arguments?.getInt("movieId") ?: -1
            SuccessScreen(movieId = movieId, viewModel = viewModel, navController = navController)
        }
        composable("ListTicker") { ListTicketScreen(navController) }
    }
}