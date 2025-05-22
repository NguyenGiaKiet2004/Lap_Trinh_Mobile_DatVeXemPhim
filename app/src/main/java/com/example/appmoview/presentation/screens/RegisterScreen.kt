package com.example.appmoview.presentation.screens

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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.appmoview.R

@Composable
fun RegisterScreen() {
    val colorScheme = MaterialTheme.colorScheme

    var username by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }

    var passwordVisible by remember { mutableStateOf(false) }
    var confirmPasswordVisible by remember { mutableStateOf(false) }

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
            text = stringResource(id = R.string.register),
            fontSize = 30.sp,
            color = colorScheme.onBackground,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(20.dp),
            textAlign = TextAlign.Center
        )

        // Username
        OutlinedTextField(
            value = username,
            onValueChange = { username = it },
            label = { Text(stringResource(id = R.string.username), color = colorScheme.onBackground) },
            singleLine = true,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 12.dp),
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

        // Email
        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text(stringResource(id = R.string.Email), color = colorScheme.onBackground) },
            singleLine = true,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 12.dp),
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

        // Password
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
                .padding(top = 12.dp),
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

        // Confirm Password
        OutlinedTextField(
            value = confirmPassword,
            onValueChange = { confirmPassword = it },
            label = { Text("Confirm Password", color = colorScheme.onBackground) },
            singleLine = true,
            visualTransformation = if (confirmPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
            trailingIcon = {
                IconButton(onClick = { confirmPasswordVisible = !confirmPasswordVisible }) {
                    Icon(
                        imageVector = if (confirmPasswordVisible) Icons.Default.Visibility else Icons.Default.VisibilityOff,
                        contentDescription = null,
                        tint = colorScheme.onBackground
                    )
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 12.dp),
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

        // Register Button
        Button(
            onClick = {  },
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
                text = stringResource(id = R.string.register),
                fontSize = 17.sp,
                fontWeight = FontWeight.Bold
            )
        }

        Spacer(modifier = Modifier.height(25.dp))

        // Already have account? Login
        Row(
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                text = stringResource(id = R.string.dont_have_account),
                fontSize = 16.sp,
                color = colorScheme.onBackground
            )
            Spacer(modifier = Modifier.width(4.dp))
            Text(
                text = stringResource(id = R.string.login),
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = colorScheme.onBackground,
                modifier = Modifier.clickable { }
            )
        }
    }
}
