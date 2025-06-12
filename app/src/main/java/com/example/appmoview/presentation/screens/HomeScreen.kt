package com.example.appmoview.presentation.screens

import androidx.compose.foundation.Image // Để hiển thị hình ảnh
import androidx.compose.foundation.background // Để đặt màu nền
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.* // Các Modifier để bố cục (Column, Row, Box, Spacer, Padding, etc.)
import androidx.compose.foundation.lazy.LazyRow // Để tạo hàng ngang có thể cuộn hiệu quả
import androidx.compose.foundation.lazy.items // Helper cho LazyRow để lặp qua danh sách
import androidx.compose.foundation.shape.RoundedCornerShape // Để bo tròn góc các thành phần
import androidx.compose.material.* // Các Composable từ Material Design (Scaffold, AppBar, Button, Text, Icon, etc.)
import androidx.compose.material.icons.Icons // Bộ biểu tượng Material Design mặc định
import androidx.compose.material.icons.filled.Home // Biểu tượng Home
import androidx.compose.material.icons.filled.Person // Biểu tượng Person
import androidx.compose.material.icons.filled.Search // Biểu tượng Search
import androidx.compose.runtime.Composable // Đánh dấu hàm là Composable Function
import androidx.compose.ui.Alignment // Để căn chỉnh các thành phần con
import androidx.compose.ui.Modifier // Đối tượng để thay đổi giao diện/hành vi của Composable
import androidx.compose.ui.draw.clip // Để cắt bo tròn hình dạng
import androidx.compose.ui.graphics.Color // Để định nghĩa màu sắc
import androidx.compose.ui.layout.ContentScale // Cách hình ảnh được điều chỉnh kích thước
import androidx.compose.ui.res.painterResource // Để tải hình ảnh từ tài nguyên drawable
import androidx.compose.ui.text.font.FontWeight // Để định nghĩa độ đậm của chữ
import androidx.compose.ui.tooling.preview.Preview // Để xem trước Composable trong Android Studio
import androidx.compose.ui.unit.dp // Đơn vị mật độ điểm ảnh (Density-independent Pixels)
import androidx.compose.ui.unit.sp // Đơn vị kích thước văn bản (Scale-independent Pixels)
import androidx.navigation.NavController
import com.example.appmoview.R
// --- 1. Data Models (Mô hình dữ liệu) ---
// Đây là các lớp dữ liệu đơn giản để đại diện cho thông tin chúng ta sẽ hiển thị.
// Trong một ứng dụng thực tế, dữ liệu này có thể đến từ API, cơ sở dữ liệu, v.v.

data class Movie(
    val id: Int,         // ID duy nhất của phim
    val title: String,   // Tên phim
    val imageUrl: Int,   // ID tài nguyên drawable của poster phim
    val genre: String? = null // Thể loại phim (có thể có hoặc không)
)

data class Section(
    val title: String,      // Tiêu đề của một phần (ví dụ: "Phim hành động")
    val movies: List<Movie> // Danh sách các phim trong phần đó
)

// --- 2. MovieAppScreen (Màn hình chính của ứng dụng) ---
// Đây là Composable chính tạo nên toàn bộ giao diện màn hình.
// Nó sử dụng Scaffold để cấu trúc màn hình với Top Bar, Bottom Bar và Content.

