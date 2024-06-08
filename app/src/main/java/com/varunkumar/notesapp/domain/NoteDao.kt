package com.varunkumar.notesapp.domain

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import com.varunkumar.notesapp.domain.models.Note
import kotlinx.coroutines.flow.Flow

@Dao
interface NoteDao {
    @Upsert
    suspend fun upsertNote(note: Note)

    @Delete
    suspend fun deleteNote(note: Note)

    @Query("SELECT * FROM Notes ORDER BY date_created DESC")
    fun getAllNotes(): Flow<List<Note>>

    @Query("SELECT * FROM Notes WHERE id=:id")
    fun getNoteById(id: Int): Flow<Note>

    @Query("SELECT * FROM Notes WHERE title LIKE '%' || :subTitle || '%'")
    fun getNotesByTitle(subTitle: String): Flow<List<Note>>

    @Query("SELECT * FROM Notes WHERE content LIKE '%' || :subContent || '%'")
    fun getNotesByContent(subContent: String): Flow<List<Note>>

    @Query("SELECT * FROM Notes WHERE date_created LIKE '%' || :subDate || '%'")
    fun getNotesByDate(subDate: String): Flow<List<Note>>

    @Query("SELECT * FROM Notes WHERE is_pinned=1 ORDER BY date_created DESC")
    fun getAllPinnedNotes(): Flow<List<Note>>
}
