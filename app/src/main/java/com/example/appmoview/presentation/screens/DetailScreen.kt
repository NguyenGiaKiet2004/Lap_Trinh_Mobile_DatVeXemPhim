package com.example.appmoview.presentation.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.appmoview.R

@Composable
fun MovieDetailScreen() {
    val colorScheme = MaterialTheme.colorScheme

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(colorScheme.background)
    ) {
        // Ảnh banner
        Image(
            painter = painterResource(id = R.drawable.ic_launcher_background),
            contentDescription = "Evil Dead Banner",
            modifier = Modifier
                .fillMaxWidth()
                .height(350.dp),
            contentScale = ContentScale.Crop
        )

        // Nút Back không có nền
        Box(
            modifier = Modifier
                .padding(16.dp)
                .size(40.dp)
                .clickable { }, // Thêm logic back ở đây nếu cần
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.baseline_arrow_back_ios_24),
                contentDescription = "Back",
                modifier = Modifier.size(24.dp),
                colorFilter = ColorFilter.tint(colorScheme.onSurface)
            )
        }

        // Nội dung bên dưới
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 280.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        color = colorScheme.surface,
                        shape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp)
                    )
                    .padding(horizontal = 20.dp, vertical = 28.dp)
                    .verticalScroll(rememberScrollState())
            ) {
                Text(
                    text = "EVIL DEAD RISE",
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold,
                    color = colorScheme.onSurface
                )

                Text(
                    text = "HORROR 2D.3D.4DX",
                    fontSize = 14.sp,
                    color = colorScheme.onSurface,
                    modifier = Modifier.padding(top = 6.dp)
                )

                Spacer(modifier = Modifier.height(25.dp)) // spacing sau tiêu đề

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Column {
                        Text("Censor Rating", fontWeight = FontWeight.SemiBold, color = colorScheme.onSurface)
                        Text("A", color = colorScheme.onSurface)
                    }
                    Column {
                        Text("Thời lượng", fontWeight = FontWeight.SemiBold, color = colorScheme.onSurface)
                        Text("1hr:38min", color = colorScheme.onSurface)
                    }
                    Column {
                        Text("Ngày phát hành", fontWeight = FontWeight.SemiBold, color = colorScheme.onSurface)
                        Text("21 April 2023", color = colorScheme.onSurface)
                    }
                }

                Spacer(modifier = Modifier.height(25.dp))

                Column {
                    Text("Ngôn ngữ", fontWeight = FontWeight.SemiBold, color = colorScheme.onSurface)
                    Text("English", color = colorScheme.onSurface)
                }

                Spacer(modifier = Modifier.height(25.dp))

                Text("Cốt chuyện", fontWeight = FontWeight.SemiBold, color = colorScheme.onSurface)
                Text(
                    "Evil Dead is a 2013 American supernatural horror film directed by Fede Álvarez...",
                    color = colorScheme.onSurface,
                    modifier = Modifier.padding(top = 12.dp)
                )

                Spacer(modifier = Modifier.height(35.dp))

                Button(
                    onClick = { /* Handle booking */ },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = colorScheme.primary,
                        contentColor = colorScheme.onPrimary
                    ),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text("ĐẶT VÉ", fontWeight = FontWeight.Bold, fontSize = 16.sp)
                }
            }
        }
    }
}
