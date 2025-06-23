
package com.example.appmoview.presentation.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.runtime.Composable
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
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.appmoview.R
import com.example.appmoview.presentation.viewmodels.MovieViewModel
import com.example.appmoview.utils.ImageHelper

/*
@Composable
fun SearchScreen() {
    val colorScheme = MaterialTheme.colorScheme
    Column(
        modifier = Modifier.fillMaxSize()
            .background(Color.Black)
            .padding(top = 40.dp)
    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
        ) {
            IconButton(
                onClick = {},
            ) {
                Image(
                    painter = painterResource(id = R.drawable.baseline_arrow_back_ios_24),
                    contentDescription = "Back",
                    modifier = Modifier.size(24.dp),
                    colorFilter = ColorFilter.tint(colorScheme.onSurface)
                )
            }

            var searchPhim by remember { mutableStateOf("") }
            OutlinedTextField(
                value = searchPhim,
                onValueChange = {
                    searchPhim = it
                },
                modifier = Modifier.weight(1f),
                shape = RoundedCornerShape(14.dp),
                colors=OutlinedTextFieldDefaults.colors(
                    focusedTextColor = colorScheme.onSurface,
                    unfocusedTextColor = colorScheme.onSurface,
                    focusedBorderColor = colorScheme.onSurface,
                    unfocusedBorderColor = colorScheme.surface,
                    focusedLabelColor = colorScheme.onSurface,
                    unfocusedLabelColor = colorScheme.onSurface,
                    focusedTrailingIconColor = colorScheme.onSurface,
                    unfocusedTrailingIconColor = colorScheme.onSurface,
                ),

                maxLines = 1,
                singleLine = true,

                placeholder = { Text("Tìm kiếm phim theo tên") },
                leadingIcon = {
                    IconButton(
                        onClick = {},
                        colors = IconButtonDefaults.iconButtonColors(
                            contentColor = colorScheme.onSurface
                        )
                    ) {
                        Icon(
                            Icons.Default.Search,
                            contentDescription = "Icon tim kiem"
                        )
                    }
                },
                trailingIcon = {
                    IconButton(
                        onClick = {
                            searchPhim = ""
                        }
                    ) {
                        Icon(
                             Icons.Default.Clear,
                            contentDescription = "Icon xoa het"
                        )
                    }
                },
            )
        }

        //phim gan day

            Text(
                text = "Phim gần đây",
                fontSize = 20.sp,
                color = Color.White,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(start = 10.dp)
            )



        LazyRow(
            modifier = Modifier
                .padding(vertical = 10.dp)
                .padding(start = 10.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(5) {
                Column(
                    modifier = Modifier
                        .height(230.dp)
                        .width(120.dp)
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.ps1),
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f)
                            .clip(RoundedCornerShape(25.dp))
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "TÂY DU KÝ (PHẦN 1)",
                        color = Color.White,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(horizontal = 6.dp)
                    )

                }
            }
        }
    }
}

*/
@Composable
fun SearchScreen(navController: NavController) {
    var query by remember { mutableStateOf("") }

    val recentSearch = listOf("Salaar", "Flash", "Aquaman 2")
    val allMovies = listOf("Aquaman 1", "Aquaman 2", "Salaar", "Flash")

    val searchResults = remember(query) {
        if (query.isNotEmpty()) {
            allMovies.filter { it.contains(query, ignoreCase = true) }
        } else emptyList()
    }
Column(
    modifier = Modifier.fillMaxSize()
        .background(Color.Black)
        .padding(top = 40.dp)
){
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
    ) {
        IconButton(
            onClick = {navController.popBackStack()},
        ) {
            Image(
                painter = painterResource(id = R.drawable.baseline_arrow_back_ios_24),
                contentDescription = "Back",
                modifier = Modifier.size(24.dp),
                colorFilter = ColorFilter.tint(colorScheme.onSurface)
            )
        }

        OutlinedTextField(
            value = query,
            onValueChange = {
                query = it
            },
            modifier = Modifier.weight(1f),
            shape = RoundedCornerShape(14.dp),
            colors=OutlinedTextFieldDefaults.colors(
                focusedTextColor = colorScheme.onSurface,
                unfocusedTextColor = colorScheme.onSurface,
                focusedBorderColor = colorScheme.onSurface,
                unfocusedBorderColor = colorScheme.surface,
                focusedLabelColor = colorScheme.onSurface,
                unfocusedLabelColor = colorScheme.onSurface,
                focusedTrailingIconColor = colorScheme.onSurface,
                unfocusedTrailingIconColor = colorScheme.onSurface,
            ),

            maxLines = 1,
            singleLine = true,

            placeholder = { Text("Tìm kiếm phim theo tên") },
            leadingIcon = {
                IconButton(
                    onClick = {},
                    colors = IconButtonDefaults.iconButtonColors(
                        contentColor = colorScheme.onSurface
                    )
                ) {
                    Icon(
                        Icons.Default.Search,
                        contentDescription = "Icon tim kiem"
                    )
                }
            },
            trailingIcon = {
                IconButton(
                    onClick = {
                        query = ""
                    }
                ) {
                    Icon(
                        Icons.Default.Clear,
                        contentDescription = "Icon xoa het"
                    )
                }
            },
        )
    }

        Spacer(Modifier.height(16.dp))

        if (query.isEmpty()) {
            Text("Recent search", color = Color.White, fontSize = 16.sp)
            Spacer(Modifier.height(8.dp))
            LazyRow {
                items(recentSearch.size) { movieTitle ->
                    MovieItem(title = allMovies[movieTitle])
                }
            }
        } else {
            Text("Search Results", color = Color.White, fontSize = 16.sp)
            Spacer(Modifier.height(8.dp))
            LazyRow {
                items(searchResults.size) { movieTitle ->
                    MovieItem(title = allMovies[movieTitle])
                }
            }
        }
    }
}

