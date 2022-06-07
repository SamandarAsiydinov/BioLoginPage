package com.example.loginpages.login

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.loginpages.R
import com.example.loginpages.ui.theme.Purple500

@Composable
fun BioMetricScreen(
    onClick: () -> Unit
) {
    val context = LocalContext.current
    val emailValue = remember { mutableStateOf("") }
    val passwordValue = remember { mutableStateOf("") }
    val isPasswordVisible = remember { mutableStateOf(false) }
    val checked = remember { mutableStateOf(false) }

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
                .background(Purple500),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Biometric FingerPrint",
                color = Color.White,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
        }
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.login_img),
                contentDescription = "Image",
                modifier = Modifier
                    .width(250.dp)
                    .height(250.dp),
                contentScale = ContentScale.Fit
            )
            Spacer(modifier = Modifier.padding(10.dp))

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 15.dp, end = 15.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "Sign In",
                    fontSize = 30.sp,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.padding(15.dp))

                OutlinedTextField(
                    value = emailValue.value,
                    onValueChange = { emailValue.value = it },
                    label = { Text(text = "Email Address") },
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Filled.Email,
                            contentDescription = "Email"
                        )
                    },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth(0.8f)
                )
                OutlinedTextField(
                    value = passwordValue.value,
                    onValueChange = { passwordValue.value = it },
                    trailingIcon = {
                        IconButton(onClick = {
                            isPasswordVisible.value = !isPasswordVisible.value
                        }
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_baseline_remove_red_eye_24),
                                contentDescription = "Password",
                                tint = if (isPasswordVisible.value) Purple500 else Color.Gray
                            )
                        }
                    },
                    label = { Text(text = "Password") },
                    placeholder = {
                        Icon(
                            imageVector = Icons.Filled.Lock,
                            contentDescription = "Password"
                        )
                    },
                    singleLine = true,
                    visualTransformation = if (isPasswordVisible.value)
                        VisualTransformation.None else PasswordVisualTransformation(),
                    modifier = Modifier.fillMaxWidth(0.8f)
                )
                Spacer(modifier = Modifier.padding(10.dp))

                Button(
                    onClick = {
                        if (emailValue.value.isEmpty()) {
                            context.toast("Please enter email address")
                        } else if (passwordValue.value.isEmpty()) {
                            context.toast("Please enter password")
                        } else {
                            context.toast("Logged Successfully")
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth(0.8f)
                        .height(50.dp)
                ) {
                    Text(text = "Sign In", fontSize = 20.sp)
                }
                Spacer(modifier = Modifier.padding(20.dp))

                Row(
                    horizontalArrangement = Arrangement.spacedBy(20.dp)
                ) {
                    Text(text = "Enable Biometric Prompt", fontSize = 20.sp)

                    Switch(
                        checked = checked.value,
                        onCheckedChange = {
                            checked.value = it
                            if (checked.value) {
                                onClick()
                            }
                        }
                    )
                }
                Spacer(modifier = Modifier.padding(10.dp))
            }
        }
    }
}

fun Context.toast(text: String) {
    Toast.makeText(this, text, Toast.LENGTH_SHORT).show()
}