@Composable
fun HomeScreen(navController: NavController) {
    Scaffold(
        // topBar: Phần trên cùng của màn hình (App Bar)
        topBar = {
            TopAppBarContent() // Gọi Composable để tạo nội dung cho Top Bar
        },
        // bottomBar: Phần dưới cùng của màn hình (Navigation Bar)
        bottomBar = {
            BottomNavigationBar() // Gọi Composable để tạo nội dung cho Bottom Bar
        },
        // content: Phần chính của màn hình, nơi các thành phần UI khác sẽ được đặt.
        // paddingValues: Tham số này được cung cấp bởi Scaffold để đảm bảo nội dung không bị che bởi Top/Bottom Bar.
        content = { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize() // Chiếm toàn bộ kích thước của màn hình
                    .background(Color(0xFF1A1A1A)) // Đặt màu nền tối giống trong ảnh
                    .padding(paddingValues) // Áp dụng padding từ Scaffold
                    .padding(horizontal = 16.dp) // Thêm padding ngang cho nội dung chính
            ) {
                Spacer(modifier = Modifier.height(16.dp)) // Khoảng trống dọc
                // Main featured movies (Phần phim nổi bật chính)
                FeaturedMoviesSection() // Gọi Composable để hiển thị phần phim nổi bật
                Spacer(modifier = Modifier.height(24.dp)) // Khoảng trống dọc

                // Action Movies Section (Phần phim hành động)
                MovieSection(
                    section = Section(
                        title = "Phim hành động", // Tiêu đề của phần
                        movies = listOf(
                            Movie(101, "SALAAR (PART 1)", R.drawable.ps1), // Phim 1
                            Movie(102, "FLASH (2023)", R.drawable.ps1),     // Phim 2
                            Movie(103, "AQUAMAN", R.drawable.ps1),         // Phim 3
                            // Thêm nhiều phim khác nếu cần
                        )
                    )
                )
            }
        }
    )
}

// Giải thích về `Scaffold`:
// `Scaffold` là một Composable cung cấp cấu trúc cơ bản cho màn hình Material Design.
// Nó giúp dễ dàng kết hợp các thành phần như TopAppBar, BottomAppBar, FloatingActionButton, Drawer, và SnackBar.
// Bằng cách sử dụng `Scaffold`, bạn không cần phải lo lắng về việc quản lý vị trí của các thành phần này.

// --- 3. TopAppBarContent (Nội dung của Top Bar) ---
// Composable này tạo ra phần trên cùng của màn hình, bao gồm giờ, biểu tượng, lời chào và nút tìm kiếm.

@Composable
fun TopAppBarContent() {

    // Hàng dưới cùng: Lời chào và nút tìm kiếm
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 50.dp, bottom = 16.dp, start = 16.dp, end = 16.dp), // Padding dọc
        horizontalArrangement = Arrangement.SpaceBetween, // Căn chỉnh cách đều
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "Chào, Tuấn",
            color = Color.White,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold
        )
        IconButton(onClick = { /* Handle search click */ }) { // Nút tìm kiếm
            Icon(
                imageVector = Icons.Default.Search, // Sử dụng biểu tượng tìm kiếm mặc định
                contentDescription = "Search",
                tint = Color.White // Màu của biểu tượng
            )
        }
    }
}

// Giải thích về `Row`, `Column`, `Box`, `Modifier`:
// - `Row`: Bố cục các thành phần con theo chiều ngang (từ trái sang phải).
// - `Column`: Bố cục các thành phần con theo chiều dọc (từ trên xuống dưới).
// - `Box`: Xếp chồng các thành phần con lên nhau (như FrameLayout trong XML). Nó cũng có thể được dùng làm container đơn giản.
// - `Modifier`: Một đối tượng mạnh mẽ được dùng để tùy chỉnh giao diện và hành vi của Composable.
//   - `.fillMaxWidth()`, `.fillMaxSize()`, `.height()`, `.width()`, `.size()`: Định nghĩa kích thước.
//   - `.padding()`: Thêm khoảng đệm bên trong.
//   - `.background()`: Đặt màu nền.
//   - `.clip()`: Cắt Composable theo một hình dạng (ví dụ: bo tròn góc).
//   - `.clickable()`: Làm cho Composable có thể nhấp được.
//   - `.align()`, `.horizontalArrangement`, `verticalAlignment`: Căn chỉnh các thành phần.

// --- 4. FeaturedMoviesSection (Phần phim nổi bật) ---
// Hiển thị một hàng các phim nổi bật có thể cuộn.

