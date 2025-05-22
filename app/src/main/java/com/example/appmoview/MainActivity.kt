package com.example.appmoview

import com.example.appmoview.presentation.screens.SplashScreen
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import com.example.appmoview.presentation.screens.LoginScreen
import com.example.appmoview.presentation.theme.SystemTheme

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent{
            SystemTheme{
                LoginScreen()
            }
        }

    }
    
}