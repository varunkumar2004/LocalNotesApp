package com.varunkumar.notesapp.presentation.viewmodels

    import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.varunkumar.notesapp.domain.NoteDao
import com.varunkumar.notesapp.domain.models.Note
import com.varunkumar.notesapp.utils.Routes
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AppViewModel @Inject constructor(
    private val dao: NoteDao
) : ViewModel() {
    private val _state = MutableStateFlow(AppState())
    val state = _state

    init {
        getAllNotes()
        getPinnedNotes()
    }

    private fun getAllNotes() {
        viewModelScope.launch {
            dao.getAllNotes()
                .collect { notes ->
                    _state.value = _state.value.copy(allNotes = notes)
                }
        }
    }

    private fun getPinnedNotes() {
        viewModelScope.launch {
            dao.getAllPinnedNotes()
                .collect { notes ->
                    _state.value = _state.value.copy(pinnedNotes = notes)
                }
        }
    }

    fun selectItem(route: Routes) {
        _state.value = _state.value.copy(
            selectedItem = route
        )
    }
}

data class AppState(
    val selectedItem: Routes = Routes.Home,
    val allNotes: List<Note> = emptyList(),
    val pinnedNotes: List<Note> = emptyList()
)