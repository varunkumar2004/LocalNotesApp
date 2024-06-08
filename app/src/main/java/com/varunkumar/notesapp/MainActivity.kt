package com.varunkumar.notesapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.rememberNavController
import com.varunkumar.notesapp.presentation.CustomNavHost
import com.varunkumar.notesapp.presentation.viewmodels.AppViewModel
import com.varunkumar.notesapp.presentation.viewmodels.DraftViewModel
import com.varunkumar.notesapp.presentation.viewmodels.SearchViewModel
import com.varunkumar.notesapp.ui.theme.NotesAppTheme
import com.varunkumar.notesapp.utils.Routes
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val navController = rememberNavController()
            val appViewModel = hiltViewModel<AppViewModel>()
            val searchViewModel = hiltViewModel<SearchViewModel>()
            val draftViewModel = hiltViewModel<DraftViewModel>()

            NotesAppTheme {
                CustomNavHost(
                    navController = navController,
                    appViewModel = appViewModel,
                    startDestination = Routes.Home.route
                )
            }
        }
    }
}