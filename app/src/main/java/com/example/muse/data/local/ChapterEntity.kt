package com.example.muse.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "chapters")
data class ChapterEntity(
    @PrimaryKey
    val id: Int,
    val courseId: Int,
    val title: String,
    val position: Int
)