package com.varunkumar.notesapp.presentation.viewmodels

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.varunkumar.notesapp.QueryFindType
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
    private val _queryFindType = MutableStateFlow(QueryFindType.TITLE)

    private var _state = MutableStateFlow(
        SearchState(query = mutableStateOf(savedStateHandle["query"] ?: "").value)
    )

    private val _notes = _queryFindType.flatMapLatest { queryFindType ->
        val subString = _state.value.query

        when (queryFindType) {
            QueryFindType.DATE -> dao.getNotesByDate(subString)
            QueryFindType.TITLE -> dao.getNotesByTitle(subString)
            QueryFindType.CONTENT -> dao.getNotesByContent(subString)
        }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(), emptyList())

    val state = combine(_state, _queryFindType, _notes) { state, queryFindType, notes ->
        state.copy(
            items = notes,
            queryQueryFindType = queryFindType
        )
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), _state.value)

    fun onQueryFindTypeChange(newQueryFindType: QueryFindType) {
        _queryFindType.value = newQueryFindType
    }

    fun onQueryChange(newQuery: String) {
        savedStateHandle["query"] = newQuery
        _state.value = _state.value.copy(
            query = newQuery
        )
    }
}

data class SearchState(
    val query: String = "",
    val items: List<Note> = emptyList(),
    val queryQueryFindType: QueryFindType = QueryFindType.TITLE
)
