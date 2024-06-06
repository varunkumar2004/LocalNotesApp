package com.varunkumar.notesapp.presentation.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.varunkumar.notesapp.domain.models.Note
import com.varunkumar.notesapp.presentation.viewmodels.AppViewModel
import com.varunkumar.notesapp.ui.theme.lightPink

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    viewModel: AppViewModel,
    onNoteClick: (Note) -> Unit
) {
    val state = viewModel.state.collectAsState().value

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        HeaderNotes(
            modifier = Modifier.fillMaxWidth(),
            title = "Pinned",
            notes = state.pinnedNotes,
            toShowPinned = true,
            onNoteClick = onNoteClick
        )

        HeaderNotes(
            modifier = Modifier.fillMaxWidth(),
            title = "All Notes",
            notes = state.allNotes,
            toShowPinned = false,
            onNoteClick = onNoteClick
        )
    }
}

@Composable
fun HeaderNotes(
    modifier: Modifier = Modifier,
    title: String,
    notes: List<Note>,
    toShowPinned: Boolean,
    onNoteClick: (Note) -> Unit
) {
    Column(
        modifier = modifier
    ) {
        Text(
            modifier = modifier
                .padding(start = 10.dp),
            text = title,
            fontWeight = FontWeight.Bold,
            style = MaterialTheme.typography.titleLarge
        )

        if (notes.isEmpty()) {
            Text(
                modifier = modifier.padding(5.dp),
                text = "No notes found",
                style = MaterialTheme.typography.bodyMedium,
                textAlign = TextAlign.Center
            )
        } else {
            NoteList(
                modifier = Modifier,
                notes = notes,
                toShowPinned = toShowPinned,
                onNoteClick = onNoteClick
            )
        }

    }
}

@Preview(showBackground = true)
@Composable
private fun HomePre() {
}

