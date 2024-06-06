package com.varunkumar.notesapp.presentation.screens

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.varunkumar.notesapp.presentation.viewmodels.AppViewModel
import com.varunkumar.notesapp.ui.theme.customNavigationBarItemColors
import com.varunkumar.notesapp.ui.theme.darkPink
import com.varunkumar.notesapp.ui.theme.lightPink
import com.varunkumar.notesapp.utils.Routes

@Composable
fun AppScreen(
    modifier: Modifier = Modifier,
    appViewModel: AppViewModel,
    navController: NavHostController
) {
    val selectedItem = appViewModel.state.collectAsState().value.selectedItem

    Scaffold(
        modifier = modifier,
        bottomBar = {
            NavigationBar(
                containerColor = lightPink,
                contentColor = darkPink

            ) {
                NavigationBarItem(
                    colors = customNavigationBarItemColors(),
                    selected = selectedItem == Routes.Home,
                    onClick = {
                        appViewModel.selectItem(Routes.Home)
                        navController.navigate(Routes.Home.route)
                    },
                    alwaysShowLabel = selectedItem == Routes.Home,
                    label = { Text(text = Routes.Home.route) },
                    icon = {
                        Icon(
                            imageVector = Routes.Home.icon,
                            contentDescription = "Home"
                        )
                    }
                )

                NavigationBarItem(
                    colors = customNavigationBarItemColors(),
                    selected = selectedItem == Routes.Search,
                    onClick = {
                        appViewModel.selectItem(Routes.Search)
                        navController.navigate(Routes.Search.route)
                    },
                    alwaysShowLabel = selectedItem == Routes.Search,
                    label = { Text(text = Routes.Search.route) },
                    icon = {
                        Icon(
                            imageVector = Routes.Search.icon,
                            contentDescription = "Search"
                        )
                    }
                )

                NavigationBarItem(
                    colors = customNavigationBarItemColors(),
                    selected = selectedItem == Routes.Draft,
                    onClick = {
                        appViewModel.selectItem(Routes.Draft)
                        navController.navigate(Routes.Draft.route + "/-1")
                    },
                    alwaysShowLabel = selectedItem == Routes.Draft,
                    label = { Text(text = Routes.Draft.route) },
                    icon = {
                        Icon(
                            imageVector = Routes.Draft.icon,
                            contentDescription = "Add Note"
                        )
                    }
                )
            }
        }
    ) {
        val scaffoldPadding = Modifier
            .padding(it)

        NavHost(navController = navController, startDestination = Routes.Home.route) {
            composable(route = Routes.Home.route) {
                HomeScreen(
                    modifier = scaffoldPadding
                        .padding(top = 10.dp)
                        .padding(horizontal = 10.dp)
                        .fillMaxSize(),
                    viewModel = appViewModel,
                    onNoteClick = { note ->
                        navController.navigate(Routes.Draft.route + "/${note.id}")
                    }
                )
            }

            composable(route = Routes.Search.route) {
                SearchScreen(
                    modifier = scaffoldPadding
                        .padding(top = 10.dp)
                        .padding(horizontal = 10.dp)
                        .fillMaxSize(),
                    onNoteClick = { note ->
                        navController.navigate(Routes.Draft.route + "/${note.id}")
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

                DraftScreen(
                    modifier = scaffoldPadding.fillMaxSize(),
                    id = id ?: -1,
                    onBackClick = {
                        //TODO handle prev route here
                        appViewModel.selectItem(Routes.Home)
                        navController.navigateUp()
                    },
                    onSavedClick = {
                        navController.navigate(Routes.Home.route)
                    }
                )
            }
        }
    }
}

@Composable
fun customNavigationBar(
    modifier: Modifier = Modifier,
    appViewModel: AppViewModel,
    navController: NavHostController
) {
    val selectedItem = appViewModel.state.collectAsState().value.selectedItem

    NavigationBar(
        containerColor = lightPink,
        contentColor = darkPink
    ) {
        NavigationBarItem(
            colors = customNavigationBarItemColors(),
            selected = selectedItem == Routes.Home,
            onClick = {
                appViewModel.selectItem(Routes.Home)
                navController.navigate(Routes.Home.route)
            },
            alwaysShowLabel = selectedItem == Routes.Home,
            label = { Text(text = Routes.Home.route) },
            icon = {
                Icon(
                    imageVector = Routes.Home.icon,
                    contentDescription = "Home"
                )
            }
        )

        NavigationBarItem(
            colors = customNavigationBarItemColors(),
            selected = selectedItem == Routes.Search,
            onClick = {
                appViewModel.selectItem(Routes.Search)
                navController.navigate(Routes.Search.route)
            },
            alwaysShowLabel = selectedItem == Routes.Search,
            label = { Text(text = Routes.Search.route) },
            icon = {
                Icon(
                    imageVector = Routes.Search.icon,
                    contentDescription = "Search"
                )
            }
        )

        NavigationBarItem(
            colors = customNavigationBarItemColors(),
            selected = selectedItem == Routes.Draft,
            onClick = {
                appViewModel.selectItem(Routes.Draft)
                navController.navigate(Routes.Draft.route + "/-1")
            },
            alwaysShowLabel = selectedItem == Routes.Draft,
            label = { Text(text = Routes.Draft.route) },
            icon = {
                Icon(
                    imageVector = Routes.Draft.icon,
                    contentDescription = "Add Note"
                )
            }
        )
    }
}

