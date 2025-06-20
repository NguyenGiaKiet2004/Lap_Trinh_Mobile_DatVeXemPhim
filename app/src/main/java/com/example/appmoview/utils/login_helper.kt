package com.example.appmoview.utils

import android.content.Context

fun logout(context: Context) {
    val sharedPref = context.getSharedPreferences("auth_prefs", Context.MODE_PRIVATE)
    sharedPref.edit().clear().apply()
}

fun formatDateTimeRaw(input: String): String {
    val parts = input.split("T")
    val date = parts[0]
    val time = parts[1].substring(0, 5) // chỉ lấy "HH:mm"

    return "$date - $time"
}

