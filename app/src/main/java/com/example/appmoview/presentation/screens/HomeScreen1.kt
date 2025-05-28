package com.example.appmoview.presentation.screens

import android.R.attr.contentDescription
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.hoverable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.appmoview.R

@Composable
fun HomeScreen1() {
    androidx.compose.material.Scaffold(
        backgroundColor = Color.Black,
        bottomBar = {
            BottomNavigationBar1() // Tách riêng thanh điều hướng
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues) // Thêm padding từ Scaffold để tránh bị đè
        ) {
            TimKiem()
            LazyColumn(
                modifier = Modifier.fillMaxSize()
            ) {
                item {
                    PhimHot()
                }
                item {
                    PhimTheoTheLoai("Phim hành động", R.drawable.phim2, "Pushpa-2", "Adventure", "180 phút")
                }
                item{
                    PhimTheoTheLoai("Phim Anime", R.drawable.phim2, "One Piece", "Shounen", "24 phút")
                }
                item{
                    PhimTheoTheLoai("Phim Cổ Trang", R.drawable.phim2, "Tam Quốc", "Cổ trang", "120 phút")
                }
            }
        }
    }
}


@Composable
fun TimKiem(){

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .padding(top = 50.dp),
            horizontalArrangement = Arrangement.SpaceBetween, // Căn chỉnh cách đều
            verticalAlignment = Alignment.CenterVertically
        ){
            Text(
                text = "Chào, Kiệt",
                modifier = Modifier,
                color = Color.White,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            )
            IconButton(onClick = {}){
                Icon(
                    imageVector = Icons.Default.Search, // Sử dụng biểu tượng tìm kiếm mặc định
                    contentDescription = "Search",
                    tint = Color.White // Màu của biểu tượng
                )
            }
        }
    }

@Composable
fun PhimHot(){
    Column(
        modifier= Modifier
            .fillMaxWidth()
            .padding(top = 20.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(16.dp), // Các item cách nhau 16dp
            contentPadding = androidx.compose.foundation.layout.PaddingValues(horizontal = 0.dp), // Padding cho nội dung của LazyRow
        ) {
            items(3) {
                Column(
                    modifier = Modifier
                        .height(300.dp)
                        .width(200.dp)
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.ps1),
                        contentDescription = null,
                        contentScale = ContentScale.Crop, // Cắt hình ảnh để lấp đầy không gian modifier = Modifier
                        modifier = Modifier
                            .fillMaxWidth() // Chiếm toàn bộ chiều rộng của Column
                            .weight(1f) // Chiếm phần còn lại của chiều cao của Column
                            .clip(RoundedCornerShape(12.dp))// Bo tròn góc hình ảnh
                    )
                }
            }
        }
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "Pushpa-2",
            fontSize = 20.sp,
            color = Color.White,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(horizontal = 16.dp)
            )
        Text(
            text = "Adventure",
            color = Color.LightGray
        )
    }
}

@Composable
fun PhimTheoTheLoai(
    tieuDe: String,
    anhResId: Int,
    tenPhim: String,
    theLoai: String,
    thoiLuong: String
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 20.dp)
            .background(color = Color.DarkGray),
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
            TextButton(onClick = {}) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
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
                    Image(
                        painter = painterResource(id = anhResId),
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f)
                            .clip(RoundedCornerShape(35.dp))
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = tenPhim,
                        color = Color.White,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(horizontal = 6.dp)
                    )
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(end = 6.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = theLoai,
                            color = Color.White,
                            modifier = Modifier.padding(horizontal = 6.dp)
                        )
                        Text(
                            text = thoiLuong,
                            color = Color.White,
                            modifier = Modifier.padding(horizontal = 6.dp)
                        )
                    }
                }
            }
        }
    }
}


@Composable
fun BottomNavigationBar1() {
    BottomNavigation(
        backgroundColor = Color(0xFF2C2C2C), // Màu nền đậm hơn cho bottom nav
        elevation = 8.dp // Độ nổi (shadow)
    ) {
        BottomNavigationItem(
            icon = {
                Icon(
                    Icons.Default.Home,
                    contentDescription = "Home",
                    tint = Color.White
                )
            },
            label = { Text("Trang chủ", color = Color.White) },
            selected = true, // Đặt là true cho mục đang chọn (Trang chủ)
            onClick = { /* Handle home click */ }
        )
        BottomNavigationItem(
            icon = {
                Icon(
                    painter = painterResource(id = R.drawable.outline_ballot_24), // Biểu tượng vé (cần tạo trong drawable)
                    contentDescription = "Tickets",
                    tint = Color.Gray // Màu xám cho mục không chọn
                )
            },
            label = { Text("Vé", color = Color.Gray) },
            selected = false,
            onClick = { /* Handle tickets click */ }
        )
        BottomNavigationItem(
            icon = {
                Icon(
                    Icons.Default.Person,
                    contentDescription = "Profile",
                    tint = Color.Gray
                )
            },
            label = { Text("Cá nhân", color = Color.Gray) },
            selected = false,
            onClick = { /* Handle profile click */ }
        )
    }
}