@Composable
fun FeaturedMoviesSection() {
    // Dữ liệu giả định cho các phim nổi bật
    val featuredMovies = listOf(
        Movie(1, "Avengers: Endgame", R.drawable.ps1),
        Movie(2, "Pushpa-2", R.drawable.ps1, genre = "Adventure"),
        Movie(3, "Spider-Man: No Way Home", R.drawable.ps1)
    )

    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(16.dp), // Các item cách nhau 16dp
        contentPadding = PaddingValues(horizontal = 0.dp) // Padding cho nội dung của LazyRow
    ) {
        // Duyệt qua danh sách phim nổi bật và hiển thị từng FeaturedMovieItem
        items(featuredMovies) { movie ->
            FeaturedMovieItem(movie = movie)
        }
    }
    Spacer(modifier = Modifier.height(16.dp)) // Khoảng trống dọc

    // Dot indicators for featured movies (Các chấm chỉ báo vị trí phim)
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center, // Căn giữa các chấm
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Lặp để tạo các chấm. Trong thực tế, bạn sẽ cần logic để biết chấm nào đang được chọn.
        for (i in featuredMovies.indices) {
            Box(
                modifier = Modifier
                    .size(8.dp)
                    .clip(RoundedCornerShape(4.dp)) // Bo tròn thành hình tròn
                    .background(if (i == 1) Color.White else Color.Gray.copy(alpha = 0.5f)) // Chấm thứ 2 (Pushpa-2) màu trắng, các chấm khác màu xám
                    .padding(horizontal = 4.dp) // Padding ngang cho mỗi chấm
            )
            Spacer(modifier = Modifier.width(4.dp)) // Khoảng trống giữa các chấm
        }
    }
}

// Giải thích về `LazyRow`:
// `LazyRow` (hoặc `LazyColumn`) là các Composable hiệu quả để hiển thị một danh sách lớn các mục.
// Chúng chỉ render (vẽ) các mục đang hiển thị trên màn hình, giúp tiết kiệm bộ nhớ và cải thiện hiệu suất.
// Điều này rất quan trọng khi bạn có hàng trăm hoặc hàng nghìn mục trong danh sách.
// `items()` là một hàm mở rộng của `LazyListScope` giúp dễ dàng duyệt qua một `List` và hiển thị từng mục.

// --- 5. FeaturedMovieItem (Item phim nổi bật) ---
// Hiển thị một poster phim lớn hơn cho phần nổi bật.

@Composable
fun FeaturedMovieItem(movie: Movie) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally, // Căn giữa nội dung theo chiều ngang
        modifier = Modifier
            .width(180.dp) // Chiều rộng cố định
            .height(260.dp) // Chiều cao cố định
            .clip(RoundedCornerShape(12.dp)) // Bo tròn góc
            .background(Color.DarkGray) // Nền màu xám đậm
            .clickable { /* Handle movie click */ } // Làm cho item có thể nhấp
    ) {
        Image(
            painter = painterResource(id = movie.imageUrl), // Tải hình ảnh từ ID tài nguyên
            contentDescription = movie.title,
            contentScale = ContentScale.Crop, // Cắt hình ảnh để lấp đầy không gian
            modifier = Modifier
                .fillMaxWidth() // Chiếm toàn bộ chiều rộng của Column
                .weight(1f) // Chiếm phần còn lại của chiều cao của Column
                .clip(RoundedCornerShape(12.dp)) // Bo tròn góc hình ảnh
        )
        if (movie.genre != null) { // Nếu có thể loại phim
            Text(
                text = movie.genre,
                color = Color.White.copy(alpha = 0.7f), // Màu trắng trong suốt
                fontSize = 12.sp,
                modifier = Modifier.padding(top = 4.dp, bottom = 8.dp) // Padding trên và dưới
            )
        }
    }
}

// Giải thích về `ContentScale.Crop` và `weight()`:
// - `ContentScale.Crop`: Chỉ định rằng hình ảnh sẽ được cắt (cropped) để lấp đầy không gian được cung cấp, duy trì tỷ lệ khung hình của hình ảnh.
// - `weight(1f)`: Trong một `Column` hoặc `Row`, `weight` cho phép một thành phần chiếm tỷ lệ không gian còn lại. `1f` nghĩa là nó sẽ chiếm tất cả không gian còn lại sau khi các thành phần khác đã được đặt.

// --- 6. MovieSection (Phần danh sách phim) ---
// Composable chung để hiển thị một phần phim, ví dụ "Phim hành động".

