package com.example.muse.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [
        CourseEntity::class,
        ChapterEntity::class,
        LessonEntity::class,
        ContentVersionEntity::class
    ],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun contentDao(): ContentDao
}