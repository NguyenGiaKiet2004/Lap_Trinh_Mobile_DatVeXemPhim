package com.example.appmoview.utils

import android.content.Context

fun logout(context: Context) {
    val sharedPref = context.getSharedPreferences("auth_prefs", Context.MODE_PRIVATE)
    sharedPref.edit().clear().apply()
}
