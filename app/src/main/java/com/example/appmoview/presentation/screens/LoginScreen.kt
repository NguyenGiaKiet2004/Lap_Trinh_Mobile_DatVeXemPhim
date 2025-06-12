package com.example.appmoview.presentation.screens

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.appmoview.R
import com.example.appmoview.domain.model.LoginRequest
import com.example.appmoview.presentation.viewmodels.AuthViewModel

@Composable
fun LoginScreen(navController: NavController) {
    val context = LocalContext.current
    val authViewModel: AuthViewModel = viewModel()
    val loginStatus by authViewModel.loginStatus.observeAsState()
    val colorScheme = MaterialTheme.colorScheme

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }
    var isLoading by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(colorScheme.background)
            .padding(20.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = stringResource(id = R.string.app_name),
            fontSize = 35.sp,
            color = colorScheme.onBackground,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(20.dp),
            textAlign = TextAlign.Center
        )

        // Email Input
        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text(stringResource(id = R.string.Email), color = colorScheme.onBackground) },
            singleLine = true,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 22.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = colorScheme.primary,
                unfocusedBorderColor = colorScheme.primary,
                focusedLabelColor = colorScheme.onBackground,
                unfocusedLabelColor = colorScheme.onBackground,
                cursorColor = colorScheme.onBackground,
                focusedTextColor = colorScheme.onBackground,
                unfocusedTextColor = colorScheme.onBackground
            )
        )

        // Password Input
        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text(stringResource(id = R.string.passowrd), color = colorScheme.onBackground) },
            singleLine = true,
            visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
            trailingIcon = {
                IconButton(onClick = { passwordVisible = !passwordVisible }) {
                    Icon(
                        imageVector = if (passwordVisible) Icons.Default.Visibility else Icons.Default.VisibilityOff,
                        contentDescription = null,
                        tint = colorScheme.onBackground
                    )
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 22.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = colorScheme.primary,
                unfocusedBorderColor = colorScheme.primary,
                focusedLabelColor = colorScheme.onBackground,
                unfocusedLabelColor = colorScheme.onBackground,
                cursorColor = colorScheme.onBackground,
                focusedTextColor = colorScheme.onBackground,
                unfocusedTextColor = colorScheme.onBackground
            )
        )

        // Login Button or Loading Spinner
        if (isLoading) {
            androidx.compose.material3.CircularProgressIndicator(
                color = colorScheme.primary,
                modifier = Modifier
                    .padding(top = 30.dp)
                    .height(50.dp)
            )
        } else {
            Button(
                onClick = {
                    val user = LoginRequest(email, password)
                    isLoading = true
                    authViewModel.loginUser(user)
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = colorScheme.primary,
                    contentColor = colorScheme.onPrimary
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 30.dp)
                    .height(50.dp),
                shape = RoundedCornerShape(16.dp)
            ) {
                Text(
                    text = stringResource(id = R.string.login),
                    fontSize = 17.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }

        Spacer(modifier = Modifier.height(35.dp))

        LaunchedEffect(loginStatus) {
            loginStatus?.let {
                isLoading = false // Tắt loading khi có kết quả

                if (it) {
                    Toast.makeText(context, "Đăng nhập thành công", Toast.LENGTH_SHORT).show()
                    navController.navigate("home") {
                        popUpTo("login") { inclusive = true }
                    }
                } else {
                    Toast.makeText(context, "Đăng nhập thất bại", Toast.LENGTH_SHORT).show()
                }
            }
        }

        // Register row
        Row(
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                text = stringResource(id = R.string.have_account),
                fontSize = 16.sp,
                color = colorScheme.onBackground
            )
            Spacer(modifier = Modifier.width(4.dp))
            Text(
                text = stringResource(id = R.string.register),
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = colorScheme.onBackground,
                modifier = Modifier.clickable {
                    navController.navigate("register") {
                        popUpTo("login") { inclusive = true }
                    }
                }
            )
        }
    }
}


