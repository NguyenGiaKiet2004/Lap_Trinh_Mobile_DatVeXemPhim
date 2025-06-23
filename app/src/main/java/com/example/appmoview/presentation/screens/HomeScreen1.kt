package com.example.appmoview.presentation.screens

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.appmoview.R
import com.example.appmoview.domain.model.MovieRequest
import com.example.appmoview.presentation.viewmodels.MovieViewModel
import com.example.appmoview.utils.ImageHelper
import com.example.appmoview.utils.getActMovie
import com.example.appmoview.utils.getAnimeMovie


@Composable
fun HomeScreen1(navController: NavController) {

    val viewModel: MovieViewModel = viewModel()
    val isLoading by viewModel.isLoading.observeAsState(true)
    val movies by viewModel.movieList.observeAsState(emptyList())

    // Load dữ liệu khi màn hình được hiển thị lần đầu
    LaunchedEffect(Unit) {
        viewModel.loadMovies()
    }

    androidx.compose.material.Scaffold(
        backgroundColor = Color.Black,
        bottomBar = {
            BottomNavigationBar1(
                selectedIndex = 0, // Cá nhân được chọn
                onItemSelected = { index ->
                    when (index) {
                        0 -> {}
                        1 -> navController.navigate("ticket")
                        2 -> navController.navigate("account")
                    }
                }
            ) // Tách riêng thanh điều hướng
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues) // Thêm padding từ Scaffold để tránh bị đè
        ) {

            TimKiem(navController)

            LazyColumn(
                modifier = Modifier.fillMaxSize()
            ) {
                item {
                    if (isLoading) {
                        PhimHotSkeleton()
                    } else {
                        PhimHot(movies
                            .sortedByDescending { it.movie_id } // Sắp xếp giảm dần theo id
                            .take(3),navController)
                    }
                }
                item {
                    if (isLoading) {
                        PhimTheoTheLoaiSkeleton("Phim hành động")
                    } else {
                        PhimTheoTheLoai("Phim hành động", getActMovie(movies),navController)
                    }
                }
                item {
                    if (isLoading) {
                        PhimTheoTheLoaiSkeleton("Phim hoạt hình")
                    } else {
                        PhimTheoTheLoai("Phim hoạt hình", getAnimeMovie(movies),navController)
                    }
                }
            }
        }
    }
}

fun navigation(navController: NavController,id:Int){
    navController.navigate("detail/${id}")
}


@Composable
fun TimKiem(navController: NavController) {
    val context = LocalContext.current

    // Đọc username từ SharedPreferences
    val username = remember {
        val sharedPref = context.getSharedPreferences("auth_prefs", Context.MODE_PRIVATE)
        sharedPref.getString("username", "Người dùng") ?: "Người dùng"
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .padding(top = 50.dp)
            .clickable{ navController.navigate("account") },
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "Chào, $username",
            color = Color.White,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold
        )
        IconButton(
            onClick = { navController.navigate("search") },
            modifier = Modifier
                .background(Color.DarkGray, shape = RoundedCornerShape(25.dp))
        ) {
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = "Search",
                tint = Color.White,
            )
        }
    }
}


@Composable
fun PhimHot(movies: List<MovieRequest>, navController: NavController) {
    val listState = rememberLazyListState()

    var centerItemIndex by remember { mutableStateOf(0) }

    // Theo dõi vị trí scroll để tính item giữa
    LaunchedEffect(listState) {
        snapshotFlow {
            listState.firstVisibleItemIndex to listState.firstVisibleItemScrollOffset
        }.collect { (index, offset) ->
            val offsetItem = if (offset > 100) 1 else 0
            centerItemIndex = (index + offsetItem).coerceIn(0, movies.lastIndex)
        }
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 20.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        LazyRow(
            state = listState,
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            contentPadding = PaddingValues(horizontal = 16.dp),
        ) {
            items(movies.size) { index ->
                val movie = movies[index]
                Column(
                    modifier = Modifier
                        .height(300.dp)
                        .width(200.dp)
                        .clickable {
                            navigation(navController,movie.movie_id)
                        }
                ) {
                    Image(
                        painter = rememberAsyncImagePainter(
                            model = ImageHelper.getMovieImageUrl(movie.movie_picture)
                        ),
                        contentDescription = movie.movie_name,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f)
                            .clip(RoundedCornerShape(12.dp))
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        if (movies.isNotEmpty()) {
            val movie = movies[centerItemIndex]
            Text(
                text = movie.movie_name,
                fontSize = 20.sp,
                color = Color.White,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(horizontal = 16.dp)
            )
            Text(
                text = "${movie.movie_time} phút",
                color = Color.LightGray
            )
        }
    }
}


@Composable
fun PhimHotSkeleton() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 20.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            contentPadding = PaddingValues(horizontal = 16.dp),
        ) {
            items(5) {
                Column(
                    modifier = Modifier
                        .height(300.dp)
                        .width(200.dp)
                ) {
                    // Khối giả hình ảnh
                    Spacer(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f)
                            .clip(RoundedCornerShape(12.dp))
                            .background(Color.Gray.copy(alpha = 0.3f))
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Khối giả tên phim
        Spacer(
            modifier = Modifier
                .height(20.dp)
                .width(150.dp)
                .clip(RoundedCornerShape(4.dp))
                .background(Color.Gray.copy(alpha = 0.3f))
        )
        Spacer(modifier = Modifier.height(4.dp))
        Spacer(
            modifier = Modifier
                .height(16.dp)
                .width(100.dp)
                .clip(RoundedCornerShape(4.dp))
                .background(Color.Gray.copy(alpha = 0.3f))
        )
    }
}


@Composable
fun PhimTheoTheLoai(
    tieuDe: String,
    movies: List<MovieRequest>,
    navController: NavController
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 20.dp)
            .background(color = Color(0xFF2C2C2C)),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Row(
            modifier = Modifier
                .padding(top = 5.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = tieuDe,
                fontSize = 20.sp,
                color = Color.White,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(start = 10.dp)
            )
            TextButton(onClick = { navController.navigate("show") }) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(text = "Xem tất cả", color = Color.Yellow)
                    Icon(
                        painter = painterResource(id = R.drawable.outline_keyboard_arrow_right_24),
                        contentDescription = null,
                        tint = Color.Yellow
                    )
                }
            }
        }

        LazyRow(
            modifier = Modifier
                .padding(vertical = 10.dp)
                .padding(start = 10.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(movies.size) { index ->
                val movie = movies[index]
                Column(
                    modifier = Modifier
                        .height(250.dp)
                        .width(150.dp)
                        .clickable {
                            navigation(navController,movie.movie_id)
                    }
                ) {
                    Image(
                        painter = rememberAsyncImagePainter(
                            model = ImageHelper.getMovieImageUrl(movie.movie_picture)
                        ),
                        contentDescription = movie.movie_name,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(180.dp)
                            .clip(RoundedCornerShape(35.dp))
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = movie.movie_name,
                        color = Color.White,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier
                            .padding(horizontal = 6.dp)
                            .fillMaxWidth()
                    )
                }
            }
        }
    }
}

