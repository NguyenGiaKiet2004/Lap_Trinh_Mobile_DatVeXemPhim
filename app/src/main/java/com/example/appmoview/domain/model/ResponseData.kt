package com.example.appmoview.domain.model


data class ResponseData(
    val success: Boolean,
    val data: Any? // hoặc dùng JsonObject nếu bạn cần xử lý cụ thể
)

