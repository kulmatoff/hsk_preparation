package com.example.muse.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "content_version")
data class ContentVersionEntity(
    @PrimaryKey
    val id: Int = 1,
    val version: Int
)