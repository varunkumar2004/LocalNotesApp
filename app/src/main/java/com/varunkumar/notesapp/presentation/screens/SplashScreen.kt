package com.varunkumar.notesapp.presentation.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import com.varunkumar.notesapp.domain.models.Note
import com.varunkumar.notesapp.presentation.viewmodels.AppViewModel
import com.varunkumar.notesapp.ui.theme.lightPink

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MoreNotesScreen(
    modifier: Modifier = Modifier,
    appViewModel: AppViewModel,
    onItemClick: (Note) -> Unit,
    onBackClick: () -> Unit
) {
    val items = appViewModel.state.collectAsState().value.pinnedNotes

    Scaffold(
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = lightPink
                ),
                navigationIcon = {
                    IconButton(onClick = { onBackClick() }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.KeyboardArrowLeft,
                            contentDescription = "Back"
                        )
                    }
                },
                title = {
                    Text(
                        text = "Pinned",
                        style = MaterialTheme.typography.titleLarge
                    )
                }
            )
        }
    ) {
        Column(
            modifier = modifier.padding(it)
        ) {
            NoteList(
                modifier = modifier,
                notes = items,
                toShowPinned = false,
                onNoteClick = onItemClick,
            )
        }

    }

}