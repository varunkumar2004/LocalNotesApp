package com.varunkumar.notesapp.utils

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.EditNote
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.ui.graphics.vector.ImageVector
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