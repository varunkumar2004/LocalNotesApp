package com.varunkumar.notesapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.varunkumar.notesapp.presentation.screens.DraftScreen
import com.varunkumar.notesapp.presentation.screens.HomeScreen
import com.varunkumar.notesapp.presentation.screens.MoreNotesScreen
import com.varunkumar.notesapp.presentation.screens.SearchScreen
import com.varunkumar.notesapp.presentation.viewmodels.AppViewModel
import com.varunkumar.notesapp.ui.theme.NotesAppTheme
import com.varunkumar.notesapp.utils.Routes
import com.varunkumar.notesapp.utils.prevBackTraceRoute
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val navController = rememberNavController()
            val appViewModel = hiltViewModel<AppViewModel>()

            NotesAppTheme {
                NavHost(navController = navController, startDestination = Routes.Home.route) {
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
                            },
                            onMoreClick = {
                                backStackEntry.savedStateHandle["prevRoute"] = Routes.Home.route
                                navController.navigate(Routes.More.route)
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

                    composable(route = Routes.More.route) { backStackEntry ->
                        MoreNotesScreen(
                            modifier = Modifier
                                .fillMaxSize(),
                            appViewModel = appViewModel,
                            onItemClick = { note ->
                                backStackEntry.savedStateHandle["prevRoute"] = Routes.Search.route
                                navController.navigate(Routes.Draft.route + "/${note.id}")
                                appViewModel.selectItem(Routes.Home)
                            },
                            onBackClick = {
                                navController.navigate(Routes.Home.route)
                                appViewModel.selectItem(Routes.Home)
                            }
                        )
                    }
                }
            }
        }
    }
}