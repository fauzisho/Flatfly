package org.saas.kmp.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Description
import androidx.compose.material.icons.filled.Hotel
import androidx.compose.material.icons.filled.People
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material.icons.outlined.Description
import androidx.compose.material.icons.outlined.Hotel
import androidx.compose.material.icons.outlined.People
import androidx.compose.material.icons.outlined.AccountCircle

object Graph {
    const val NAVIGATION_BAR_SCREEN_GRAPH = "navigationBarScreenGraph"
}

sealed class Routes(var route: String) {
    data object Home : Routes("home")
    data object Document : Routes("document")
    data object Residence : Routes("residence")
    data object BuddyHelp : Routes("buddyHelp")
    data object Account : Routes("account")
    data object HomeDetail : Routes("homeDetail")
    data object SettingDetail : Routes("settingDetail")
}

val navigationItemsLists = listOf(
    NavigationItem(
        unSelectedIcon = Icons.Outlined.Home,
        selectedIcon = Icons.Filled.Home,
        title = "Home",
        route = Routes.Home.route,
    ),
    NavigationItem(
        unSelectedIcon = Icons.Outlined.Description,
        selectedIcon = Icons.Filled.Description,
        title = "Document",
        route = Routes.Document.route,
    ),
    NavigationItem(
        unSelectedIcon = Icons.Outlined.Hotel,
        selectedIcon = Icons.Filled.Hotel,
        title = "Housing",
        route = Routes.Residence.route,
    ),
    NavigationItem(
        unSelectedIcon = Icons.Outlined.People,
        selectedIcon = Icons.Filled.People,
        title = "Buddy Help",
        route = Routes.BuddyHelp.route,
    ),
    NavigationItem(
        unSelectedIcon = Icons.Outlined.AccountCircle,
        selectedIcon = Icons.Filled.AccountCircle,
        title = "Account",
        route = Routes.Account.route,
    ),
)
