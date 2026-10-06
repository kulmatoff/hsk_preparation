package com.example.muse.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "lessons")
data class LessonEntity(
    @PrimaryKey
    val id: Int,
    val chapterId: Int,
    val courseId: Int,
    val chapterTitle: String,
    val chapterPosition: Int,
    val title: String,
    val content: String,
    val position: Int
)