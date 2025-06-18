package com.example.appmoview.presentation.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.appmoview.R
import com.example.appmoview.presentation.viewmodels.MovieViewModel
import java.text.NumberFormat
import java.util.Locale


@Composable
fun SeatBookingScreen(
    movieId: Int,
    viewModel: MovieViewModel,
    navController: NavController
) {
    val selectedSeats = remember { mutableStateListOf<String>() }


    val seatRows = listOf("A", "B", "C", "D", "E", "F", "G")
    val seatCols = 6
    val seatSize = 42.dp
    val seatPadding = 10.dp
    val screenWidth = (seatSize + seatPadding) * seatCols



    val isLoading by viewModel.isLoading.observeAsState(true)
    // Lấy showtime đã chọn
    val selectedShowtime = viewModel.selectedShowtime.value
    // Sinh danh sách tất cả ghế (ví dụ 42 ghế: 1 đến 42)
    val allSeatIds = (1..(seatRows.size * seatCols)).toList()

    // Gọi kiểm tra ghế đã đặt nếu có showtime
    LaunchedEffect(selectedShowtime) {
        selectedShowtime?.let {
            viewModel.checkBookedSeats(it.showtimeId, allSeatIds)
        }
    }

    val bookedSeats = viewModel.bookedSeats.value ?: emptyList()

    if (isLoading) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black),
            contentAlignment = Alignment.Center
        ) {
            Text("Đang tải...", color = Color.White)
        }
        return
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(colorScheme.background)
            .padding(16.dp)
    ) {
        // Nút Back
        // Row chứa cả nút back và tiêu đề căn giữa
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            IconButton(onClick = { navController.popBackStack() }) {
                Image(
                    painter = painterResource(id = R.drawable.baseline_arrow_back_ios_24),
                    contentDescription = "Back",
                    modifier = Modifier.size(24.dp),
                    colorFilter = ColorFilter.tint(colorScheme.onSurface)
                )
            }

            // Căn giữa tiêu đề bằng cách đặt trong Box giữa Spacer
            Box(
                modifier = Modifier.weight(1f),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Chọn ghế",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = colorScheme.onSurface
                )
            }

            Spacer(modifier = Modifier.width(48.dp)) // chiếm chỗ đối xứng với nút back
        }


        // Màn hình chiếu
        Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
            Image(
                painter = painterResource(id = R.drawable.screen),
                contentDescription = "Screen",
                modifier = Modifier
                    .width(screenWidth)
                    .height(100.dp), // <<-- TĂNG CHIỀU CAO Ở ĐÂY
                contentScale = ContentScale.Fit
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Ghế
        Column(modifier = Modifier.verticalScroll(rememberScrollState())) {
            seatRows.forEach { row ->
                Row(horizontalArrangement = Arrangement.Center, modifier = Modifier.fillMaxWidth()) {
                    for (col in 1..seatCols) {
                        val seatId = "$row$col"
                        val isBooked = bookedSeats.contains(seatId.toIntOrNull())
                        val isSelected = selectedSeats.contains(seatId)
                        val seatColor = when {
                            isBooked -> Color.Gray
                            isSelected -> colorScheme.primary
                            else -> Color(0xFF2C2C2C)
                        }

                        Box(
                            modifier = Modifier
                                .padding(4.dp)
                                .size(seatSize)
                                .background(color = seatColor, shape = RoundedCornerShape(6.dp))
                                .clickable(enabled = !isBooked) {
                                    if (isSelected) selectedSeats.remove(seatId)
                                    else selectedSeats.add(seatId)
                                },
                            contentAlignment = Alignment.Center
                        ) {
                            Text(text = seatId, fontSize = 12.sp, color = Color.White)
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
                LegendItem("Trống", Color(0xFF2C2C2C))
                LegendItem("Chọn", colorScheme.primary)
                LegendItem("Hết", Color.Gray)
            }



            // Nút MUA VÉ
            Spacer(modifier = Modifier.height(32.dp)) // tạo khoảng cách trước nút

            val totalPrice = selectedSeats.size * selectedShowtime!!.movies.movie_price
            val formattedPrice = NumberFormat.getNumberInstance(Locale("vi", "VN")).format(totalPrice)
            Text(
                text = "Tổng tiền: $formattedPrice đ",
                fontSize = 18.sp,
                fontWeight = FontWeight.SemiBold,
                color = Color.White,
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(bottom = 8.dp)
            )


            Spacer(modifier = Modifier.height(5.dp))
            Box(
                modifier = Modifier
                    .fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                Button(
                    onClick = { /* Handle đặt vé */ },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp), // cao hơn 1 chút
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Text(
                        text = "MUA VÉ",
                        fontSize = 17.sp,
                        fontWeight = FontWeight.Bold,
                        maxLines = 1
                    )
                }
            }

        }
    }

}



@Composable
fun LegendItem(text: String, color: Color) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Box(
            modifier = Modifier
                .size(20.dp)
                .background(color = color, shape = RoundedCornerShape(4.dp))
        )
        Spacer(modifier = Modifier.width(6.dp))
        Text(text = text, fontSize = 16.sp, color = Color.White)
    }
}





