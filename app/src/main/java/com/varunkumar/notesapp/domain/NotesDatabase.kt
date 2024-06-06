package com.varunkumar.notesapp.domain

import androidx.room.Database
import androidx.room.RoomDatabase
import com.varunkumar.notesapp.domain.models.Note

@Database(
    entities = [Note::class],
    version = 1
)
abstract class NotesDatabase : RoomDatabase() {
    abstract val dao: NoteDao
}