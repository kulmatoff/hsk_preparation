package com.example.muse.data.progress

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

@Dao
interface ProgressDao {

    // --- Прогресс по темам ---

    @Upsert
    suspend fun upsertProgress(progress: TopicProgressEntity)

    @Query("DELETE FROM topic_progress WHERE subjectId = :subjectId AND topicIndex = :topicIndex")
    suspend fun clearProgress(subjectId: String, topicIndex: Int)

    @Query("SELECT * FROM topic_progress")
    fun observeAllProgress(): Flow<List<TopicProgressEntity>>

    @Query("SELECT * FROM topic_progress WHERE subjectId = :subjectId")
    fun observeSubjectProgress(subjectId: String): Flow<List<TopicProgressEntity>>

    // --- Домашние задания ---

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertHomework(tasks: List<HomeworkTaskEntity>)

    @Query("SELECT * FROM homework_tasks ORDER BY subjectId, topicIndex, id")
    fun observeHomework(): Flow<List<HomeworkTaskEntity>>

    @Query("UPDATE homework_tasks SET done = :done WHERE id = :id")
    suspend fun setHomeworkDone(id: String, done: Boolean)

    @Query("SELECT COUNT(*) FROM homework_tasks WHERE done = 0")
    fun observePendingHomeworkCount(): Flow<Int>

    @Query("SELECT COUNT(*) FROM homework_tasks")
    suspend fun homeworkCount(): Int
}
