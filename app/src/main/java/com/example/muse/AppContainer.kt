package com.example.muse

import android.content.Context
import androidx.room.Room
import com.example.muse.data.local.AppDatabase
import com.example.muse.data.network.RetrofitClient
import com.example.muse.data.repository.ContentRepository

class AppContainer(context: Context) {

    private val database = Room.databaseBuilder(
        context,
        AppDatabase::class.java,
        "muse.db"
    ).build()

    val contentRepository = ContentRepository(
        api = RetrofitClient.api,
        database = database
    )
}