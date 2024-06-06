package com.varunkumar.notesapp

sealed interface NoteEvent {
    object SaveNote: NoteEvent
    data class DeleteNote(val id: String): NoteEvent
    object ShowDialog: NoteEvent
    object HideDialog: NoteEvent
    data class SortNotes(val queryFindType: QueryFindType): NoteEvent
}

enum class QueryFindType {
    DATE, TITLE, CONTENT
}