@Composable
fun MovieSection(section: Section) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween, // Căn chỉnh tiêu đề và nút "See All" cách đều
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = section.title, // Tiêu đề của phần (ví dụ: "Phim hành động")
                color = Color.White,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )
            TextButton(onClick = { /* Handle "See All" click */ }) { // Nút "See All"
                Text(
                    text = "See All >",
                    color = Color.LightGray,
                    fontSize = 14.sp
                )
            }
        }
        Spacer(modifier = Modifier.height(12.dp)) // Khoảng trống dọc
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(12.dp), // Các item cách nhau 12dp
            contentPadding = PaddingValues(horizontal = 0.dp)
        ) {
            // Duyệt qua danh sách phim trong phần và hiển thị từng MovieCard
            items(section.movies) { movie ->
                MovieCard(movie = movie)
            }
        }
    }
}

// --- 7. MovieCard (Thẻ phim nhỏ hơn) ---
// Hiển thị một thẻ phim với poster, tên phim và nút play nhỏ.

@Composable
fun MovieCard(movie: Movie) {
    Column(
        modifier = Modifier
            .width(120.dp) // Chiều rộng cố định
            .clip(RoundedCornerShape(8.dp)) // Bo tròn góc
            .background(Color.DarkGray) // Nền màu xám đậm
            .clickable { /* Handle movie click */ }
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(160.dp) // Chiều cao cố định cho phần hình ảnh
                .clip(RoundedCornerShape(8.dp))
        ) {
            Image(
                painter = painterResource(id = movie.imageUrl),
                contentDescription = movie.title,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize() // Chiếm toàn bộ Box
            )
            // Play icon overlay (Nút play phủ lên hình ảnh)
            Box(
                modifier = Modifier
                    .align(Alignment.BottomEnd) // Căn chỉnh ở góc dưới bên phải
                    .padding(8.dp) // Padding xung quanh nút play
                    .size(30.dp) // Kích thước nút play
                    .clip(RoundedCornerShape(15.dp)) // Bo tròn thành hình tròn
                    .background(Color.Black.copy(alpha = 0.6f)) // Nền đen trong suốt
                    .clickable { /* Handle play click */ },
                contentAlignment = Alignment.Center // Căn giữa biểu tượng play bên trong Box này
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ps1), // Biểu tượng play (cần tạo trong drawable)
                    contentDescription = "Play",
                    tint = Color.White,
                    modifier = Modifier.size(16.dp) // Kích thước biểu tượng play
                )
            }
        }
        Text(
            text = movie.title,
            color = Color.White,
            fontSize = 14.sp,
            fontWeight = FontWeight.Medium,
            modifier = Modifier.padding(8.dp) // Padding xung quanh tên phim
        )
    }
}

// --- 8. BottomNavigationBar (Thanh điều hướng dưới cùng) ---
// Tạo thanh điều hướng ở cuối màn hình với các biểu tượng và nhãn.

@Composable
fun BottomNavigationBar() {
    BottomNavigation(
        backgroundColor = Color(0xFF2C2C2C), // Màu nền đậm hơn cho bottom nav
        elevation = 8.dp // Độ nổi (shadow)
    ) {
        BottomNavigationItem(
            icon = { Icon(Icons.Default.Home, contentDescription = "Home", tint = Color.White) },
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
            icon = { Icon(Icons.Default.Person, contentDescription = "Profile", tint = Color.Gray) },
            label = { Text("Cá nhân", color = Color.Gray) },
            selected = false,
            onClick = { /* Handle profile click */ }
        )
    }
}

// Giải thích về `BottomNavigation` và `BottomNavigationItem`:
// - `BottomNavigation`: Một Composable tuân thủ Material Design để tạo thanh điều hướng dưới cùng.
// - `BottomNavigationItem`: Một mục trong `BottomNavigation`, bao gồm biểu tượng, nhãn và trạng thái `selected`.


// --- 10. Drawables (Tài nguyên hình ảnh) ---
// Đây là những gì bạn cần thêm vào thư mục `res/drawable` của dự án.
// Bạn có thể kéo thả các tệp hình ảnh (.png, .jpg) vào đây hoặc tạo Vector Asset.

// R.drawable.avengers_endgame_poster
// R.drawable.pushpa2_poster
// R.drawable.spiderman_poster
// R.drawable.salaar_poster
// R.drawable.flash_poster
// R.drawable.aquaman_poster
// R.drawable.ic_play (một hình tam giác nhỏ cho nút play)
// R.drawable.ic_ticket (một biểu tượng giống vé xem phim)
