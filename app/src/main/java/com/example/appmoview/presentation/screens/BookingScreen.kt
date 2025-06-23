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
    val selectedSeatNames = remember { mutableStateListOf<String>() }

    val seatRows = listOf("A", "B", "C", "D", "E", "F", "G")
    val seatCols = 6
    val seatSize = 42.dp
    val seatPadding = 10.dp
    val screenWidth = (seatSize + seatPadding) * seatCols

    // ✅ Tạo map từ seatName ("A1", "B2"...) sang seatId (1, 2, ...)
    val seatNameToId = remember {
        mutableMapOf<String, Int>().apply {
            var id = 1
            for (row in seatRows) {
                for (col in 1..seatCols) {
                    val name = "$row$col"
                    put(name, id++)
                }
            }
        }
    }

    val isLoading by viewModel.isLoading.observeAsState(true)
    val selectedShowtime = viewModel.selectedShowtime.value
    val allSeatIds = (1..(seatRows.size * seatCols)).toList()

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
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 30.dp, bottom = 20.dp, start = 16.dp, end = 16.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "Chọn ghế",
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

        Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
            Image(
                painter = painterResource(id = R.drawable.screen),
                contentDescription = "Screen",
                modifier = Modifier
                    .width(screenWidth)
                    .height(100.dp),
                contentScale = ContentScale.Fit
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Column(modifier = Modifier.verticalScroll(rememberScrollState())) {
            seatRows.forEach { row ->
                Row(horizontalArrangement = Arrangement.Center, modifier = Modifier.fillMaxWidth()) {
                    for (col in 1..seatCols) {
                        val seatName = "$row$col"
                        val seatId = seatNameToId[seatName]
                        val isBooked = bookedSeats.contains(seatId)
                        val isSelected = selectedSeats.contains(seatName)
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
                                    if (isSelected) {
                                        selectedSeats.remove(seatName)
                                        selectedSeatNames.remove(seatName)
                                    } else {
                                        selectedSeats.add(seatName)
                                        selectedSeatNames.add(seatName)
                                    }
                                },
                            contentAlignment = Alignment.Center
                        ) {
                            Text(text = seatName, fontSize = 12.sp, color = Color.White)
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

            Spacer(modifier = Modifier.height(32.dp))

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
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                Button(
                    onClick = {
                        // ✅ Lấy seat_id đúng
                        val selectedSeatIds = selectedSeats.mapNotNull { seatNameToId[it] }
                        viewModel.saveSelectedSeats(selectedSeatIds, selectedSeatNames)
                        navController.navigate("payment_screen")
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp),
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