@Composable
fun MovieItem(title: String) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.padding(8.dp)
    ) {
        Box(
            modifier = Modifier
//                .size(100.dp)
                .width(110.dp)
                .height(140.dp)
                .clip(RoundedCornerShape(20.dp))
                .background(Color.DarkGray)
        ) {
            Image(
                painter = painterResource(id = R.drawable.phim2),
                contentDescription = null,
                contentScale = ContentScale.Crop, // 👉 Zoom vừa đủ để che hết Box mà không bị méo
                modifier = Modifier
                    .fillMaxSize(),
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
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = title,
            color = Color.White,
            fontSize = 12.sp,
            maxLines = 1
        )
    }
}

@Composable
fun SearchScreen1(navController: NavController, viewModel: MovieViewModel) {
    var query by remember { mutableStateOf("") }

    viewModel.loadMovies()
    val movieList by viewModel.movieList.observeAsState(emptyList())
    val recentSearch = listOf("Salaar", "Flash", "Aquaman 2")

    val searchResults = remember(query, movieList) {
        if (query.isNotEmpty()) {
            movieList.filter { it.movie_name.contains(query, ignoreCase = true) }
        } else emptyList()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
            .padding(top = 40.dp)
    ) {
        // Thanh tìm kiếm
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
        ) {
            IconButton(onClick = { navController.popBackStack() }) {
                Image(
                    painter = painterResource(id = R.drawable.baseline_arrow_back_ios_24),
                    contentDescription = "Back",
                    modifier = Modifier.size(24.dp),
                    colorFilter = ColorFilter.tint(colorScheme.onSurface)
                )
            }

            OutlinedTextField(
                value = query,
                onValueChange = { query = it },
                modifier = Modifier.weight(1f),
                shape = RoundedCornerShape(14.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedTextColor = colorScheme.onSurface,
                    unfocusedTextColor = colorScheme.onSurface,
                    focusedBorderColor = colorScheme.onSurface,
                    unfocusedBorderColor = MaterialTheme.colorScheme.surface,
                ),
                maxLines = 1,
                singleLine = true,
                placeholder = { Text("Tìm kiếm phim theo tên") },
                leadingIcon = {
                    IconButton(onClick = {}) {
                        Icon(Icons.Default.Search, contentDescription = "Tìm kiếm")
                    }
                },
                trailingIcon = {
                    IconButton(onClick = { query = "" }) {
                        Icon(Icons.Default.Clear, contentDescription = "Xóa")
                    }
                },
            )
        }

        Spacer(Modifier.height(16.dp))

        if (query.isEmpty()) {
            Text("Recent search", color = Color.White, fontSize = 16.sp)
            Spacer(Modifier.height(8.dp))
            LazyRow {
                items(recentSearch) { title ->
                    MovieItem(title = title)
                }
            }
        } else {
            Text("Search Results", color = Color.White, fontSize = 16.sp)
            Spacer(Modifier.height(8.dp))
            LazyColumn {
                items(searchResults) { movie ->
                    MovieItem1(
                        title = movie.movie_name,
                        imageUrl = ImageHelper.getMovieImageUrl(movie.movie_picture),
                        onClick = {
                            navigateAndRemoveSelf(navController, movie.movie_id)
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun MovieItem1(title: String, imageUrl: String? = null, onClick: () -> Unit) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.padding(8.dp)
            .clickable { onClick() }
    ) {
        Box(
            modifier = Modifier
                .width(110.dp)
                .height(140.dp)
                .clip(RoundedCornerShape(20.dp))
                .background(Color.DarkGray)
        ) {
            if (imageUrl != null) {
                AsyncImage(
                    model = imageUrl,
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )
            } else {
                Image(
                    painter = painterResource(id = R.drawable.phim2),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )
            }

            Icon(
                imageVector = Icons.Default.PlayArrow,
                contentDescription = null,
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(4.dp),
                tint = Color.White
            )
        }
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            modifier = Modifier.width(110.dp),
            text = title,
            color = Color.White,
            fontSize = 12.sp,
            overflow = TextOverflow.Ellipsis,
            maxLines = 1
        )
    }
}

fun navigateAndRemoveSelf(navController: NavController, id: Int) {
    navController.navigate("detail/$id") {
        popUpTo("search") { inclusive = true } // 👈 xóa luôn màn hình "search"
    }
}


