import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.appmoview.R

data class Film(val title: String, val imageRes: Int)

val actionMovies = listOf(
    Film("Phim 1", R.drawable.phim2),
    Film("Phim 2", R.drawable.ps1),
    Film("Phim 3", R.drawable.phim2),
    Film("Phim 4", R.drawable.ps1),
    Film("Phim 5", R.drawable.phim2),
    Film("Phim 6", R.drawable.ps1),
    Film("Phim 7", R.drawable.phim2),
    Film("Phim 8", R.drawable.ps1),
    Film("Phim 9", R.drawable.phim2),
)

@Composable
fun ShowAllFilm(navController: NavController, films: List<Film> = actionMovies, categoryTitle: String = "Phim hành động") {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color(0xFF2C2C2C))
            .padding(vertical = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 30.dp, bottom = 40.dp, start = 20.dp, end = 16.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = categoryTitle,
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


        LazyColumn(
            modifier = Modifier.padding(horizontal = 12.dp)
        ) {
            val rows = films.chunked(3) // chia thành từng nhóm 3 phim

            items(rows.size) { rowIndex ->
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    val rowFilms = rows[rowIndex]

                    rowFilms.forEach { film ->
                        Column(
                            modifier = Modifier
                                .width(120.dp)
                                .padding(4.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Box(
                                modifier = Modifier
                                    .height(150.dp)
                                    .clip(RoundedCornerShape(12.dp))
                                    .background(Color.DarkGray)
                            ) {
                                Image(
                                    painter = painterResource(id = film.imageRes),
                                    contentDescription = null,
                                    contentScale = ContentScale.Crop,
                                    modifier = Modifier.fillMaxSize(),
                                )
                                Icon(
                                    imageVector = Icons.Default.PlayArrow,
                                    contentDescription = null,
                                    modifier = Modifier
                                        .align(Alignment.BottomEnd)
                                        .padding(4.dp),
                                    tint = Color.White
                                )
                            }

                            Spacer(modifier = Modifier.height(6.dp))

                            Text(
                                text = film.title,
                                color = Color.White,
                                fontSize = 12.sp,
                                maxLines = 1
                            )
                        }
                    }

                    // Nếu số phim không đủ 3, chèn Spacer để căn giữa
                    repeat(3 - rowFilms.size) {
                        Spacer(modifier = Modifier.width(100.dp))
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))
            }
        }
    }
}
