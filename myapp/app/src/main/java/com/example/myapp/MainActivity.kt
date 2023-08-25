package com.example.wastewarrior

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import com.example.wastewarrior.ui.theme.WASTEWARRIORTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            WASTEWARRIORTheme {
                LoginScreen()
            }
        }
    }
}

@Composable
fun LoginScreen() {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxSize().padding(16.dp)
        ) {
            var email by remember { mutableStateOf("") }
            var password by remember { mutableStateOf("") }

            BasicTextField(
                value = email,
                onValueChange = { email = it },
                placeholder = { Text(text = "Email") },
                modifier = Modifier.fillMaxWidth().border(1.dp, Color.Gray).padding(8.dp)
            )
            Spacer(modifier = Modifier.height(10.dp))
            BasicTextField(
                value = password,
                onValueChange = { password = it },
                visualTransformation = PasswordVisualTransformation(),
                placeholder = { Text(text = "Password") },
                modifier = Modifier.fillMaxWidth().border(1.dp, Color.Gray).padding(8.dp)
            )
            Spacer(modifier = Modifier.height(20.dp))
            Button(onClick = {
                // Handle customer login
            }) {
                Text("Customer Login")
            }
            Spacer(modifier = Modifier.height(10.dp))
            Button(onClick = {
                // Handle admin login
            }) {
                Text("Admin Login")
            }
        }
    }
}
