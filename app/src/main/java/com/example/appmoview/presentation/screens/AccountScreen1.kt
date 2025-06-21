package com.example.appmoview.presentation.screens

import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CameraAlt
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
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.appmoview.R

// Dữ liệu mẫu cho tài khoản người dùng
data class UserProfile(
    val fullName: String,
    val email: String,
    val phoneNumber: String,
    val address: String
)

@Composable
fun AccountScreen1(navController: NavController, onLogout: () -> Unit) {
    val colorScheme = MaterialTheme.colorScheme
    val context = LocalContext.current

    // Định nghĩa dữ liệu mẫu
    val mockUserProfile = remember {
        UserProfile(
            fullName = "Nguyễn Văn A",
            email = "nguyenvana@gmail.com",
            phoneNumber = "0923189987",
            address = "403/52/98 Tân Chánh Hiệp"
        )
    }

    // State để quản lý chế độ chỉnh sửa
    var isEditing by remember { mutableStateOf(false) }

    // State để giữ giá trị của các TextField khi đang chỉnh sửa
    var fullName by remember { mutableStateOf(mockUserProfile.fullName) }
    var phoneNumber by remember { mutableStateOf(mockUserProfile.phoneNumber) }
    var address by remember { mutableStateOf(mockUserProfile.address) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
            .padding(top = 40.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Header
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

            Text(
                text = "Thông tin tài khoản",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = colorScheme.onSurface,
                modifier = Modifier.align(Alignment.Center),
                textAlign = TextAlign.Center
            )
        }

        AvatarPicker1()
        Spacer(modifier = Modifier.height(16.dp))

        InformationPerson1(
            fullName = fullName,
            email = mockUserProfile.email,
            phone = phoneNumber,
            address = address,
            isEditing = isEditing,
            onEdit = { isEditing = true },
            onCancel = {
                isEditing = false
                fullName = mockUserProfile.fullName
                phoneNumber = mockUserProfile.phoneNumber
                address = mockUserProfile.address
            },
            onSave = {
                Toast.makeText(context, "Cập nhật thành công (Mock)!", Toast.LENGTH_SHORT).show()
                isEditing = false
            },
            onFullNameChange = { fullName = it },
            onPhoneChange = { phoneNumber = it },
            onAddressChange = { address = it }
        )
        Spacer(modifier = Modifier.weight(1f))
        Button(
            onClick = onLogout,
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
fun AvatarPicker1() {
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
            .background(Color.Gray),
        contentAlignment = Alignment.Center
    ) {
        // Hiển thị avatar
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

        // Thêm icon camera ở góc dưới bên phải
        Icon(
            imageVector = Icons.Default.CameraAlt,
            contentDescription = "Change Avatar",
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(8.dp)
                .size(40.dp)
                .clip(CircleShape)
                .clickable { launcher.launch("image/*") }
                .background(Color.Black.copy(alpha = 0.5f), CircleShape),
            tint = Color.White
        )
    }
}

@Composable
fun InformationPerson1(
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