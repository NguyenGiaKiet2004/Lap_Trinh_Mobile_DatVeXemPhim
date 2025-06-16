package com.example.appmoview.presentation.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
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
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.appmoview.R
import com.example.appmoview.presentation.viewmodels.MovieViewModel
import com.example.appmoview.utils.ImageHelper

@Composable
fun DetailScreen(movieId: Int, navController: NavController) {
    val colorScheme = MaterialTheme.colorScheme
    val viewModel: MovieViewModel = viewModel()
    val movieDetail by viewModel.movieDetail.observeAsState()
    val isLoading by viewModel.isLoading.observeAsState(true)

    // Gọi API khi màn hình được hiển thị
    androidx.compose.runtime.LaunchedEffect(movieId) {
        viewModel.loadMovieDetail(movieId)
    }

    if (isLoading || movieDetail == null) {
        // Loading hoặc chưa có dữ liệu
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black),
            contentAlignment = Alignment.Center
        ) {
            Text("Đang tải...", color = Color.White)
        }
    } else {
        val movie = movieDetail!!

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black)
        ) {
            // movie_image
            AsyncImage(
                model = ImageHelper.getMovieImageUrl(movie.movie_picture),
                contentDescription = movie.movie_name,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(350.dp)
                    .clip(RoundedCornerShape(bottomStart = 44.dp, bottomEnd = 44.dp)),
                contentScale = ContentScale.Crop
            )
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
            }

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
                // movie_name
                Text(
                    text = movie.movie_name,
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold,
                    color = colorScheme.onSurface,
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center
                )
                HorizontalDivider(
                    modifier = Modifier.padding(vertical = 4.dp),
                    thickness = 1.dp,
                    color = Color.White
                )
                Spacer(modifier = Modifier.height(25.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Start
                ) {
                    Column(
                        modifier = Modifier.weight(1f)
                    ) {
                        Text(
                            text = stringResource(id = R.string.thoi_luong),
                            fontWeight = FontWeight.SemiBold,
                            color = colorScheme.onSurface
                        )
                        Text("${movie.movie_time} phút", color = colorScheme.onSurface)
                    }

                    Spacer(modifier = Modifier.width(16.dp))

                    Column(
                        modifier = Modifier.weight(1f)
                    ) {
                        Text(
                            text = stringResource(id = R.string.gia_ve),
                            fontWeight = FontWeight.SemiBold,
                            color = colorScheme.onSurface
                        )
                        Text("${movie.movie_price} VNĐ", color = colorScheme.onSurface)
                    }
                }

                Spacer(modifier = Modifier.height(25.dp))

                Text(
                    text = stringResource(id = R.string.the_loai),
                    fontWeight = FontWeight.SemiBold,
                    color = colorScheme.onSurface
                )
                Text(
                    movie.genres.joinToString(", "),
                    color = colorScheme.onSurface,
                    modifier = Modifier.padding(top = 4.dp)
                )

                Spacer(modifier = Modifier.height(25.dp))

                Text(
                    text = stringResource(id = R.string.cot_truyen),
                    fontWeight = FontWeight.SemiBold,
                    color = colorScheme.onSurface
                )
                Text(
                    text = movie.movie_description,
                    color = colorScheme.onSurface,
                    modifier = Modifier.padding(top = 12.dp)
                )

                HorizontalDivider(
                    modifier = Modifier.padding(vertical = 4.dp),
                    thickness = 1.dp,
                    color = Color.White
                )
                Spacer(modifier = Modifier.height(35.dp))

                Button(
                    onClick = { /* Xử lý đặt vé */ },
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
}

