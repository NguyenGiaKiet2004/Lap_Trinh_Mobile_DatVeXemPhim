package com.example.appmoview.presentation.screens

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.hoverable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme.colors
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.appmoview.R
import java.nio.file.WatchEvent

@Composable
fun AccountScreen() {
    val colorScheme = MaterialTheme.colorScheme
    Column(
        modifier = Modifier.fillMaxSize()
            .background(Color.Black)
            .padding(top = 40.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
        ) {
            IconButton(
                onClick = {},
                modifier = Modifier.align(Alignment.CenterStart)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.baseline_arrow_back_ios_24),
                    contentDescription = "Back",
                    modifier = Modifier.size(24.dp),
                    colorFilter = ColorFilter.tint(colorScheme.onSurface)
                )
            }

            Text(
                text = "Thông tin tài khoảng",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = colorScheme.onSurface,
                modifier = Modifier.align(Alignment.Center),
                textAlign = TextAlign.Center
            )
        }

        AvatarPicker()
        Spacer(modifier = Modifier.height(16.dp))
        InformationPerson()
        Spacer(modifier = Modifier.weight(1f))
        Button(
            onClick = {},
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.background
            ),
            shape = RoundedCornerShape(14.dp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 40.dp, vertical = 40.dp)
                .height(60.dp)
        ) {
            Text(text = "Đăng xuất", fontSize = 24.sp, fontWeight = FontWeight.Bold)
        }
    }
}
@Composable
fun AvatarPicker() {
    val context = LocalContext.current
    var imageUri by remember { mutableStateOf<Uri?>(null) }

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        imageUri = uri
    }

    Box(
        modifier = Modifier
            .size(200.dp)
            .clip(CircleShape)
            .clickable { launcher.launch("image/*") }
            .background(Color.Gray),
        contentAlignment = Alignment.Center,
    ) {
        if (imageUri != null) {
            AsyncImage(
                model = imageUri,
                contentDescription = "Avatar",
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )
        } else {
            Icon(
                imageVector = Icons.Default.Person,
                contentDescription = "Default Avatar",
                modifier = Modifier.size(150.dp),
                tint = Color.White
            )
        }
    }
}

@Composable
fun InformationPerson(){
    // Nội dung bên dưới

    Column(
        modifier = Modifier
            .padding(horizontal = 20.dp)
            .fillMaxWidth()
            .height(230.dp)
            .background(
                color = colorScheme.surface,
                shape = RoundedCornerShape(24.dp)
            )
            .padding(horizontal = 15.dp, vertical = 30.dp)
            .verticalScroll(rememberScrollState()),
    ) {
        Text(
            text = "NGUYỄN VĂN A",
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold,
            color = colorScheme.onSurface,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center
        )

        Text(
            text = "NAM",
            fontSize = 14.sp,
            color = colorScheme.onSurface,
            modifier = Modifier.padding(top = 6.dp).fillMaxWidth(),
            textAlign = TextAlign.Center
        )
        Divider(
            color = Color.White,
            thickness = 1.dp,
            modifier = Modifier.padding(vertical = 10.dp),
        )

        Column(
            modifier = Modifier
                .height(100.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ){
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
            ) {
                Text(stringResource(id = R.string.Email), fontWeight = FontWeight.SemiBold, color = colorScheme.onSurface,modifier = Modifier.weight(1f))
                Text("thank@gmail.com", color = colorScheme.onSurface,modifier = Modifier.weight(2f))
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth(),
            ){
                Text(stringResource(id = R.string.sdt), fontWeight = FontWeight.SemiBold, color = colorScheme.onSurface,modifier = Modifier.weight(1f))
                Text("0923189987", color = colorScheme.onSurface,modifier = Modifier.weight(2f))
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
            ){
                Text(stringResource(id = R.string.dia_chi), fontWeight = FontWeight.SemiBold, color = colorScheme.onSurface,modifier = Modifier.weight(1f))
                Text("403/52/98 Tân Chánh Hiệp", color = colorScheme.onSurface,modifier = Modifier.weight(2f))
            }
        }




    }

    Spacer(modifier = Modifier.height(20.dp))

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 50.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ){
        Button(
            onClick = {},
            colors = ButtonDefaults.buttonColors(
                containerColor = colorScheme.primary,
                contentColor = colorScheme.background
            ),
            modifier = Modifier.weight(1f)
            ) {
            Text(
                "Chỉnh sửa",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center
            )
        }

        Spacer(modifier = Modifier.width(30.dp))

        Button(
            onClick = {},
            colors = ButtonDefaults.buttonColors(
                containerColor = colorScheme.primary,
                contentColor = colorScheme.background

            ),
            modifier = Modifier.weight(1f)
        ) {
            Text(
                "Lưu",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center
            )
        }
    }



}



