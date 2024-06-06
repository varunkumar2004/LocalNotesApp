package com.varunkumar.notesapp.presentation.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BookmarkAdd
import androidx.compose.material.icons.filled.BookmarkAdded
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.varunkumar.notesapp.presentation.viewmodels.AppViewModel
import com.varunkumar.notesapp.presentation.viewmodels.DraftViewModel
import com.varunkumar.notesapp.ui.theme.customTextFieldColors
import com.varunkumar.notesapp.ui.theme.darkPink
import com.varunkumar.notesapp.ui.theme.lightPink
import com.varunkumar.notesapp.utils.CustomNavigationBar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DraftScreen(
    modifier: Modifier = Modifier,
    id: Int,
    appViewModel: AppViewModel,
    navController: NavHostController,
    onBackClick: () -> Unit,
    onSavedClick: () -> Unit
) {
    //initialized here because only used by this screen
    val viewModel = hiltViewModel<DraftViewModel>()
    val state = viewModel.state
    var isTitleFocused by remember { mutableStateOf(false) }
    var isContentFocused by remember { mutableStateOf(false) }

    if (id != -1) {
        viewModel.getNoteById(id)
    }

    Scaffold(
        modifier = modifier,
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = lightPink
                ),
                navigationIcon = {
                    IconButton(onClick = { onBackClick() }) {
                        Icon(
                            imageVector = Icons.Default.KeyboardArrowLeft,
                            contentDescription = "Back"
                        )
                    }
                },
                title = {
                    Text(
                        maxLines = 1,
                        text = "Draft",
                        style = MaterialTheme.typography.titleLarge,
                        overflow = TextOverflow.Ellipsis
                    )
                },
                actions = {
                    if (id != -1) {
                        IconButton(
                            onClick = {
                                viewModel.deleteNote(id)
                                onSavedClick()
                            }
                        ) {
                            Icon(
                                tint = darkPink,
                                imageVector = Icons.Default.Delete,
                                contentDescription = "Delete Note"
                            )
                        }
                    }

                    IconButton(
                        onClick = {
                            if (id != -1) {
                                viewModel.updateNote(id)
                            } else {
                                if (state.title.isNotBlank() || state.title.isNotEmpty()) {
                                    viewModel.saveNote()
                                }
                            }

                            onSavedClick()
                        }
                    ) {
                        Icon(
                            tint = darkPink,
                            imageVector = Icons.Default.Done,
                            contentDescription = "Save Note"
                        )
                    }
                }
            )
        },
        bottomBar = {
            CustomNavigationBar(
                appViewModel = appViewModel,
                navController = navController
            )
        }
    ) {
        Column(
            modifier = Modifier
                .padding(it)
                .padding(vertical = 5.dp, horizontal = 10.dp)
        ) {
            IconButton(onClick = viewModel::onIsPinnedChange) {
                Icon(
                    tint = darkPink,
                    imageVector = if (state.isPinned) Icons.Default.BookmarkAdded else Icons.Default.BookmarkAdd,
                    contentDescription = null
                )
            }

            TextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .onFocusChanged { isTitleFocused = it.isFocused },
                colors = customTextFieldColors(),
                value = state.title,
                onValueChange = {title ->
                    if (title.length <= 100) {
                        viewModel.onTitleChange(title)
                    }
                },
                textStyle = MaterialTheme.typography.titleLarge,
                placeholder = {
                    Text(
                        text = "Title",
                        style = MaterialTheme.typography.titleLarge
                    )
                },
                trailingIcon = {
                    if (state.title.isNotBlank() && isTitleFocused) {
                        IconButton(onClick = { viewModel.onTitleChange("") }) {
                            Icon(imageVector = Icons.Default.Clear, contentDescription = "Clear")
                        }
                    }
                }
            )

            TextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .onFocusChanged { isContentFocused = it.isFocused },
                value = state.content,
                colors = customTextFieldColors(),
                onValueChange = {
                    viewModel.onContentChange(it)
                },
                shape = RoundedCornerShape(20.dp),
                singleLine = false,
                placeholder = { Text(text = "Enter text") },
                trailingIcon = {
                    if (state.content.isNotBlank() && isContentFocused) {
                        IconButton(onClick = { viewModel.onContentChange("") }) {
                            Icon(imageVector = Icons.Default.Clear, contentDescription = "Clear")
                        }
                    }
                }
            )
        }
    }
}

//@Composable
//fun SelectColor(
//    modifier: Modifier = Modifier
//) {
//    DropdownMenu(expanded = true, onDismissRequest = { /*TODO*/ }) {
//        LazyRow(
//            modifier = modifier,
//            verticalAlignment = Alignment.CenterVertically,
//            horizontalArrangement = Arrangement.spacedBy(5.dp)
//        ) {
//            itemsIndexed(noteHeaderColors) { _, color ->
//                Box(
//                    modifier = Modifier
//                        .clip(CircleShape)
//                        .size(20.dp)
//                        .background(color)
//                        .border(1.dp, Color.Black, CircleShape)
//                )
//            }
//        }
//    }
//}

@Preview(showBackground = true)
@Composable
private fun Prev() {
}