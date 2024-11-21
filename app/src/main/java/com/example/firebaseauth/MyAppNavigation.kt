package com.example.firebaseauth

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.firebaseauth.screens.HomeScreen
import com.example.firebaseauth.screens.LogInScreen
import com.example.firebaseauth.screens.SignUpScreen
import com.example.firebaseauth.viewmodel.AuthViewModel

@Composable
fun MyAppNavigation(authViewModel: AuthViewModel) {
    val navController = rememberNavController()
    
    NavHost(navController = navController, startDestination = "login_screen"){
        composable("home_screen") {
            HomeScreen(navController, authViewModel)
        }
        composable("signup_screen") {
            SignUpScreen(navController, authViewModel)
        }
        composable("login_screen") {
            LogInScreen(navController, authViewModel)
        }
    }
}