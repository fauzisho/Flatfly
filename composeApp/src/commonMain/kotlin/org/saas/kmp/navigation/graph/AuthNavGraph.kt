package org.saas.kmp.navigation.graph

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import org.saas.kmp.navigation.Graph
import org.saas.kmp.navigation.Routes
import org.saas.kmp.screens.auth.LoginScreen
import org.saas.kmp.screens.auth.SignUpScreen

fun NavGraphBuilder.authNavGraph(
    rootNavController: NavHostController,
    onLoginSuccess: () -> Unit
) {
    navigation(
        startDestination = Routes.Login.route,
        route = Graph.AUTH_GRAPH
    ) {
        composable(route = Routes.Login.route) {
            LoginScreen(
                navController = rootNavController,
                onLoginSuccess = onLoginSuccess
            )
        }
        
        composable(route = Routes.SignUp.route) {
            SignUpScreen(
                navController = rootNavController,
                onSignUpSuccess = onLoginSuccess
            )
        }
    }
}
