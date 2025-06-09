package com.example.appmoview

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.runtime.Composable
import com.example.appmoview.presentation.screens.AccountScreen
import com.example.appmoview.presentation.screens.RegisterScreen
import com.example.appmoview.presentation.screens.HomeScreen
import com.example.appmoview.presentation.screens.HomeScreen1
import com.example.appmoview.presentation.screens.BookingScreen
import com.example.appmoview.presentation.screens.ListTickerScreen
import com.example.appmoview.presentation.screens.MovieDetailScreen
import com.example.appmoview.presentation.screens.MovieSearchScreen

import com.example.appmoview.presentation.theme.SystemTheme

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent{
            SystemTheme{
                RegisterScreen()
            }
        }

    }
}
