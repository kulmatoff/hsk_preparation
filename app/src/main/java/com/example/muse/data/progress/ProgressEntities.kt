package com.example.muse.data.progress

import androidx.room.Entity

/** Пройденные темы: один предмет + номер темы = одна строка. */
@Entity(tableName = "topic_progress", primaryKeys = ["subjectId", "topicIndex"])
data class TopicProgressEntity(
    val subjectId: String,
    val topicIndex: Int,
    val completedAt: Long
)

/** Одно домашнее задание. */
@Entity(tableName = "homework_tasks", primaryKeys = ["id"])
data class HomeworkTaskEntity(
    val id: String,            // "math_0_1" — предмет_тема_номер
    val subjectId: String,
    val subjectName: String,
    val topicIndex: Int,
    val topicTitle: String,
    val title: String,
    val done: Boolean = false,
    val deadline: String
)