@Composable
fun PhimTheoTheLoaiSkeleton(tieuDe: String) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 20.dp)
            .background(color = Color(0xFF2C2C2C)),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Row(
            modifier = Modifier
                .padding(top = 5.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = tieuDe,
                fontSize = 20.sp,
                color = Color.White,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(start = 10.dp)
            )
            TextButton(onClick = { }) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(text = "Xem tất cả", color = Color.Yellow)
                    Icon(
                        painter = painterResource(id = R.drawable.outline_keyboard_arrow_right_24),
                        contentDescription = null,
                        tint = Color.Yellow
                    )
                }
            }
        }

        LazyRow(
            modifier = Modifier
                .padding(vertical = 10.dp)
                .padding(start = 10.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(5) {
                Column(
                    modifier = Modifier
                        .height(250.dp)
                        .width(150.dp)
                ) {
                    Spacer(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(180.dp)
                            .clip(RoundedCornerShape(35.dp))
                            .background(Color.Gray.copy(alpha = 0.3f))
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Spacer(
                        modifier = Modifier
                            .height(20.dp)
                            .width(100.dp)
                            .clip(RoundedCornerShape(4.dp))
                            .background(Color.Gray.copy(alpha = 0.3f))
                    )
                }
            }
        }
    }
}




@Composable
fun BottomNavigationBar1(
    selectedIndex: Int,
    onItemSelected: (Int) -> Unit
) {
    BottomNavigation(
        backgroundColor = Color(0xFF2C2C2C),
        elevation = 8.dp
    ) {
        BottomNavigationItem(
            icon = {
                Icon(
                    Icons.Default.Home,
                    contentDescription = "Home",
                    tint = if (selectedIndex == 0) Color.White else Color.Gray
                )
            },
            label = {
                Text("Trang chủ", color = if (selectedIndex == 0) Color.White else Color.Gray)
            },
            selected = selectedIndex == 0,
            onClick = { onItemSelected(0) }
        )

        BottomNavigationItem(
            icon = {
                Icon(
                    painter = painterResource(id = R.drawable.outline_ballot_24),
                    contentDescription = "Tickets",
                    tint = if (selectedIndex == 1) Color.White else Color.Gray
                )
            },
            label = {
                Text("Vé", color = if (selectedIndex == 1) Color.White else Color.Gray)
            },
            selected = selectedIndex == 1,
            onClick = { onItemSelected(1) }
        )

        BottomNavigationItem(
            icon = {
                Icon(
                    Icons.Default.Person,
                    contentDescription = "Profile",
                    tint = if (selectedIndex == 2) Color.White else Color.Gray
                )
            },
            label = {
                Text("Cá nhân", color = if (selectedIndex == 2) Color.White else Color.Gray)
            },
            selected = selectedIndex == 2,
            onClick = { onItemSelected(2) }
        )
    }
}


/*@Composable
fun SlideShow() {
    val images = listOf(
        R.drawable.ps1, // Thay bằng ID tài nguyên ảnh
        R.drawable. ps1,
        R.drawable.phim2
    )

    val pagerState = rememberPagerState(pageCount = { images.size })

    LaunchedEffect(Unit) {
        while (true) {
            delay(3000) // Trượt sau mỗi 3 giây
            pagerState.animateScrollToPage((pagerState.currentPage + 1) % images.size)
        }
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        HorizontalPager(state = pagerState) { page ->
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .padding(horizontal = 8.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(Color.LightGray)
            ) {
                Image(
                    painter = painterResource(id = images[page]),
                    contentDescription = "Slide image $page",
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )
            }
        }

    }
}*/



