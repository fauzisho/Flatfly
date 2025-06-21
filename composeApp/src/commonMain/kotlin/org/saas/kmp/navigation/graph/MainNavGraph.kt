package org.saas.kmp.navigation.graph

import androidx.compose.foundation.layout.PaddingValues
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import org.saas.kmp.navigation.Graph
import org.saas.kmp.navigation.Routes
import org.saas.kmp.screens.HomeScreen
import org.saas.kmp.screens.AccountScreen
import org.saas.kmp.screens.DocumentScreen
import org.saas.kmp.screens.ResidenceScreen
import org.saas.kmp.screens.BuddyHelpScreen

fun NavGraphBuilder.mainNavGraph(
    rootNavController: NavHostController,
    innerPadding: PaddingValues,
    onDialogVisibilityChange: (Boolean) -> Unit
) {
    navigation(
        startDestination = Routes.Home.route,
        route = Graph.NAVIGATION_BAR_SCREEN_GRAPH
    ) {
        composable(route = Routes.Home.route) {
            HomeScreen(rootNavController = rootNavController, paddingValues = innerPadding)
        }
        composable(route = Routes.Document.route) {
            DocumentScreen(
                rootNavController = rootNavController, 
                paddingValues = innerPadding,
                onDialogVisibilityChange = onDialogVisibilityChange
            )
        }
        composable(route = Routes.Residence.route) {
            ResidenceScreen(
                rootNavController = rootNavController, 
                paddingValues = innerPadding,
                onDialogVisibilityChange = onDialogVisibilityChange
            )
        }
        composable(route = Routes.BuddyHelp.route) {
            BuddyHelpScreen(rootNavController = rootNavController, paddingValues = innerPadding)
        }
        composable(route = Routes.Account.route) {
            AccountScreen(rootNavController = rootNavController, paddingValues = innerPadding)
        }
    }
}