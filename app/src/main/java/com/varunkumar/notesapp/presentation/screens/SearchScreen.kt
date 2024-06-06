package com.varunkumar.notesapp.presentation.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BookmarkAdded
import androidx.compose.material.icons.filled.FilterAlt
import androidx.compose.material.icons.filled.RadioButtonChecked
import androidx.compose.material.icons.filled.RadioButtonUnchecked
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Divider
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MenuDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.varunkumar.notesapp.QueryFindType
import com.varunkumar.notesapp.domain.models.Note
import com.varunkumar.notesapp.presentation.viewmodels.SearchViewModel
import com.varunkumar.notesapp.ui.theme.customTextFieldColors
import com.varunkumar.notesapp.ui.theme.darkPink
import com.varunkumar.notesapp.ui.theme.lightPink
import com.varunkumar.notesapp.ui.theme.mediumPink

@Composable
fun SearchScreen(
    modifier: Modifier = Modifier,
    viewModel: SearchViewModel,
    onNoteClick: (Note) -> Unit
) {
    var showDropDown by remember {
        mutableStateOf(false)
    }

    val state = viewModel.state.collectAsState().value

    val queryFindTypeString = when (state.queryQueryFindType) {
        QueryFindType.DATE -> "Date"
        QueryFindType.TITLE -> "Title"
        QueryFindType.CONTENT -> "Content"
    }

    Column(
        modifier = modifier
    ) {
        TextField(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(40.dp),
            colors = customTextFieldColors(lightPink),
            value = state.query,
            singleLine = true,
            placeholder = { Text(text = "Search") },
            onValueChange = {
                viewModel.onQueryChange(it)
            },
            leadingIcon = {
                Icon(imageVector = Icons.Default.Search, contentDescription = "Search")
            },
            trailingIcon = {
                IconButton(onClick = { showDropDown = true }) {
                    Icon(imageVector = Icons.Default.FilterAlt, contentDescription = "Filter")
                }

                if (showDropDown) {
                    DropdownMenu(
                        modifier = Modifier
                            .background(lightPink),
                        expanded = true,
                        onDismissRequest = { showDropDown = false }
                    ) {
                        DropdownMenuItem(
                            colors = MenuDefaults.itemColors(
                                textColor = darkPink,
                                trailingIconColor = mediumPink
                            ),
                            text = { Text(text = "Title") },
                            onClick = {
                                viewModel.onQueryFindTypeChange(QueryFindType.TITLE)
                                showDropDown = false
                            },
                            trailingIcon = {
                                Icon(
                                    imageVector =
                                    if (state.queryQueryFindType == QueryFindType.TITLE)
                                        Icons.Default.RadioButtonChecked else Icons.Default.RadioButtonUnchecked,
                                    contentDescription = null
                                )
                            }
                        )
                        DropdownMenuItem(
                            text = { Text(text = "Date") },
                            onClick = {
                                viewModel.onQueryFindTypeChange(QueryFindType.DATE)
                                showDropDown = false
                            },
                            trailingIcon = {
                                Icon(
                                    imageVector =
                                    if (state.queryQueryFindType == QueryFindType.DATE)
                                        Icons.Default.RadioButtonChecked else Icons.Default.RadioButtonUnchecked,
                                    contentDescription = null
                                )
                            }
                        )
                        DropdownMenuItem(
                            text = { Text(text = "Content") },
                            onClick = {
                                viewModel.onQueryFindTypeChange(QueryFindType.CONTENT)
                                showDropDown = false
                            },
                            trailingIcon = {
                                Icon(
                                    imageVector =
                                    if (state.queryQueryFindType == QueryFindType.CONTENT)
                                        Icons.Default.RadioButtonChecked else Icons.Default.RadioButtonUnchecked,
                                    contentDescription = null
                                )
                            }
                        )
                    }
                }
            }
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                modifier = Modifier.size(15.dp),
                imageVector = Icons.Default.Warning,
                contentDescription = null,
                tint = mediumPink
            )

            Text(
                style = MaterialTheme.typography.bodySmall,
                color = mediumPink,
                modifier = Modifier.padding(16.dp),
                text = "Search results are based on $queryFindTypeString matches"
            )
        }

        NoteList(
            modifier = Modifier.clickable {

            },
            notes = state.items,
            toShowPinned = true,
            onNoteClick = onNoteClick
        )
    }
}

@Composable
fun NoteList(
    modifier: Modifier = Modifier,
    notes: List<Note>,
    toShowPinned: Boolean,
    onNoteClick: (Note) -> Unit
) {
    LazyColumn {
        items(notes) { note ->
            ListItem(
                modifier = modifier
                    .clickable {
                        onNoteClick(note)
                    },
                headlineContent = {
                    Text(
                        color = darkPink,
                        text = note.title,
                        maxLines = 1,
                        style = MaterialTheme.typography.bodyLarge
                    )
                },
                supportingContent = {
                    Text(
                        color = mediumPink,
                        text = if (note.content.isNullOrBlank()) "..." else note.content,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis,
                        style = MaterialTheme.typography.bodySmall
                    )
                },
                leadingContent = {
                    if (toShowPinned && note.isPinned) {
                        Icon(
                            tint = mediumPink,
                            imageVector = Icons.Default.BookmarkAdded,
                            contentDescription = null
                        )
                    }
                },
                trailingContent = {
                    Text(
                        text = note.date,
                        color = mediumPink,
                        style = MaterialTheme.typography.bodySmall
                    )
                }
            )

            Divider(color = lightPink)
        }
    }
}

@Preview
@Composable
private fun ListPrev() {
    val note = Note(
        id = 1,
        title = "Title askdf kljadsh flkah  sdlkf hiwueh fkljlsdhf lkajdsh flkajshdflkahs",
        content = "Content",
        date = "Date",
        isPinned = true
    )


}
