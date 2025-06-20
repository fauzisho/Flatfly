package org.saas.kmp.navigation.graph

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import org.saas.kmp.navigation.Graph
import org.saas.kmp.navigation.Routes
import org.saas.kmp.screens.HomeDetailScreen
import org.saas.kmp.screens.SettingDetailScreen

@Composable
fun RootNavGraph(
    rootNavController: NavHostController,
    innerPadding: PaddingValues
) {
    NavHost(
        navController = rootNavController,
        startDestination = Graph.NAVIGATION_BAR_SCREEN_GRAPH,
    ) {
        mainNavGraph(rootNavController = rootNavController, innerPadding = innerPadding)
        composable(
            route = Routes.HomeDetail.route,
        ) {
            rootNavController.previousBackStackEntry?.savedStateHandle?.get<String>("name")?.let { name ->
                HomeDetailScreen(rootNavController = rootNavController, name = name)
            }
        }
        composable(
            route = Routes.SettingDetail.route,
        ) {
            SettingDetailScreen(rootNavController = rootNavController)
        }
    }
}