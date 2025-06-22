package com.example.appmoview.presentation.screens

import android.content.Context
import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.appmoview.utils.logout

@Composable
fun AccountScreen2(navController: NavController) {
    val context = LocalContext.current

    val sharedPref = context.getSharedPreferences("auth_prefs", Context.MODE_PRIVATE)
    val savedUsername = sharedPref.getString("username", "Người dùng")

    var fullName by remember { mutableStateOf(savedUsername ?: "Người dùng") }
    var email by remember { mutableStateOf("vana@example.com") }
    var phoneNumber by remember { mutableStateOf("0123456789") }
    var address by remember { mutableStateOf("403/52/98 Tân Chánh Hiệp, Quận 12") }

    var isEditing by remember { mutableStateOf(false) }
    var imageUri by remember { mutableStateOf<Uri?>(null) }

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        imageUri = uri
    }

    Scaffold(
        bottomBar = {
            BottomNavigationBar1(
                selectedIndex = 2, // Cá nhân được chọn
                onItemSelected = { index ->
                    when (index) {
                        0 -> navController.popBackStack()
                        1 -> navController.navigate("ticket") {
                            popUpTo("account") { inclusive = true }
                        }
                        2 -> {} // đang ở Cá nhân
                    }
                }
            )
        },
        containerColor = Color.Black
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .statusBarsPadding(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Thông tin tài khoản",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp),
                textAlign = TextAlign.Center
            )

            // Avatar
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

            Spacer(modifier = Modifier.height(20.dp))

            // Thông tin người dùng
            InformationPerson2(
                fullName = fullName,
                email = email,
                phone = phoneNumber,
                address = address,
                isEditing = isEditing,
                onEdit = { isEditing = true },
                onCancel = {
                    isEditing = false
                    fullName = savedUsername ?: "Người dùng"
                    phoneNumber = "0123456789"
                    address = "403/52/98 Tân Chánh Hiệp, Quận 12"
                },
                onSave = {
                    Toast.makeText(context, "Đã lưu thông tin mới", Toast.LENGTH_SHORT).show()
                    isEditing = false
                },
                onFullNameChange = { fullName = it },
                onPhoneChange = { phoneNumber = it },
                onAddressChange = { address = it }
            )

            Spacer(modifier = Modifier.height(30.dp))

            Button(
                onClick = {
                    // Ví dụ: clear SharedPreferences và chuyển về màn hình đăng nhập
                    logout(context)
                    navController.navigate("login") {
                        popUpTo(0) { inclusive = true }
                    }
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Red,
                    contentColor = Color.White
                ),
                shape = RoundedCornerShape(14.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 40.dp)
                    .height(60.dp)
            ) {
                Text("Đăng xuất", fontSize = 20.sp, fontWeight = FontWeight.Bold)
            }
        }
    }
}





@Composable
fun AvatarPicker2() {
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
fun InformationPerson2(
    fullName: String,
    email: String,
    phone: String,
    address: String,
    isEditing: Boolean,
    onEdit: () -> Unit,
    onCancel: () -> Unit,
    onSave: () -> Unit,
    onFullNameChange: (String) -> Unit,
    onPhoneChange: (String) -> Unit,
    onAddressChange: (String) -> Unit
) {
    Column(
        modifier = Modifier
            .padding(horizontal = 20.dp)
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.surface, RoundedCornerShape(24.dp))
            .padding(horizontal = 15.dp, vertical = 30.dp)
            .verticalScroll(rememberScrollState())
    ) {
        OutlinedTextField(
            value = fullName,
            onValueChange = onFullNameChange,
            label = { Text("Họ và tên") },
            enabled = isEditing,
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = email,
            onValueChange = {},
            label = { Text("Email") },
            enabled = false,
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = phone,
            onValueChange = onPhoneChange,
            label = { Text("Số điện thoại") },
            enabled = isEditing,
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = address,
            onValueChange = onAddressChange,
            label = { Text("Địa chỉ") },
            enabled = isEditing,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(20.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            if (isEditing) {
                Button(
                    onClick = onCancel,
                    colors = ButtonDefaults.buttonColors(containerColor = Color.Gray),
                    modifier = Modifier.weight(1f)
                ) {
                    Text("Hủy", fontWeight = FontWeight.Bold)
                }

                Spacer(modifier = Modifier.width(30.dp))

                Button(
                    onClick = onSave,
                    colors = ButtonDefaults.buttonColors(containerColor = Color.Yellow),
                    modifier = Modifier.weight(1f)
                ) {
                    Text("Lưu", color = Color.Black, fontWeight = FontWeight.Bold)
                }
            } else {
                Button(
                    onClick = onEdit,
                    colors = ButtonDefaults.buttonColors(containerColor = Color.Yellow),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Chỉnh sửa", color = Color.Black, fontWeight = FontWeight.Bold)
                }
            }
        }
    }
}

