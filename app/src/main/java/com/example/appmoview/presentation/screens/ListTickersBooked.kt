package com.example.appmoview.presentation.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.hoverable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Colors
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.appmoview.R

@Composable
fun ListTickerScreen() {
    val colorScheme = MaterialTheme.colorScheme
    Column(
        modifier = Modifier.fillMaxSize()
            .background(Color.Black)
            .padding(top = 40.dp)
    ) {

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
        ) {
            IconButton(
                onClick = {},
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
            items(5) {
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
                            text="21/9/2025",
                            modifier = Modifier.fillMaxWidth().padding(0.dp),
                            fontSize = 12.sp,
                            color = Color.Green,
                            textAlign = TextAlign.Right
                        )
                        Text(
                            text = "EVIL DEAD RISE",
                            fontSize = 22.sp,
                            fontWeight = FontWeight.Bold,
                            color = colorScheme.onSurface,
                            modifier = Modifier.fillMaxWidth(),
                            textAlign = TextAlign.Center
                        )

                        Text(
                            text = "HORROR 2D.3D.4DX",
                            fontSize = 14.sp,
                            color = colorScheme.onSurface,
                            modifier = Modifier.padding(top = 6.dp).fillMaxWidth(),
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
                                Text("123", color = colorScheme.onSurface)}
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Text(stringResource(id = R.string.thoi_luong), fontWeight = FontWeight.SemiBold, color = colorScheme.onSurface)
                                Text("1h:38min", color = colorScheme.onSurface)}
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Text(stringResource(id = R.string.gio_chieu), fontWeight = FontWeight.SemiBold, color = colorScheme.onSurface)
                                Text("23h", color = colorScheme.onSurface)}
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

