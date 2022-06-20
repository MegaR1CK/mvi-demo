package com.kgoncharov.mvi_demo.presentation.base

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.kgoncharov.mvi_demo.presentation.main.MainScreen
import com.kgoncharov.mvi_demo.presentation.main.MainViewModel

@Composable
fun MviApp() {
    val navController = rememberNavController()
    MaterialTheme {
        Scaffold(
            content = { paddingValues ->
                NavHost(navController = navController, startDestination = Route.MainScreen) {
                    mainScreenRoute(paddingValues)
                }
            }
        )
    }
}

private fun NavGraphBuilder.mainScreenRoute(paddingValues: PaddingValues) {
    composable(Route.MainScreen) {
        val viewModel = hiltViewModel<MainViewModel>()
        MainScreen(viewModel = viewModel, paddingValues)
    }
}

object Route {
    const val MainScreen = "MainScreen"
}
