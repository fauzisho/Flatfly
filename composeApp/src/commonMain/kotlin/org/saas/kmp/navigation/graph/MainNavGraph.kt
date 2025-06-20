package org.saas.kmp.navigation.graph

import androidx.compose.foundation.layout.PaddingValues
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import org.saas.kmp.navigation.Graph
import org.saas.kmp.navigation.Routes
import org.saas.kmp.screens.HomeScreen
import org.saas.kmp.screens.SettingScreen

fun NavGraphBuilder.mainNavGraph(
    rootNavController: NavHostController,
    innerPadding: PaddingValues
) {
    navigation(
        startDestination = Routes.Home.route,
        route = Graph.NAVIGATION_BAR_SCREEN_GRAPH
    ) {
        composable(route = Routes.Home.route) {
            HomeScreen(rootNavController = rootNavController, paddingValues = innerPadding)
        }
        composable(route = Routes.Setting.route) {
            SettingScreen(rootNavController = rootNavController, paddingValues = innerPadding)
        }
    }

}