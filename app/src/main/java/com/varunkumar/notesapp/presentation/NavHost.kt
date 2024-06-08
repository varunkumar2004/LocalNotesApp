package com.varunkumar.notesapp.presentation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.varunkumar.notesapp.presentation.screens.DraftScreen
import com.varunkumar.notesapp.presentation.screens.HomeScreen
import com.varunkumar.notesapp.presentation.screens.SearchScreen
import com.varunkumar.notesapp.presentation.viewmodels.AppViewModel
import com.varunkumar.notesapp.utils.Routes
import com.varunkumar.notesapp.utils.prevBackTraceRoute

@Composable
fun CustomNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    appViewModel: AppViewModel,
    startDestination: String,
) {
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = startDestination
    ) {
        composable(route = Routes.Home.route) { backStackEntry ->
            HomeScreen(
                modifier = Modifier
                    .fillMaxSize(),
                viewModel = appViewModel,
                navController = navController,
                onNoteClick = { note ->
                    backStackEntry.savedStateHandle["prevRoute"] = Routes.Home.route
                    navController.navigate(Routes.Draft.route + "/${note.id}")
                    appViewModel.selectItem(Routes.Home)
                }
            )
        }

        composable(route = Routes.Search.route) { backStackEntry ->
            SearchScreen(
                modifier = Modifier
                    .fillMaxSize(),
                appViewModel = appViewModel,
                navController = navController,
                onNoteClick = { note ->
                    backStackEntry.savedStateHandle["prevRoute"] = Routes.Search.route
                    navController.navigate(Routes.Draft.route + "/${note.id}")
                    appViewModel.selectItem(Routes.Home)
                }
            )
        }

        composable(
            route = Routes.Draft.route + "/{id}",
            arguments = listOf(
                navArgument("id") { type = NavType.IntType }
            )
        ) { backStackEntry ->
            val id = backStackEntry.arguments?.getInt("id")
            val prevRouteString =
                backStackEntry.savedStateHandle.get<String>("prevRoute")

            DraftScreen(
                modifier = Modifier.fillMaxSize(),
                id = id ?: -1,
                onBackClick = {
                    if (prevRouteString != null) {
                        val prevRoute = prevBackTraceRoute(prevRouteString)
                        navController.navigateUp()
                        appViewModel.selectItem(prevRoute)
                    } else {
                        navController.navigate(Routes.Home.route)
                        appViewModel.selectItem(Routes.Home)
                    }
                },
                appViewModel = appViewModel,
                navController = navController,
                onSavedClick = {
                    navController.navigate(Routes.Home.route)
                    appViewModel.selectItem(Routes.Home)
                }
            )
        }
    }
}