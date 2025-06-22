package com.example.appmoview

import android.os.Build
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.compose.rememberNavController
import com.example.appmoview.presentation.navigation.AppNavigation
import com.example.appmoview.presentation.theme.SystemTheme
import com.example.appmoview.utils.logout

class MainActivity : AppCompatActivity() {

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent{
            SystemTheme{
                val navController = rememberNavController()
                AppNavigation(navController = navController)
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        logout(this)
    }

}
