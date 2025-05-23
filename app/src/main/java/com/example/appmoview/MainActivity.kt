package com.example.appmoview

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import com.example.appmoview.presentation.screens.RegisterScreen
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