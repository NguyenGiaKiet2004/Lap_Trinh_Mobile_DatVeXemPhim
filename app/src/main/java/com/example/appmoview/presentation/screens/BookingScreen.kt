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
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import com.example.appmoview.R
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.*


@Composable
fun BookingScreen() {
    val selectedSeats = remember { mutableStateListOf<String>() }
    val bookedSeats = listOf("B3", "C4", "E2", "F5") // Ghế đã đặt

    val seatRows = listOf("A", "B", "C", "D", "E", "F", "G")
    val seatCols = 6
    val seatSize = 36.dp
    val seatPadding = 8.dp
    val screenWidth = (seatSize + seatPadding) * seatCols

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(colorScheme.background)
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "Chọn chỗ ngồi",
                color = colorScheme.onBackground,
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold
            )

            Image(
                painter = painterResource(id = R.drawable.baseline_arrow_back_ios_24),
                contentDescription = "Back",
                modifier = Modifier
                    .align(Alignment.CenterStart)
                    .size(24.dp)
                    .clickable { },
                colorFilter = ColorFilter.tint(colorScheme.onSurface)
            )
        }

        Spacer(modifier = Modifier.height(25.dp))

        // Hình ảnh màn hình với chiều rộng đúng bằng dãy ghế
        Box(
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.screen),
                contentDescription = "Screen",
                modifier = Modifier
                    .width(screenWidth)
                    .height(80.dp)
                    .padding(vertical = 2.dp),
                contentScale = ContentScale.Fit
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        seatRows.forEach { row ->
            Row(
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxWidth()
            ) {
                for (col in 1..seatCols) {
                    val seatId = "$row$col"
                    val isBooked = bookedSeats.contains(seatId)
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
                            .background(
                                color = seatColor,
                                shape = RoundedCornerShape(6.dp)
                            )
                            .clickable(enabled = !isBooked) {
                                if (isSelected) selectedSeats.remove(seatId)
                                else selectedSeats.add(seatId)
                            },
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = seatId,
                            fontSize = 10.sp,
                            color = Color.White
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            LegendItem("Trống", Color(0xFF2C2C2C))
            LegendItem("Chọn", colorScheme.primary)
            LegendItem("Hết", Color.Gray)
        }

        Spacer(modifier = Modifier.height(25.dp))

        val days = listOf("Feb 22", "Feb 23", "Feb 24", "Feb 25", "Feb 26")
        val times = listOf("11:15", "3:15", "6:15", "10:15")

        var selectedDay by remember { mutableStateOf("Feb 25") }
        var selectedTime by remember { mutableStateOf("3:15") }


        Text(
            text = "Chọn ngày & giờ",
            color = colorScheme.onBackground,
            fontSize = 16.sp,
            fontWeight = FontWeight.Medium
        )

        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier
                .padding(top = 20.dp)
                .fillMaxWidth()
        ) {
            items(days) { day ->
                Box(
                    modifier = Modifier
                        .background(
                            color = if (day == selectedDay) colorScheme.primary else Color(0xFF2C2C2C),
                            shape = RoundedCornerShape(10.dp)
                        )
                        .clickable { selectedDay = day }
                        .padding(vertical = 8.dp, horizontal = 12.dp)
                ) {
                    Text(text = day, color = Color.White)
                }
            }
        }

        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier
                .padding(top = 20.dp)
                .fillMaxWidth()
        ) {
            items(times) { time ->
                Box(
                    modifier = Modifier
                        .background(
                            color = if (time == selectedTime) colorScheme.primary else Color(0xFF2C2C2C),
                            shape = RoundedCornerShape(10.dp)
                        )
                        .clickable { selectedTime = time }
                        .padding(vertical = 8.dp, horizontal = 16.dp)
                ) {
                    Text(text = time, color = Color.White)
                }
            }
        }

        Spacer(modifier = Modifier.height(25.dp))

        Button(
            onClick = { /* Handle booking */ },
            colors = ButtonDefaults.buttonColors(
                containerColor = colorScheme.primary,
                contentColor = colorScheme.onPrimary
            ),
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            shape = RoundedCornerShape(16.dp)
        ) {
            Text(text = "MUA VÉ", fontSize = 17.sp, fontWeight = FontWeight.Bold)
        }

        Spacer(modifier = Modifier.height(10.dp))

        Text(
            text = "Giá: 300.000",
            fontSize = 16.sp,
            color = colorScheme.onBackground,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )
    }
}


@Composable
fun LegendItem(text: String, color: Color) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Box(
            modifier = Modifier
                .size(16.dp)
                .background(color = color, shape = RoundedCornerShape(4.dp))
        )
        Spacer(modifier = Modifier.width(6.dp))
        Text(text = text, fontSize = 12.sp, color = Color.White)
    }
}
