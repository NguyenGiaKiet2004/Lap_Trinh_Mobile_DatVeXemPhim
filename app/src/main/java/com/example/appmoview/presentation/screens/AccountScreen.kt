package com.example.appmoview.presentation.screens

import android.content.Context
import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.appmoview.R
import com.example.appmoview.utils.logout

@Composable
fun AccountScreen(navController: NavController) {
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
                        0 -> navController.navigate("home") {
                            popUpTo(0) { inclusive = true }
                        } // chuyển sang Home
                        1 -> navController.navigate("ticket") {
                            popUpTo(0) { inclusive = true }
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
            InformationPerson(
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



// bản load
/*@Composable
fun AccountScreen(navController: NavController) {

    val colorScheme = MaterialTheme.colorScheme
    val context = LocalContext.current

    val userViewModel: UserViewModel = viewModel()
    val userProfile by userViewModel.userProfile.observeAsState()
    val updateStatus by userViewModel.updateStatus.observeAsState()

    // State để quản lý chế độ chỉnh sửa
    var isEditing by remember { mutableStateOf(false) }

    // State để giữ giá trị của các TextField khi đang chỉnh sửa
    var fullName by remember { mutableStateOf("") }
    var phoneNumber by remember { mutableStateOf("") }
    var address by remember { mutableStateOf("") }

    // Lấy dữ liệu người dùng khi màn hình được bật lần đầu
    LaunchedEffect(Unit) {
        userViewModel.fetchUserProfile()
    }

    // Cập nhật state của TextField khi có dữ liệu mới từ ViewModel
    LaunchedEffect(userProfile) {
        userProfile?.let {
            fullName = it.fullName ?: ""
            phoneNumber = it.phoneNumber ?: ""
            address = it.address ?: ""
        }
    }

    // Hiển thị Toast thông báo kết quả cập nhật
    LaunchedEffect(updateStatus) {
        when (updateStatus) {
            true -> {
                Toast.makeText(context, "Cập nhật thành công!", Toast.LENGTH_SHORT).show()
                isEditing = false // Thoát chế độ chỉnh sửa sau khi lưu thành công
                userViewModel.onUpdateStatusShown() // Reset trạng thái để không hiển thị lại toast
            }

            false -> {
                Toast.makeText(context, "Cập nhật thất bại!", Toast.LENGTH_SHORT).show()
                userViewModel.onUpdateStatusShown()
            }

            null -> { *//* Do nothing *//*
            }
        }
    }

    if (userProfile == null) {
        // Hiển thị loading indicator khi chưa có dữ liệu
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator(color = Color.Yellow)
        }
    } else {
        Column(
            modifier = Modifier.fillMaxSize()
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
                    onClick = {navController.popBackStack()},
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

            InformationPerson(
                fullName = fullName,
                email = userProfile?.email ?: "N/A",
                phone = phoneNumber,
                address = address,
                isEditing = isEditing,
                onEdit = { isEditing = true },
                onCancel = {
                    isEditing = false
                    fullName = userProfile?.fullName ?: ""
                    phoneNumber = userProfile?.phoneNumber ?: ""
                    address = userProfile?.address ?: ""
                },
                onSave = {
                    userViewModel.updateUserProfile(fullName, phoneNumber, address)
                },
                onFullNameChange = { fullName = it },
                onPhoneChange = { phoneNumber = it },
                onAddressChange = { address = it }
            )
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
}*/



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
fun InformationPerson(
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

/*
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



}*/



