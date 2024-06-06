package com.varunkumar.notesapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.rememberNavController
import com.varunkumar.notesapp.presentation.screens.AppScreen
import com.varunkumar.notesapp.presentation.viewmodels.AppViewModel
import com.varunkumar.notesapp.presentation.viewmodels.HomeViewModel
import com.varunkumar.notesapp.presentation.viewmodels.SearchViewModel
import com.varunkumar.notesapp.ui.theme.NotesAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val navController = rememberNavController()
            val appViewModel = hiltViewModel<AppViewModel>()
            val searchViewModel = hiltViewModel<SearchViewModel>()
            val homeViewModel = hiltViewModel<HomeViewModel>()

            NotesAppTheme {
                AppScreen(
                    modifier = Modifier.fillMaxSize(),
                    appViewModel = appViewModel,
                    homeViewModel = homeViewModel,
                    searchViewModel = searchViewModel,
                    navController = navController
                )
            }
        }
    }
}