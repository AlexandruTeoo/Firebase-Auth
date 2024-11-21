package com.example.firebaseauth.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth

class AuthViewModel: ViewModel() {

    private val auth: FirebaseAuth = FirebaseAuth.getInstance()
    private val _authState = MutableLiveData<AuthState>()
    val authState: LiveData<AuthState> = _authState

    init {
        checkAuthStatus()
    }

    fun checkAuthStatus(){
        if (auth.currentUser == null){
            _authState.value = AuthState.isNotAuth
        }
        else{
            _authState.value = AuthState.isAuth
        }
    }

    fun login (email: String, password: String){
        if (email.isEmpty() || password.isEmpty()){
            _authState.value = AuthState.Error("Email and Password can't be empty!")
            return
        }

        _authState.value = AuthState.Loading
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener{ task ->
                if(task.isSuccessful){
                    _authState.value = AuthState.isAuth
                }
                else{
                    _authState.value = AuthState.Error(task.exception?.message ?: "Something went wrong! Please try again!")
                }
            }
    }

    fun signup (email: String, password: String){
        if (email.isEmpty() || password.isEmpty()){
            _authState.value = AuthState.Error ("Email and Password can't be empty!")
            return
        }
        _authState.value = AuthState.Loading
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful){
                    _authState.value = AuthState.isAuth
                }
                else{
                    _authState.value = AuthState.Error(task.exception?.message ?: "Something went wrong! Please try again!")
                }
            }
    }

    fun signout(){
        auth.signOut()
        _authState.value = AuthState.isNotAuth
    }
}

sealed class AuthState{
    object isAuth: AuthState()
    object isNotAuth: AuthState()
    object Loading: AuthState()
    data class Error(val message: String): AuthState()
}