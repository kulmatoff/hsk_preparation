package com.example.muse.ui.network

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.muse.data.local.ChapterEntity
import com.example.muse.data.local.CourseEntity
import com.example.muse.data.local.LessonEntity
import com.example.muse.data.repository.ContentRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class ContentUiState(
    val courses: List<CourseEntity> = emptyList(),
    val chapters: List<ChapterEntity> = emptyList(),
    val lessons: List<LessonEntity> = emptyList(),
    val isLoading: Boolean = true,
    val isSyncing: Boolean = false,
    val error: String? = null,
    val syncFailed: Boolean = false
)

class ContentViewModel(
    private val repository: ContentRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(ContentUiState())
    val uiState: StateFlow<ContentUiState> = _uiState.asStateFlow()

    init {
        loadContent()
    }

    private fun loadContent() {
        viewModelScope.launch {
            try {
                val cachedCourses = repository.getCachedCourses()
                val cachedChapters = repository.getCachedChapters()
                val cachedLessons = repository.getCachedLessons()

                _uiState.value = ContentUiState(
                    courses = cachedCourses,
                    chapters = cachedChapters,
                    lessons = cachedLessons,
                    isLoading = false,
                    isSyncing = true
                )

                repository.synchronize()

                val courses = repository.getCachedCourses()
                val chapters = repository.getCachedChapters()
                val lessons = repository.getCachedLessons()

                _uiState.value = ContentUiState(
                    courses = courses,
                    chapters = chapters,
                    lessons = lessons,
                    isLoading = false,
                    isSyncing = false
                )
            } catch (e: Exception) {
                val courses = repository.getCachedCourses()
                val chapters = repository.getCachedChapters()
                val lessons = repository.getCachedLessons()

                val errorMessage = if (e is retrofit2.HttpException) {
                    "HTTP ${e.code()}: ${e.message()}"
                } else {
                    e.message
                }

                _uiState.value = _uiState.value.copy(
                    courses = courses,
                    chapters = chapters,
                    lessons = lessons,
                    isLoading = false,
                    isSyncing = false,
                    error = if (courses.isEmpty()) errorMessage else null,
                    syncFailed = true
                )
            }
        }
    }
}