package com.example.muse.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface ContentDao {

    @Query("SELECT * FROM courses ORDER BY id")
    suspend fun getCourses(): List<CourseEntity>

    @Query("SELECT * FROM chapters ORDER BY courseId, position")
    suspend fun getChapters(): List<ChapterEntity>

    @Query("SELECT * FROM lessons ORDER BY courseId, chapterPosition, position")
    suspend fun getLessons(): List<LessonEntity>

    @Query("SELECT * FROM content_version WHERE id = 1")
    suspend fun getContentVersion(): ContentVersionEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCourses(courses: List<CourseEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertChapters(chapters: List<ChapterEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertLessons(lessons: List<LessonEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertContentVersion(version: ContentVersionEntity)

    @Query("DELETE FROM courses")
    suspend fun deleteCourses()

    @Query("DELETE FROM chapters")
    suspend fun deleteChapters()

    @Query("DELETE FROM lessons")
    suspend fun deleteLessons()
}