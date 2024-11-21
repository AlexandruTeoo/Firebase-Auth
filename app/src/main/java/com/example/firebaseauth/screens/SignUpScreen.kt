package com.example.firebaseauth.screens

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.firebaseauth.viewmodel.AuthState
import com.example.firebaseauth.viewmodel.AuthViewModel

@Composable
fun SignUpScreen(navController: NavController, authViewModel: AuthViewModel){

    var name by remember {
        mutableStateOf("")
    }

    var email by remember {
        mutableStateOf("")
    }

    var password by remember {
        mutableStateOf("")
    }

    var password2 by remember {
        mutableStateOf("")
    }

    val authState = authViewModel.authState.observeAsState()
    val context = LocalContext.current

    LaunchedEffect(authState.value) {
        when(authState.value){
            is AuthState.isAuth -> navController.navigate("home_screen")
            is AuthState.Error -> Toast.makeText(context, (authState.value as AuthState.Error).message, Toast.LENGTH_SHORT).show()
            else -> Unit
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Sign Up Page",
            fontSize = 30.sp
        )
        Spacer(modifier = Modifier.height(16.dp))
        TextField(
            value = name,
            onValueChange = {name = it},
            label = { Text(text = "Name")}
        )
        Spacer(modifier = Modifier.height(4.dp))
        TextField(
            value = email,
            onValueChange = {email = it},
            label = { Text(text = "Email")}
        )
        Spacer(modifier = Modifier.height(4.dp))
        TextField(
            value = password,
            onValueChange = {password = it},
            label = { Text(text = "Password")}
        )
        Spacer(modifier = Modifier.height(4.dp))
        TextField(
            value = password2,
            onValueChange = {password2 = it},
            label = { Text(text = "Confirm Password")}
        )
        Spacer(modifier = Modifier.height(4.dp))
        Button(onClick = {
            authViewModel.signup(email, password)
        }) {
            Text(text = "Register")
        }
        TextButton(onClick = { navController.navigate("login_screen") }) {
            Text(text = "Do you have an account? Log In")
        }
    }
}