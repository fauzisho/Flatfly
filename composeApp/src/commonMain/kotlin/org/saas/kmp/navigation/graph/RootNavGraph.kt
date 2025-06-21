package org.saas.kmp.navigation.graph

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import org.saas.kmp.auth.AuthManager
import org.saas.kmp.navigation.Graph

@Composable
fun RootNavGraph(
    rootNavController: NavHostController,
    innerPadding: PaddingValues,
    onDialogVisibilityChange: (Boolean) -> Unit
) {
    val isLoggedIn = AuthManager.isLoggedIn
    
    NavHost(
        navController = rootNavController,
        startDestination = if (isLoggedIn) Graph.NAVIGATION_BAR_SCREEN_GRAPH else Graph.AUTH_GRAPH,
    ) {
        // Authentication Graph
        authNavGraph(
            rootNavController = rootNavController,
            onLoginSuccess = {
                rootNavController.navigate(Graph.NAVIGATION_BAR_SCREEN_GRAPH) {
                    // Clear back stack to prevent going back to login
                    popUpTo(Graph.AUTH_GRAPH) {
                        inclusive = true
                    }
                }
            }
        )
        
        // Main App Graph
        mainNavGraph(
            rootNavController = rootNavController, 
            innerPadding = innerPadding,
            onDialogVisibilityChange = onDialogVisibilityChange,
            onLogout = {
                rootNavController.navigate(Graph.AUTH_GRAPH) {
                    // Clear back stack to prevent going back to main app
                    popUpTo(Graph.NAVIGATION_BAR_SCREEN_GRAPH) {
                        inclusive = true
                    }
                }
            }
        )
    }
}