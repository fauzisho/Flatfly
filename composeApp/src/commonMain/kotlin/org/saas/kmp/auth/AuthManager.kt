package org.saas.kmp.auth

import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue

object AuthManager {
    private var _isLoggedIn by mutableStateOf(false)
    private var _currentUser by mutableStateOf<User?>(null)
    
    val isLoggedIn: Boolean get() = _isLoggedIn
    val currentUser: User? get() = _currentUser
    
    fun login(email: String, password: String): Boolean {
        // Simulate authentication logic
        return if (email.isNotEmpty() && password.isNotEmpty()) {
            _currentUser = User(
                id = "1",
                name = "John Doe",
                email = email,
                avatar = "👨‍💼"
            )
            _isLoggedIn = true
            true
        } else {
            false
        }
    }
    
    fun signUp(name: String, email: String, password: String): Boolean {
        // Simulate sign up logic
        return if (name.isNotEmpty() && email.isNotEmpty() && password.isNotEmpty()) {
            _currentUser = User(
                id = "1",
                name = name,
                email = email,
                avatar = "👨‍💼"
            )
            _isLoggedIn = true
            true
        } else {
            false
        }
    }
    
    fun logout() {
        _isLoggedIn = false
        _currentUser = null
    }
    
    fun updateUserProfile(user: User) {
        _currentUser = user
    }
}

data class User(
    val id: String,
    val name: String,
    val email: String,
    val avatar: String,
    val memberSince: String = "March 2024"
)
