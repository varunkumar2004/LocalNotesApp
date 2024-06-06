package com.varunkumar.notesapp.utils

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.EditNote
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavHostController
import com.varunkumar.notesapp.presentation.viewmodels.AppViewModel
import com.varunkumar.notesapp.ui.theme.customNavigationBarItemColors
import com.varunkumar.notesapp.ui.theme.darkPink
import com.varunkumar.notesapp.ui.theme.lightPink
import java.text.SimpleDateFormat

fun extractTimeDate(timestamp: Long) : String {
    val simpleDateFormat = SimpleDateFormat("dd/MM/yyyy")
    return simpleDateFormat.format(timestamp)
}

sealed class Routes(val route: String, val icon: ImageVector) {
    data object Home : Routes("Home", Icons.Default.Home)
    data object Search : Routes("Search", Icons.Default.Search)
    data object Draft : Routes("Draft", Icons.Default.EditNote)
    data object Profile : Routes("Profile", Icons.Default.Person)
}

@Composable
fun CustomNavigationBar(
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
            icon = {
                Icon(
                    imageVector = Routes.Draft.icon,
                    contentDescription = "Add Note"
                )
            }
        )
    }
}