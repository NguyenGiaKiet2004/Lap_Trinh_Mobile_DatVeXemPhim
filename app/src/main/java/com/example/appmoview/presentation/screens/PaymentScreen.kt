package com.example.appmoview.presentation.screens

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.appmoview.R
import com.example.appmoview.presentation.viewmodels.MovieViewModel
import com.example.appmoview.utils.formatDateTimeRaw

@Composable
fun PaymentScreen(
    movieId: Int,
    viewModel: MovieViewModel,
    navController: NavController
) {
    val selectedShowtime by viewModel.selectedShowtime.observeAsState()
    val selectedSeats by viewModel.selectedSeatNames.observeAsState(emptyList())
    val seatIds by viewModel.selectedSeatIds.observeAsState(emptyList())
    val bookingResult by viewModel.bookingResult.observeAsState()
    val context = LocalContext.current

    // loading state
    var isLoading by remember { mutableStateOf(false) }



    val movieName = selectedShowtime?.movies?.movie_name ?: ""
    val roomName = selectedShowtime?.rooms?.room_name ?: ""
    val startTime = formatDateTimeRaw(selectedShowtime?.startTime ?: "")
    val pricePerSeat = selectedShowtime?.movies?.movie_price ?: 0
    val totalPrice = pricePerSeat * selectedSeats.size

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 30.dp, bottom = 20.dp, start = 16.dp, end = 16.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "Thanh toán",
                color = colorScheme.onBackground,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            )

            Image(
                painter = painterResource(id = R.drawable.baseline_arrow_back_ios_24),
                contentDescription = "Back",
                modifier = Modifier
                    .align(Alignment.CenterStart)
                    .size(24.dp)
                    .clickable { navController.popBackStack() },
                colorFilter = ColorFilter.tint(colorScheme.onSurface)
            )
        }

        Spacer(modifier = Modifier.height(28.dp))

        PaymentItem(label = "Tên phim:", value = movieName)
        PaymentItem(label = "Ghế:", value = selectedSeats.joinToString(", "))
        PaymentItem(label = "Rạp:", value = roomName)
        PaymentItem(label = "Giờ chiếu:", value = startTime)
        PaymentItem(label = "Giá vé:", value = "%,d đ x %d".format(pricePerSeat, selectedSeats.size))

        Spacer(modifier = Modifier.height(30.dp))

        Text(
            text = "Tổng: %,d đ".format(totalPrice),
            color = Color.White,
            fontWeight = FontWeight.SemiBold,
            fontSize = 16.sp,
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(bottom = 8.dp)
        )

        Text(
            text = "Phương thức thanh toán",
            color = Color.White,
            fontWeight = FontWeight.SemiBold,
            fontSize = 16.sp
        )

        Spacer(modifier = Modifier.height(16.dp))

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFF1E1E1E), RoundedCornerShape(12.dp))
                .padding(16.dp)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Image(
                    painter = painterResource(id = R.drawable.screen),
                    contentDescription = "Mastercard",
                    modifier = Modifier.size(40.dp)
                )
                Spacer(modifier = Modifier.width(12.dp))
                Text(
                    text = "************ 3454",
                    color = Color.White,
                    fontSize = 15.sp,
                    modifier = Modifier.weight(1f)
                )
            }
        }

        Spacer(modifier = Modifier.height(32.dp))

        // Nút hoặc loading spinner
        if (isLoading) {
            CircularProgressIndicator(
                color = Color(0xFFFFC107),
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(top = 10.dp)
            )
        } else {
            Button(
                onClick = {
                    val sharedPref = context.getSharedPreferences("auth_prefs", Context.MODE_PRIVATE)
                    val userId = sharedPref.getString("user_id", null)?.toIntOrNull()
                    if (userId != null && selectedShowtime != null) {
                        isLoading = true
                        viewModel.createBooking(userId, selectedShowtime!!.showtimeId, seatIds)
                    } else {
                        Toast.makeText(context, "Thiếu thông tin người dùng hoặc lịch chiếu", Toast.LENGTH_SHORT).show()
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFFFC107),
                    contentColor = Color.Black
                ),
                shape = RoundedCornerShape(16.dp)
            ) {
                Text(
                    text = "THANH TOÁN",
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp
                )
            }
        }
    }

    // Quan sát kết quả đặt vé
    LaunchedEffect(bookingResult) {
        bookingResult?.let { (success, message) ->
            isLoading = false
            viewModel.clearBookingResult() // Reset để tránh gọi lại
            if (success) {
                navController.navigate("success_screen") {
                    popUpTo(0) { inclusive = true }
                }
            } else {
                Toast.makeText(context, message, Toast.LENGTH_LONG).show()
            }
        }
    }

}


@Composable
fun PaymentItem(label: String, value: String) {
    Column(modifier = Modifier
        .fillMaxWidth()
        .padding(vertical = 6.dp)) {
        Row(horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth()) {
            Text(text = label, color = Color.Gray, fontSize = 16.sp)
            Text(text = value, color = Color.White, fontSize = 16.sp, fontWeight = FontWeight.Medium)
        }
    }
}
