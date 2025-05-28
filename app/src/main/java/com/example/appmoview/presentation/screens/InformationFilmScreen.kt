package com.example.appmoview.presentation.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.appmoview.R

@Composable
fun InformationFilmScreen(){
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color=Color.Black)
    ){
        AnhNen()
    }


}

@Composable
fun AnhNen(){
    Box(){
        Image(
            painter = painterResource(id= R.drawable.anhnen),
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(60.dp)),
            contentScale = ContentScale.Crop
        )
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(700.dp)
                .padding(horizontal=20.dp)
                .padding(top=300.dp)
                .background(color = Color.DarkGray)
                .clip(RoundedCornerShape(60.dp))
        ){
            Text(
                text = "Tên Phim",
                color = Color.White
            )
        }
    }

}