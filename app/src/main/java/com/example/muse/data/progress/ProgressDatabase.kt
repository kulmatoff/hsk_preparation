package com.example.muse.data.progress

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [
        TopicProgressEntity::class,
        HomeworkTaskEntity::class
    ],
    version = 1,
    exportSchema = false
)
abstract class ProgressDatabase : RoomDatabase() {
    abstract fun progressDao(): ProgressDao
}
