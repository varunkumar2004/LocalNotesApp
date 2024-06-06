package com.varunkumar.notesapp.domain.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.varunkumar.notesapp.utils.extractTimeDate

@Entity(tableName = "Notes")
data class Note(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo(name = "date_created") val date: String = extractTimeDate(System.currentTimeMillis()),
    @ColumnInfo(name = "title") val title: String,
    @ColumnInfo(name = "content") val content: String?,
    @ColumnInfo(name = "is_pinned") var isPinned: Boolean = false
)