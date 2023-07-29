package com.kgoncharov.mvi_demo.presentation

import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.kgoncharov.mvi_demo.presentation.main.MainScreen
import com.kgoncharov.mvi_demo.presentation.main.MainViewModel

@Composable
fun TodoApp() {
    val navController = rememberNavController()
    MaterialTheme {
        NavHost(
            navController = navController,
            startDestination = Route.MAIN_SCREEN,
            ) {
            mainScreenRoute()
        }
    }
}

private fun NavGraphBuilder.mainScreenRoute() {
    composable(Route.MAIN_SCREEN) {
        val viewModel = hiltViewModel<MainViewModel>()
        MainScreen(viewModel)
    }
}

object Route {
    const val MAIN_SCREEN = "MainScreen"
}