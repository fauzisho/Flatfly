package org.saas.kmp.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Help
import androidx.compose.material.icons.automirrored.filled.Logout
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

// Data classes for account features
data class UserAccount(
    val name: String,
    val email: String,
    val avatar: String,
    val memberSince: String,
    val country: String,
    val countryFlag: String,
    val isVerified: Boolean = true,
    val isPremium: Boolean = false
)

data class MenuItem(
    val title: String,
    val subtitle: String? = null,
    val icon: ImageVector,
    val iconColor: Color = Color.Gray,
    val showArrow: Boolean = true,
    val showBadge: Boolean = false,
    val badgeText: String = "",
    val onClick: () -> Unit
)

@Composable
fun AccountScreen(
    rootNavController: NavController,
    paddingValues: PaddingValues
) {
    var showLogoutDialog by remember { mutableStateOf(false) }
    var showEditProfile by remember { mutableStateOf(false) }
    var notificationsEnabled by remember { mutableStateOf(true) }
    var darkModeEnabled by remember { mutableStateOf(false) }

    // Sample user data
    val user = remember {
        UserAccount(
            name = "John Doe",
            email = "john.doe@example.com",
            avatar = "👨‍💼",
            memberSince = "March 2024",
            country = "Germany",
            countryFlag = "🇩🇪",
            isVerified = true,
            isPremium = false
        )
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF8F9FA))
            .padding(paddingValues),
        verticalArrangement = Arrangement.spacedBy(0.dp)
    ) {
        // Profile Header
        item {
            ProfileHeader(
                user = user,
                onEditClick = { showEditProfile = true }
            )
        }

        // Premium Banner (if not premium)
        if (!user.isPremium) {
            item {
                PremiumBanner(
                    onUpgradeClick = { /* Handle premium upgrade */ }
                )
            }
        }

        // Account Section
        item {
            MenuSection(
                title = "Account",
                items = listOf(
                    MenuItem(
                        title = "Personal Information",
                        subtitle = "Update your details",
                        icon = Icons.Default.Person,
                        iconColor = Color(0xFF2196F3)
                    ) { showEditProfile = true },
                    MenuItem(
                        title = "Verification Status",
                        subtitle = if (user.isVerified) "Verified account" else "Verify your account",
                        icon = if (user.isVerified) Icons.Default.Verified else Icons.Default.Warning,
                        iconColor = if (user.isVerified) Color(0xFF4CAF50) else Color(0xFFFF9800),
                        showBadge = user.isVerified,
                        badgeText = "✓"
                    ) { /* Handle verification */ },
                    MenuItem(
                        title = "Security & Privacy",
                        subtitle = "Password, 2FA, privacy settings",
                        icon = Icons.Default.Security,
                        iconColor = Color(0xFF9C27B0)
                    ) { /* Handle security */ },
                    MenuItem(
                        title = "Preferences",
                        subtitle = "Language, region, currency",
                        icon = Icons.Default.Settings,
                        iconColor = Color(0xFF607D8B)
                    ) { /* Handle preferences */ }
                )
            )
        }

        // Notifications Section
        item {
            MenuSection(
                title = "Notifications",
                items = listOf(
                    MenuItem(
                        title = "Push Notifications",
                        subtitle = if (notificationsEnabled) "Enabled" else "Disabled",
                        icon = Icons.Default.Notifications,
                        iconColor = Color(0xFFFF5722),
                        showArrow = false
                    ) { notificationsEnabled = !notificationsEnabled },
                    MenuItem(
                        title = "Email Notifications",
                        subtitle = "Housing updates, messages",
                        icon = Icons.Default.Email,
                        iconColor = Color(0xFF03A9F4)
                    ) { /* Handle email notifications */ },
                    MenuItem(
                        title = "SMS Notifications",
                        subtitle = "Important alerts only",
                        icon = Icons.Default.Sms,
                        iconColor = Color(0xFF4CAF50)
                    ) { /* Handle SMS notifications */ }
                )
            )
        }

        // App Settings Section
        item {
            MenuSection(
                title = "App Settings",
                items = listOf(
                    MenuItem(
                        title = "Dark Mode",
                        subtitle = if (darkModeEnabled) "Dark theme enabled" else "Light theme active",
                        icon = if (darkModeEnabled) Icons.Default.DarkMode else Icons.Default.LightMode,
                        iconColor = if (darkModeEnabled) Color(0xFF9C27B0) else Color(0xFFFF9800),
                        showArrow = false
                    ) { darkModeEnabled = !darkModeEnabled },
                    MenuItem(
                        title = "Storage & Cache",
                        subtitle = "Manage app data",
                        icon = Icons.Default.Storage,
                        iconColor = Color(0xFF795548)
                    ) { /* Handle storage */ },
                    MenuItem(
                        title = "Auto-sync",
                        subtitle = "Sync data across devices",
                        icon = Icons.Default.Sync,
                        iconColor = Color(0xFF00BCD4)
                    ) { /* Handle sync */ }
                )
            )
        }

        // Support Section
        item {
            MenuSection(
                title = "Support & Feedback",
                items = listOf(
                    MenuItem(
                        title = "Help Center",
                        subtitle = "FAQs and tutorials",
                        icon = Icons.AutoMirrored.Filled.Help,
                        iconColor = Color(0xFF2196F3)
                    ) { /* Handle help */ },
                    MenuItem(
                        title = "Contact Support",
                        subtitle = "Get help from our team",
                        icon = Icons.Default.Support,
                        iconColor = Color(0xFF4CAF50)
                    ) { /* Handle contact support */ },
                    MenuItem(
                        title = "Rate the App",
                        subtitle = "Share your experience",
                        icon = Icons.Default.Star,
                        iconColor = Color(0xFFFF9800)
                    ) { /* Handle rating */ },
                    MenuItem(
                        title = "Send Feedback",
                        subtitle = "Help us improve",
                        icon = Icons.Default.Feedback,
                        iconColor = Color(0xFF9C27B0)
                    ) { /* Handle feedback */ }
                )
            )
        }

        // About Section
        item {
            MenuSection(
                title = "About",
                items = listOf(
                    MenuItem(
                        title = "Terms of Service",
                        subtitle = "App usage terms",
                        icon = Icons.Default.Description,
                        iconColor = Color(0xFF607D8B)
                    ) { /* Handle terms */ },
                    MenuItem(
                        title = "Privacy Policy",
                        subtitle = "How we protect your data",
                        icon = Icons.Default.PrivacyTip,
                        iconColor = Color(0xFF9E9E9E)
                    ) { /* Handle privacy */ },
                    MenuItem(
                        title = "App Version",
                        subtitle = "v1.2.3 (Build 456)",
                        icon = Icons.Default.Info,
                        iconColor = Color(0xFF795548),
                        showArrow = false
                    ) { /* Handle version info */ }
                )
            )
        }

        // Logout Section
        item {
            MenuSection(
                title = "",
                items = listOf(
                    MenuItem(
                        title = "Sign Out",
                        subtitle = "Log out of your account",
                        icon = Icons.AutoMirrored.Filled.Logout,
                        iconColor = Color(0xFFE91E63)
                    ) { showLogoutDialog = true }
                )
            )
        }

        // Bottom spacing
        item {
            Spacer(modifier = Modifier.height(100.dp))
        }
    }

    // Logout Confirmation Dialog
    if (showLogoutDialog) {
        AlertDialog(
            onDismissRequest = { showLogoutDialog = false },
            title = { Text("Sign Out") },
            text = { Text("Are you sure you want to sign out of your account?") },
            confirmButton = {
                TextButton(
                    onClick = {
                        showLogoutDialog = false
                        // Handle logout
                    }
                ) {
                    Text("Sign Out", color = Color(0xFFE91E63))
                }
            },
            dismissButton = {
                TextButton(onClick = { showLogoutDialog = false }) {
                    Text("Cancel")
                }
            }
        )
    }

    // Edit Profile Dialog (simplified)
    if (showEditProfile) {
        AlertDialog(
            onDismissRequest = { showEditProfile = false },
            title = { Text("Edit Profile") },
            text = { 
                Text("Profile editing feature coming soon!\n\nYou'll be able to update:\n• Profile picture\n• Personal information\n• Contact details\n• Preferences") 
            },
            confirmButton = {
                TextButton(onClick = { showEditProfile = false }) {
                    Text("OK")
                }
            }
        )
    }
}

