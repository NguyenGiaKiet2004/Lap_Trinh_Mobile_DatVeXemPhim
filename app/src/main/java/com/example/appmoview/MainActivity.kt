package com.example.appmoview

import ShowAllFilm
import android.content.Context
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.compose.rememberNavController
import com.example.appmoview.presentation.navigation.AppNavigation
import com.example.appmoview.presentation.screens.RegisterScreen
import com.example.appmoview.presentation.theme.SystemTheme
import com.example.appmoview.utils.logout

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent{
            SystemTheme{
                val navController = rememberNavController()
                //AppNavigation(navController = navController)
                ShowAllFilm()
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        logout(this)
    }

}
