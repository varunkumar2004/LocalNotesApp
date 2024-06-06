package com.varunkumar.notesapp.presentation.viewmodels

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.varunkumar.notesapp.domain.NoteDao
import com.varunkumar.notesapp.domain.models.Note
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DraftViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val dao: NoteDao
) : ViewModel() {
    private var _state by mutableStateOf(
        AddNoteState(
            title = mutableStateOf(savedStateHandle["title"] ?: "").value,
            content = mutableStateOf(savedStateHandle["content"] ?: "").value,
            isPinned = mutableStateOf(savedStateHandle["isPinned"] ?: false).value
        )
    )
    val state get() = _state

    fun getNoteById(id: Int) {
        viewModelScope.launch {
            dao.getNoteById(id).collect { note ->
                _state = _state.copy(
                    title = note.title,
                    content = note.content ?: "",
                    isPinned = note.isPinned
                )
            }
        }

        Log.d("DraftViewModel", "getNoteById: $id")
    }

    fun onTitleChange(newTitle: String) {
        savedStateHandle["title"] = newTitle
        _state = _state.copy(title = newTitle)
    }

    fun onIsPinnedChange() {
        savedStateHandle["isPinned"] = !_state.isPinned
        _state = _state.copy(isPinned = !_state.isPinned)
    }

    fun onContentChange(newContent: String) {
        _state = _state.copy(content = newContent)
        savedStateHandle["content"] = newContent
    }

    fun updateNote(id: Int) {
        viewModelScope.launch {
            val note = Note(
                id = id,
                title = _state.title,
                content = _state.content,
                isPinned = _state.isPinned
            )
            dao.upsertNote(note)
        }
    }

    fun saveNote() {
        viewModelScope.launch {
            val note = Note(
                title = _state.title,
                content = _state.content,
                isPinned = _state.isPinned
            )
            dao.upsertNote(note)
        }
    }

    fun deleteNote(id: Int) {
        viewModelScope.launch {
            dao.deleteNote(id)
        }
    }
}

data class AddNoteState(
    val title: String,
    val content: String,
    val isPinned: Boolean
)