@Composable
fun ProfileHeader(
    user: UserAccount,
    onEditClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        shape = RoundedCornerShape(16.dp)
    ) {
        Column(
            modifier = Modifier.padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Avatar
            Box(
                modifier = Modifier.size(80.dp),
                contentAlignment = Alignment.Center
            ) {
                Box(
                    modifier = Modifier
                        .size(80.dp)
                        .clip(CircleShape)
                        .background(Color(0xFF2196F3).copy(alpha = 0.1f))
                        .border(3.dp, Color(0xFF2196F3).copy(alpha = 0.3f), CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = user.avatar,
                        fontSize = 32.sp
                    )
                }
                
                // Verification badge
                if (user.isVerified) {
                    Box(
                        modifier = Modifier
                            .size(24.dp)
                            .clip(CircleShape)
                            .background(Color(0xFF4CAF50))
                            .align(Alignment.BottomEnd),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            Icons.Default.Check,
                            contentDescription = "Verified",
                            tint = Color.White,
                            modifier = Modifier.size(16.dp)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Name and country
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    text = user.name,
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )
                Text(
                    text = user.countryFlag,
                    fontSize = 18.sp
                )
                if (user.isPremium) {
                    Icon(
                        Icons.Default.Star,
                        contentDescription = "Premium",
                        tint = Color(0xFFFF9800),
                        modifier = Modifier.size(20.dp)
                    )
                }
            }

            // Email
            Text(
                text = user.email,
                fontSize = 14.sp,
                color = Color.Gray,
                modifier = Modifier.padding(top = 4.dp)
            )

            // Member since
            Text(
                text = "Member since ${user.memberSince}",
                fontSize = 12.sp,
                color = Color.Gray,
                modifier = Modifier.padding(top = 2.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Edit Profile Button
            OutlinedButton(
                onClick = onEditClick,
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.outlinedButtonColors(),
                border = BorderStroke(1.dp, Color(0xFF2196F3)),
                shape = RoundedCornerShape(8.dp)
            ) {
                Icon(
                    Icons.Default.Edit,
                    contentDescription = null,
                    tint = Color(0xFF2196F3),
                    modifier = Modifier.size(16.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "Edit Profile",
                    color = Color(0xFF2196F3),
                    fontWeight = FontWeight.Medium
                )
            }
        }
    }
}

@Composable
fun PremiumBanner(
    onUpgradeClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .clickable { onUpgradeClick() },
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFFFF9800).copy(alpha = 0.1f)
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        shape = RoundedCornerShape(12.dp)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)
                    .background(Color(0xFFFF9800).copy(alpha = 0.2f)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    Icons.Default.Star,
                    contentDescription = null,
                    tint = Color(0xFFFF9800),
                    modifier = Modifier.size(24.dp)
                )
            }
            
            Spacer(modifier = Modifier.width(12.dp))
            
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = "Upgrade to Premium",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )
                Text(
                    text = "Get priority support, advanced filters & more",
                    fontSize = 12.sp,
                    color = Color.Gray
                )
            }
            
            Icon(
                Icons.Default.ChevronRight,
                contentDescription = null,
                tint = Color(0xFFFF9800)
            )
        }
    }
}

