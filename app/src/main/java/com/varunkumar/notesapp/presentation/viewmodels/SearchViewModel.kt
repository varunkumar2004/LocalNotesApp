package com.varunkumar.notesapp.presentation.viewmodels

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.varunkumar.notesapp.utils.QueryFindType
import com.varunkumar.notesapp.domain.NoteDao
import com.varunkumar.notesapp.domain.models.Note
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@OptIn(ExperimentalCoroutinesApi::class)
@HiltViewModel
class SearchViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val dao: NoteDao
) : ViewModel() {
    private var _state = MutableStateFlow(
        SearchState(query = savedStateHandle["query"] ?: "")
    )

    private val _notes = _state.flatMapLatest { state ->
        when (state.queryQueryFindType) {
            QueryFindType.DATE -> dao.getNotesByDate(state.query)
            QueryFindType.TITLE -> dao.getNotesByTitle(state.query)
            QueryFindType.CONTENT -> dao.getNotesByContent(state.query)
        }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(), emptyList())

    val state = combine(_state, _notes) { state, notes ->
        state.copy(items = notes)
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), SearchState())

    fun onQueryFindTypeChange(newQueryFindType: QueryFindType) {
        _state.value = _state.value.copy(queryQueryFindType = newQueryFindType)
    }

    fun onQueryChange(newQuery: String) {
        savedStateHandle["query"] = newQuery
        _state.value = _state.value.copy(query = newQuery)
    }
}

data class SearchState(
    val query: String = "",
    val items: List<Note> = emptyList(),
    val queryQueryFindType: QueryFindType = QueryFindType.TITLE
)
