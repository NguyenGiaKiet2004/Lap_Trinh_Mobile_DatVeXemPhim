package com.example.appmoview.presentation.screens

//for Date
import android.content.Context
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Divider
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.remember
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
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.appmoview.R
import com.example.appmoview.presentation.viewmodels.BookingViewModel
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


fun isPastDate(dateStr: String): Boolean {
    return try {
        val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        val date = sdf.parse(dateStr)
        date?.before(Date()) ?: false
    } catch (e: Exception) {
        false // nếu lỗi định dạng thì coi như chưa qua
    }
}

data class Booking(
    val id: Int,
    val movieName: String,
    val movieType: String,
    val duration: String,
    val showDate: String,
    val showTime: String
)


@Composable
fun ListTicketScreen(navController: NavController,viewModel: BookingViewModel = viewModel()) {
    val bookings by viewModel.bookings.observeAsState(emptyList())
    val context = LocalContext.current
    val colorScheme = MaterialTheme.colorScheme
    // Đọc userId từ SharedPreferences
    val userId = remember {
        val sharedPref = context.getSharedPreferences("auth_prefs", Context.MODE_PRIVATE)
        val userIdStr = sharedPref.getString("user_id", null)
        userIdStr?.toIntOrNull() ?: -1 // nếu null hoặc lỗi thì trả -1
    }
    // Gọi API nếu userId hợp lệ
    LaunchedEffect(Unit) {
        if (userId != -1) {
            /*iewModel.loadBookings(userId)*/
        } else {
            Log.e("ListTicketScreen", "Không tìm thấy userId")
        }
    }

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
                onClick = {navController.popBackStack()},
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


        // Nội dung bên dưới
        LazyColumn(
            modifier = Modifier.fillMaxSize()
        ) {
            items(bookings) {booking->
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 16.dp, horizontal = 20.dp)
                        .height(150.dp)
                        .clip(RoundedCornerShape(20.dp))
                        .background(Color.DarkGray)
                )
                {
                    Column(
                        modifier = Modifier
                            .padding(top = 5.dp, start = 10.dp, end = 10.dp)
                            .fillMaxSize()
                    ) {
                        Text(
                            text=booking.showDate,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(0.dp),
                            fontSize = 12.sp,
                            color = if (isPastDate(booking.showDate) == true) Color.Red else Color.Green,
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
                            horizontalArrangement = Arrangement.SpaceBetween,
                        ) {
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Text(stringResource(id = R.string.id_phim), fontWeight = FontWeight.SemiBold, color = colorScheme.onSurface)
                                Text("${booking.id}", color = colorScheme.onSurface)}
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Text(stringResource(id = R.string.thoi_luong), fontWeight = FontWeight.SemiBold, color = colorScheme.onSurface)
                                Text(booking.duration, color = colorScheme.onSurface)}
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Text(stringResource(id = R.string.gio_chieu), fontWeight = FontWeight.SemiBold, color = colorScheme.onSurface)
                                Text(booking.showTime, color = colorScheme.onSurface)}
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