@Composable
fun MenuSection(
    title: String,
    items: List<MenuItem>
) {
    Column(
        modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
    ) {
        if (title.isNotEmpty()) {
            Text(
                text = title,
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium,
                color = Color.Gray,
                modifier = Modifier.padding(bottom = 8.dp, start = 4.dp)
            )
        }

        Card(
            colors = CardDefaults.cardColors(containerColor = Color.White),
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
            shape = RoundedCornerShape(12.dp)
        ) {
            Column {
                items.forEachIndexed { index, item ->
                    MenuItemRow(item = item)
                    
                    if (index < items.size - 1) {
                        Divider(
                            modifier = Modifier.padding(start = 52.dp),
                            color = Color.Gray.copy(alpha = 0.1f),
                            thickness = 0.5.dp
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun MenuItemRow(
    item: MenuItem
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { item.onClick() }
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Icon
        Box(
            modifier = Modifier
                .size(36.dp)
                .clip(CircleShape)
                .background(item.iconColor.copy(alpha = 0.1f)),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                item.icon,
                contentDescription = null,
                tint = item.iconColor,
                modifier = Modifier.size(20.dp)
            )
        }

        Spacer(modifier = Modifier.width(12.dp))

        // Content
        Column(modifier = Modifier.weight(1f)) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = item.title,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color.Black,
                    modifier = Modifier.weight(1f)
                )
                
                if (item.showBadge && item.badgeText.isNotEmpty()) {
                    Text(
                        text = item.badgeText,
                        fontSize = 12.sp,
                        color = Color(0xFF4CAF50),
                        fontWeight = FontWeight.Bold
                    )
                }
            }
            
            item.subtitle?.let { subtitle ->
                Text(
                    text = subtitle,
                    fontSize = 13.sp,
                    color = Color.Gray,
                    modifier = Modifier.padding(top = 2.dp)
                )
            }
        }

        // Arrow or toggle
        if (item.showArrow) {
            Icon(
                Icons.Default.ChevronRight,
                contentDescription = null,
                tint = Color.Gray,
                modifier = Modifier.size(20.dp)
            )
        }
    }
}
