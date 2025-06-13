package com.example.appmoview.presentation.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.hoverable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
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
import androidx.navigation.NavController
import com.example.appmoview.R

@Composable
fun DetailScreen(navController: NavController) {
    val colorScheme = MaterialTheme.colorScheme

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
            .padding(top = 40.dp)
    ) {
        // Ảnh banner
        Image(
            painter = painterResource(id = R.drawable.anhnen),
            contentDescription = "Evil Dead Banner",
            modifier = Modifier
                .fillMaxWidth()
                .height(350.dp)
                .clip(RoundedCornerShape(bottomStart = 44.dp, bottomEnd = 44.dp)),
            contentScale = ContentScale.Crop,
        )

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
        ){
            IconButton(onClick = {},
                modifier = Modifier.align(Alignment.CenterStart)) {
                Image(
                    painter = painterResource(id = R.drawable.baseline_arrow_back_ios_24),
                    contentDescription = "Back",
                    modifier = Modifier.size(24.dp),
                    colorFilter = ColorFilter.tint(colorScheme.onSurface))
            }

        }

        // Nội dung bên dưới

            Column(
                modifier = Modifier
                    .padding(horizontal = 20.dp)
                    .fillMaxSize()
                    .padding(top = 280.dp)
                    .background(
                        color = colorScheme.surface,
                        shape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp)
                    )
                    .padding(horizontal = 20.dp, vertical = 28.dp)
                    .verticalScroll(rememberScrollState()),
            ) {
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
                    modifier = Modifier.padding(vertical = 4.dp)
                )
                Spacer(modifier = Modifier.height(25.dp)) // spacing sau tiêu đề

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Column {
                        Text(stringResource(id = R.string.the_loai), fontWeight = FontWeight.SemiBold, color = colorScheme.onSurface)
                        Text("Phim hành động", color = colorScheme.onSurface)
                    }

                    Column {
                        Text(stringResource(id = R.string.thoi_luong), fontWeight = FontWeight.SemiBold, color = colorScheme.onSurface)
                        Text("1hr:38min", color = colorScheme.onSurface)
                    }
                    Column {
                        Text(stringResource(id = R.string.ngay_phat_hanh), fontWeight = FontWeight.SemiBold, color = colorScheme.onSurface)
                        Text("21 April 2023", color = colorScheme.onSurface)
                    }
                }

                Spacer(modifier = Modifier.height(25.dp))

                Column {
                    Text(stringResource(id = R.string.ngon_ngu), fontWeight = FontWeight.SemiBold, color = colorScheme.onSurface)
                    Text("English", color = colorScheme.onSurface)
                }
                Divider(
                    color = Color.White,
                    thickness = 1.dp,
                    modifier = Modifier.padding(vertical = 4.dp)
                )
                Spacer(modifier = Modifier.height(25.dp))

                Text(stringResource(id = R.string.cot_truyen), fontWeight = FontWeight.SemiBold, color = colorScheme.onSurface)
                Text(
                    "Evil Dead is a 2013 American supernatural horror film directed by Fede Álvarez...",
                    color = colorScheme.onSurface,
                    modifier = Modifier.padding(top = 12.dp)
                )
                Divider(
                    color = Color.White,
                    thickness = 1.dp,
                    modifier = Modifier.padding(vertical = 4.dp)
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
                    Text(stringResource(id = R.string.dat_ve), fontWeight = FontWeight.Bold, fontSize = 16.sp)
                }
            }
    }
}
