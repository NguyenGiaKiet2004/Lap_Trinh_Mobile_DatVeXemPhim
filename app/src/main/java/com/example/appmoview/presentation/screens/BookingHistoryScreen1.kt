package com.example.appmoview.presentation.screens

import android.content.Context
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.appmoview.R
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

// Hàm kiểm tra ngày đã qua
fun isPastDate1(dateStr: String): Boolean {
    return try {
        val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        val date = sdf.parse(dateStr)
        date?.before(Date()) ?: false
    } catch (e: Exception) {
        false // Nếu lỗi định dạng, coi như chưa qua
    }
}

// Định nghĩa dữ liệu mẫu cho vé xem phim
data class Booking(
    val id: Int,
    val movieName: String,
    val movieType: String,
    val duration: String,
    val showDate: String,
    val showTime: String
)

@Composable
fun ListTicketScreen1(navController: NavController) {
    // Định nghĩa danh sách mẫu
    val mockBookings = remember {
        listOf(
            Booking(
                id = 1,
                movieName = "Avengers: Endgame",
                movieType = "Action",
                duration = "2h 41m",
                showDate = "20/07/2025",
                showTime = "19:30"
            ),
            Booking(
                id = 2,
                movieName = "The Lion King",
                movieType = "Animation",
                duration = "1h 58m",
                showDate = "04/07/2025",
                showTime = "14:00"
            ),
            Booking(
                id = 3,
                movieName = "Inception",
                movieType = "Sci-Fi",
                duration = "2h 28m",
                showDate = "15/06/2025",
                showTime = "21:00"
            )
        )
    }

    val context = LocalContext.current
    val colorScheme = MaterialTheme.colorScheme

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
            .padding(top = 40.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
        ) {
            IconButton(
                onClick = { navController.popBackStack() },
                modifier = Modifier.align(Alignment.CenterStart)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.baseline_arrow_back_ios_24),
                    contentDescription = "Back",
                    modifier = Modifier.size(24.dp),
                    colorFilter = ColorFilter.tint(colorScheme.onSurface)
                )
            }

            Text(
                text = "Lịch sử đặt vé",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = colorScheme.onSurface,
                modifier = Modifier.align(Alignment.Center),
                textAlign = TextAlign.Center
            )
        }

        // Hiển thị danh sách vé
        LazyColumn(
            modifier = Modifier.fillMaxSize()
        ) {
            items(mockBookings) { booking ->
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 16.dp, horizontal = 20.dp)
                        .height(150.dp)
                        .clip(RoundedCornerShape(20.dp))
                        .background(Color.DarkGray)
                ) {
                    Column(
                        modifier = Modifier
                            .padding(top = 5.dp, start = 10.dp, end = 10.dp)
                            .fillMaxSize()
                    ) {
                        Text(
                            text = booking.showDate,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(0.dp),
                            fontSize = 12.sp,
                            color = if (isPastDate1(booking.showDate)) Color.Red else Color.Green,
                            textAlign = TextAlign.Right
                        )
                        Text(
                            text = booking.movieName,
                            fontSize = 22.sp,
                            fontWeight = FontWeight.Bold,
                            color = colorScheme.onSurface,
                            modifier = Modifier.fillMaxWidth(),
                            textAlign = TextAlign.Center
                        )

                        Text(
                            text = booking.movieType,
                            fontSize = 14.sp,
                            color = colorScheme.onSurface,
                            modifier = Modifier
                                .padding(top = 6.dp)
                                .fillMaxWidth(),
                            textAlign = TextAlign.Center
                        )

                        Divider(
                            color = Color.White,
                            thickness = 1.dp,
                            modifier = Modifier.padding(vertical = 10.dp)
                        )

                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 15.dp),
                        ) {
                            Column(
                                modifier = Modifier.weight(1f),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Text(stringResource(id = R.string.id_phim), fontWeight = FontWeight.SemiBold, color = colorScheme.onSurface)
                                Text("${booking.id}", color = colorScheme.onSurface)
                            }
                            Column(
                                modifier = Modifier.weight(1f),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Text(stringResource(id = R.string.thoi_luong), fontWeight = FontWeight.SemiBold, color = colorScheme.onSurface)
                                Text(booking.duration, color = colorScheme.onSurface)
                            }
                            Column(
                                modifier = Modifier.weight(1f),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Text(stringResource(id = R.string.gio_chieu), fontWeight = FontWeight.SemiBold, color = colorScheme.onSurface)
                                Text(booking.showTime, color = colorScheme.onSurface)
                            }
                        }
                        Divider(
                            color = Color.White,
                            thickness = 1.dp,
                            modifier = Modifier.padding(vertical = 10.dp)
                        )
                    }
                }
            }
        }
    }
}