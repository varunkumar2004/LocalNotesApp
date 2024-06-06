package com.varunkumar.notesapp.ui.theme

import androidx.compose.material3.NavigationBarItemColors
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

val Purple80 = Color(0xFFD0BCFF)
val PurpleGrey80 = Color(0xFFCCC2DC)
val Pink80 = Color(0xFFEFB8C8)

val Purple40 = Color(0xFF6650a4)
val PurpleGrey40 = Color(0xFF625b71)
val Pink40 = Color(0xFF7D5260)

val whitesmoke = Color.White

val lightPink = Color(0xFFFFEDEB)
val indicatorPInk = Color(0xFFFFB9B2)
val mediumPink = Color(0xFF4E404A)
val darkPink = Color(0xFF1B181D)

val noteHeaderColors = listOf(
    Color(0xFF39FF14),
    Color(0xFF8F50FF),
    Color(0xFF00FFFF),
    Color(0xFFFFD700),
    Color(0xFFFA8072),
    Color(0xFFBF00FF),
)

@Composable
fun customTextFieldColors(
    backgroundColor: Color = Color.Transparent
): TextFieldColors {
    return TextFieldDefaults.colors(
        cursorColor = darkPink,
        focusedContainerColor = backgroundColor,
        unfocusedContainerColor = backgroundColor,
        focusedIndicatorColor = Color.Transparent,
        unfocusedIndicatorColor = Color.Transparent
    )
}



@Composable
fun customNavigationBarItemColors() : NavigationBarItemColors {
    return NavigationBarItemDefaults.colors(
        selectedIconColor = darkPink,
        unselectedIconColor = mediumPink,
        selectedTextColor = darkPink,
        unselectedTextColor = mediumPink,
        indicatorColor = indicatorPInk
    )
}
