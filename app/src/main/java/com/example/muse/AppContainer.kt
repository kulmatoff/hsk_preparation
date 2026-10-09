package com.example.muse

import android.content.Context
import com.example.muse.data.content.ContentProvider
import com.example.muse.data.repository.ContentRepository

/**
 * Оставлено для совместимости: реальные синглтоны живут в [ContentProvider],
 * чтобы вся Room-БД была одна на приложение.
 */
class AppContainer(context: Context) {

    val contentRepository: ContentRepository = ContentProvider.repository(context)
}