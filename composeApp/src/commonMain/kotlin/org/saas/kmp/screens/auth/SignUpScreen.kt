package org.saas.kmp.screens.auth

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import kotlinx.coroutines.launch
import org.saas.kmp.auth.AuthManager
import androidx.compose.foundation.Image
import androidx.compose.ui.layout.ContentScale
import saaskmp.composeapp.generated.resources.Res
import saaskmp.composeapp.generated.resources.flatfly
import org.jetbrains.compose.resources.painterResource

@Composable
fun SignUpScreen(
    navController: NavController,
    onSignUpSuccess: () -> Unit
) {
    var fullName by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }
    var confirmPasswordVisible by remember { mutableStateOf(false) }
    var isLoading by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf("") }
    var agreeToTerms by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        Color(0xFF1976D2),
                        Color(0xFF42A5F5)
                    )
                )
            )
            .padding(24.dp)
            .verticalScroll(rememberScrollState())
    ) {
        // Back Button
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(
                onClick = { navController.popBackStack() },
                modifier = Modifier.background(
                    Color.White.copy(alpha = 0.2f),
                    RoundedCornerShape(12.dp)
                )
            ) {
                Icon(
                    Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Back",
                    tint = Color.White
                )
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // App Logo
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            Card(
                modifier = Modifier.size(80.dp),
                shape = RoundedCornerShape(40.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White.copy(alpha = 0.95f)),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
            ) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Image(
                        painter = painterResource(Res.drawable.flatfly),
                        contentDescription = "FlatFly Logo",
                        modifier = Modifier
                            .fillMaxSize()
                            .clip(CircleShape),
                        contentScale = ContentScale.Crop
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Header
        Text(
            text = "Create Account",
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )

        Text(
            text = "Join FlatFly and find your perfect home",
            fontSize = 16.sp,
            color = Color.White.copy(alpha = 0.8f),
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 32.dp)
        )

        Spacer(modifier = Modifier.height(48.dp))

        // Sign Up Form
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White)
        ) {
            Column(
                modifier = Modifier.padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Sign Up",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF1976D2)
                )

                Spacer(modifier = Modifier.height(24.dp))

                // Full Name Field
                OutlinedTextField(
                    value = fullName,
                    onValueChange = { 
                        fullName = it
                        errorMessage = ""
                    },
                    label = { Text("Full Name") },
                    leadingIcon = {
                        Icon(Icons.Default.Person, contentDescription = null)
                    },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    singleLine = true
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Email Field
                OutlinedTextField(
                    value = email,
                    onValueChange = { 
                        email = it
                        errorMessage = ""
                    },
                    label = { Text("Email") },
                    leadingIcon = {
                        Icon(Icons.Default.Email, contentDescription = null)
                    },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    singleLine = true
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Password Field
                OutlinedTextField(
                    value = password,
                    onValueChange = { 
                        password = it
                        errorMessage = ""
                    },
                    label = { Text("Password") },
                    leadingIcon = {
                        Icon(Icons.Default.Lock, contentDescription = null)
                    },
                    trailingIcon = {
                        IconButton(onClick = { passwordVisible = !passwordVisible }) {
                            Icon(
                                if (passwordVisible) Icons.Default.Visibility else Icons.Default.VisibilityOff,
                                contentDescription = if (passwordVisible) "Hide password" else "Show password"
                            )
                        }
                    },
                    visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    singleLine = true
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Confirm Password Field
                OutlinedTextField(
                    value = confirmPassword,
                    onValueChange = { 
                        confirmPassword = it
                        errorMessage = ""
                    },
                    label = { Text("Confirm Password") },
                    leadingIcon = {
                        Icon(Icons.Default.Lock, contentDescription = null)
                    },
                    trailingIcon = {
                        IconButton(onClick = { confirmPasswordVisible = !confirmPasswordVisible }) {
                            Icon(
                                if (confirmPasswordVisible) Icons.Default.Visibility else Icons.Default.VisibilityOff,
                                contentDescription = if (confirmPasswordVisible) "Hide password" else "Show password"
                            )
                        }
                    },
                    visualTransformation = if (confirmPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    singleLine = true
                )

                if (errorMessage.isNotEmpty()) {
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = errorMessage,
                        color = MaterialTheme.colorScheme.error,
                        fontSize = 12.sp
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Terms and Conditions
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Checkbox(
                        checked = agreeToTerms,
                        onCheckedChange = { agreeToTerms = it }
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Column {
                        Row {
                            Text(
                                text = "I agree to the ",
                                fontSize = 12.sp,
                                color = Color.Gray
                            )
                            Text(
                                text = "Terms of Service",
                                fontSize = 12.sp,
                                color = Color(0xFF1976D2),
                                modifier = Modifier.clickable { /* Handle terms click */ }
                            )
                        }
                        Row {
                            Text(
                                text = "and ",
                                fontSize = 12.sp,
                                color = Color.Gray
                            )
                            Text(
                                text = "Privacy Policy",
                                fontSize = 12.sp,
                                color = Color(0xFF1976D2),
                                modifier = Modifier.clickable { /* Handle privacy click */ }
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))

                // Sign Up Button
                Button(
                    onClick = {
                        when {
                            fullName.isEmpty() -> errorMessage = "Please enter your full name"
                            email.isEmpty() -> errorMessage = "Please enter your email"
                            password.isEmpty() -> errorMessage = "Please enter your password"
                            confirmPassword.isEmpty() -> errorMessage = "Please confirm your password"
                            !email.contains("@") -> errorMessage = "Please enter a valid email"
                            password.length < 6 -> errorMessage = "Password must be at least 6 characters"
                            password != confirmPassword -> errorMessage = "Passwords do not match"
                            !agreeToTerms -> errorMessage = "Please agree to the Terms of Service and Privacy Policy"
                            else -> {
                                isLoading = true
                                // Simulate sign up API call
                                kotlinx.coroutines.MainScope().launch {
                                    kotlinx.coroutines.delay(2000)
                                    isLoading = false
                                    // Use AuthManager to handle signup
                                    if (AuthManager.signUp(fullName, email, password)) {
                                        onSignUpSuccess()
                                    } else {
                                        errorMessage = "Failed to create account"
                                    }
                                }
                            }
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF1976D2)
                    ),
                    enabled = !isLoading
                ) {
                    if (isLoading) {
                        CircularProgressIndicator(
                            modifier = Modifier.size(24.dp),
                            color = Color.White
                        )
                    } else {
                        Text(
                            text = "Create Account",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Medium
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Sign In Link
                Row {
                    Text(
                        text = "Already have an account? ",
                        color = Color.Gray,
                        fontSize = 14.sp
                    )
                    Text(
                        text = "Sign In",
                        color = Color(0xFF1976D2),
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Medium,
                        modifier = Modifier.clickable {
                            navController.popBackStack()
                        }
                    )
                }
            }
        }
    }
}
