package com.example.appmoview.presentation.screens

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.appmoview.presentation.viewmodels.BookingViewModel
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

fun getShowDate(startTime: String): String {
    return try {
        val sdf = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault())
        val date = sdf.parse(startTime)
        SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(date!!)
    } catch (e: Exception) {
        ""
    }
}

fun getShowTime(startTime: String): String {
    return try {
        val sdf = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault())
        val date = sdf.parse(startTime)
        SimpleDateFormat("HH:mm", Locale.getDefault()).format(date!!)
    } catch (e: Exception) {
        ""
    }
}

fun getDuration(start: String, end: String): String {
    return try {
        val sdf = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault())
        val startDate = sdf.parse(start)
        val endDate = sdf.parse(end)
        val durationInMillis = endDate!!.time - startDate!!.time
        val minutes = durationInMillis / (1000 * 60)
        "$minutes phút"
    } catch (e: Exception) {
        ""
    }
}

fun isPastDate1(dateStr: String): Boolean {
    return try {
        val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        val date = sdf.parse(dateStr)
        date?.before(Date()) ?: false
    } catch (e: Exception) {
        false
    }
}

@Composable
fun ListTicketScreen1(
    navController: NavController,
) {
    val viewModel: BookingViewModel = viewModel()
    val bookings by viewModel.rawBookings.observeAsState(emptyList())
    val isLoading by viewModel.isLoading.observeAsState(false)
    val error by viewModel.errorMessage.observeAsState("")

    val context = LocalContext.current
    val sharedPref = context.getSharedPreferences("auth_prefs", Context.MODE_PRIVATE)
    val userid = sharedPref.getString("user_id", "-1")!!.toIntOrNull()

    LaunchedEffect(Unit) {
        viewModel.loadBookings(userid!!)
    }

    val colorScheme = MaterialTheme.colorScheme

    Scaffold(
        bottomBar = {
            BottomNavigationBar1(
                selectedIndex = 1,
                onItemSelected = { index ->
                    when (index) {
                        0 -> {
                            if (navController.previousBackStackEntry != null) {
                                navController.popBackStack()
                            } else {
                                navController.navigate("home") {
                                    popUpTo(0) { inclusive = true } // Xóa hết stack
                                }
                            }
                        }
                        1 -> {
                            // Đang ở màn vé - không làm gì
                        }
                        2 -> {
                            navController.navigate("account") {
                                popUpTo("ticket") { inclusive = true }
                            }
                        }
                    }
                }

            )
        },
        containerColor = Color.Black
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(Color.Black)
        ) {
            // Header
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
            ) {
                Text(
                    text = "Lịch sử đặt vé",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = colorScheme.onSurface,
                    modifier = Modifier.align(Alignment.Center),
                    textAlign = TextAlign.Center
                )
            }

            if (isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    color = Color.White
                )
            }

            if (error.isNotEmpty()) {
                Text(
                    text = error,
                    color = Color.Red,
                    modifier = Modifier
                        .padding(16.dp)
                        .align(Alignment.CenterHorizontally)
                )
            }

            LazyColumn(
                modifier = Modifier.fillMaxSize()
            ) {
                items(bookings) { booking ->
                    val date = getShowDate(booking.start_time)
                    val time = getShowTime(booking.start_time)
                    val duration = getDuration(booking.start_time, booking.end_time)

                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 16.dp, horizontal = 20.dp)
                            .height(180.dp)
                            .clip(RoundedCornerShape(20.dp))
                            .background(Color.DarkGray)
                    ) {
                        Column(
                            modifier = Modifier
                                .padding(top = 5.dp, start = 10.dp, end = 10.dp)
                                .fillMaxSize()
                        ) {
                            Text(
                                text = date,
                                modifier = Modifier.fillMaxWidth(),
                                fontSize = 12.sp,
                                color = if (isPastDate1(date)) Color.Red else Color.Green,
                                textAlign = TextAlign.Right
                            )
                            Text(
                                text = "Mã phim ${booking.showtime_id}",
                                fontSize = 22.sp,
                                fontWeight = FontWeight.Bold,
                                color = colorScheme.onSurface,
                                modifier = Modifier.fillMaxWidth(),
                                textAlign = TextAlign.Center
                            )
                            Text(
                                text = "Phòng ${booking.room_id}",
                                fontSize = 14.sp,
                                color = colorScheme.onSurface,
                                modifier = Modifier
                                    .padding(top = 6.dp)
                                    .fillMaxWidth(),
                                textAlign = TextAlign.Center
                            )

                            HorizontalDivider(
                                modifier = Modifier.padding(vertical = 10.dp),
                                thickness = 1.dp,
                                color = Color.White
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
                                    Text("ID Vé", fontWeight = FontWeight.SemiBold, color = colorScheme.onSurface)
                                    Text("${booking.booking_id}", color = colorScheme.onSurface)
                                }
                                Column(
                                    modifier = Modifier.weight(1f),
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {
                                    Text("Thời lượng", fontWeight = FontWeight.SemiBold, color = colorScheme.onSurface)
                                    Text(duration, color = colorScheme.onSurface)
                                }
                                Column(
                                    modifier = Modifier.weight(1f),
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {
                                    Text("Giờ chiếu", fontWeight = FontWeight.SemiBold, color = colorScheme.onSurface)
                                    Text(time, color = colorScheme.onSurface)
                                }
                            }

                            HorizontalDivider(
                                modifier = Modifier.padding(vertical = 10.dp),
                                thickness = 1.dp,
                                color = Color.White
                            )

                            Text(
                                text = "Giá: ${booking.price} VNĐ",
                                fontSize = 14.sp,
                                color = colorScheme.onSurface,
                                modifier = Modifier.align(Alignment.End)
                            )
                        }
                    }
                }
            }
        }
    }
}

