package com.example.appmoview.presentation.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.appmoview.R
import com.example.appmoview.domain.model.Showtime
import com.example.appmoview.presentation.viewmodels.MovieViewModel
import com.example.appmoview.utils.ImageHelper

@Composable
fun ShowtimeScreen(movieId: Int,viewModel: MovieViewModel, navController: NavController) {
    val isLoading by viewModel.isLoading.observeAsState(true)
    val movieDetail by viewModel.movieDetail.observeAsState()
    val showtimes by viewModel.showtimeList.observeAsState(emptyList())

    LaunchedEffect(Unit) {
        viewModel.loadMovieDetail(movieId)
        viewModel.getShowTimes()
    }

    val filteredShowtimes = showtimes.filter { it.movies.movie_id == movieId }

    if (isLoading || movieDetail == null) {
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

    val movie = movieDetail!!
    val grouped = filteredShowtimes.groupBy { it.startTime.substring(0, 10) }
    val availableDates = grouped.keys.sorted()
    var selectedDate by remember { mutableStateOf(availableDates.firstOrNull() ?: "") }
    var selectedShowtime by remember { mutableStateOf<Showtime?>(null) }

    val showtimesForSelectedDate = grouped[selectedDate] ?: emptyList()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
    ) {
        // Header image
        AsyncImage(
            model = ImageHelper.getMovieImageUrl(movie.movie_picture),
            contentDescription = movie.movie_name,
            modifier = Modifier
                .fillMaxWidth()
                .height(350.dp)
                .clip(RoundedCornerShape(bottomStart = 44.dp, bottomEnd = 44.dp)),
            contentScale = ContentScale.Crop
        )

        // Back button
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
                    colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onSurface)
                )
            }
        }

        // Nội dung bên dưới ảnh
        Column(
            modifier = Modifier
                .padding(horizontal = 20.dp)
                .fillMaxSize()
                .padding(top = 280.dp)
                .background(
                    color = MaterialTheme.colorScheme.surface,
                    shape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp)
                )
                .padding(horizontal = 20.dp, vertical = 28.dp)
                .verticalScroll(rememberScrollState()),
        ) {
            // Tên phim
            Text(
                text = movie.movie_name,
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(20.dp))

            // Mô tả phim
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = movie.movie_description,
                color = colorScheme.onSurface,
                modifier = Modifier.padding(top = 12.dp)
            )

            Spacer(modifier = Modifier.height(12.dp))

            // Đường kẻ trắng
            HorizontalDivider(
                modifier = Modifier.padding(vertical = 4.dp),
                thickness = 1.dp,
                color = Color.White
            )

            Spacer(modifier = Modifier.height(16.dp))


            // Chọn ngày
            Text(text = "Chọn ngày", fontSize = 16.sp, fontWeight = FontWeight.Medium,color = colorScheme.onSurface)
            Spacer(modifier = Modifier.height(6.dp))
            LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                items(availableDates) { date ->
                    val formatted = date.substring(8, 10) + "/" + date.substring(5, 7)
                    Box(
                        modifier = Modifier
                            .background(
                                if (date == selectedDate) MaterialTheme.colorScheme.primary else Color.DarkGray,
                                shape = RoundedCornerShape(10.dp)
                            )
                            .clickable { selectedDate = date }
                            .padding(vertical = 8.dp, horizontal = 12.dp)
                    ) {
                        Text(formatted, color = Color.White)
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Chọn giờ
            Text("Chọn giờ", fontSize = 16.sp, fontWeight = FontWeight.Medium,color = colorScheme.onSurface)
            Spacer(modifier = Modifier.height(6.dp))
            LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                items(showtimesForSelectedDate) { st ->
                    val time = st.startTime.substring(11, 16)
                    Box(
                        modifier = Modifier
                            .background(
                                if (selectedShowtime?.showtimeId == st.showtimeId)
                                    MaterialTheme.colorScheme.primary
                                else Color.Gray,
                                shape = RoundedCornerShape(10.dp)
                            )
                            .clickable { selectedShowtime = st }
                            .padding(vertical = 8.dp, horizontal = 16.dp)
                    ) {
                        Text(time, color = Color.White)
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Nút tiếp tục
            Button(
                onClick = {
                    selectedShowtime?.let {
                        viewModel.setSelectedShowtime(it)
                        navController.navigate("seat_booking_screen")
                } },
                enabled = selectedShowtime != null,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                shape = RoundedCornerShape(16.dp)
            ) {
                Text("TIẾP TỤC", fontSize = 16.sp, fontWeight = FontWeight.Bold)
            }
        }
    }
}


