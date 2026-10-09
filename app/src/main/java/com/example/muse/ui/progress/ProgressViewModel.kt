package com.example.muse.ui.progress

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.muse.data.content.ContentProvider
import com.example.muse.data.progress.HomeworkTaskEntity
import com.example.muse.data.progress.ProgressProvider
import com.example.muse.data.progress.ProgressRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

/**
 * Общий ViewModel прогресса.
 * Экземпляр создаётся на каждом экране, но все они читают и пишут
 * одну и ту же Room-БД, поэтому состояние согласовано автоматически.
 */
class ProgressViewModel(private val repository: ProgressRepository) : ViewModel() {

    init {
        // Задания следуют за контентом: демо → живые данные с бэкенда.
        viewModelScope.launch {
            ContentProvider.subjects.collect { subjects ->
                repository.seedHomework(subjects)
            }
        }
    }

    /** subjectId -> список пройденных номеров тем. */
    val completedTopics: StateFlow<Map<String, List<Int>>> =
        repository.observeAllProgress()
            .map { list -> list.groupBy({ it.subjectId }, { it.topicIndex }) }
            .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyMap())

    /** Все домашние задания. */
    val homework: StateFlow<List<HomeworkTaskEntity>> =
        repository.observeHomework()
            .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    /** Сколько заданий не выполнено. */
    val pendingHomeworkCount: StateFlow<Int> =
        repository.observePendingHomeworkCount()
            .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), 0)

    fun isTopicCompleted(subjectId: String, topicIndex: Int): Boolean =
        completedTopics.value[subjectId]?.contains(topicIndex) == true

    fun setTopicCompleted(subjectId: String, topicIndex: Int, completed: Boolean) {
        viewModelScope.launch {
            repository.setTopicCompleted(subjectId, topicIndex, completed)
        }
    }

    fun setHomeworkDone(id: String, done: Boolean) {
        viewModelScope.launch { repository.setHomeworkDone(id, done) }
    }

    companion object {
        fun factory(context: Context): ViewModelProvider.Factory = viewModelFactory {
            initializer {
                ProgressViewModel(ProgressProvider.repository(context))
            }
        }
    }
}
