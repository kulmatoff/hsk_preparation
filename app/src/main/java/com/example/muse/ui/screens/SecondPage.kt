package com.example.muse.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.muse.AppContainer
import com.example.muse.ui.network.ContentUiState
import com.example.muse.ui.network.ContentViewModel
import com.example.muse.ui.network.ContentViewModelFactory

import com.example.muse.data.local.CourseEntity
import com.example.muse.data.local.ChapterEntity
import com.example.muse.data.local.LessonEntity
import com.example.muse.ui.theme.MuseTheme

@Composable
fun SecondPageContent(
    innerPadding: PaddingValues,
    uiState: ContentUiState,
    courseTitle: String = ""
) {
    val filteredCourses = if (courseTitle.isNotEmpty() && courseTitle != "Chinese") {
        uiState.courses.filter { it.title.contains(courseTitle, ignoreCase = true) }
    } else {
        uiState.courses
    }

    when {
        (uiState.isLoading || uiState.isSyncing) && uiState.courses.isEmpty() -> {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                CircularProgressIndicator()
                Text(
                    text = "Загрузка контента...",
                    modifier = Modifier.padding(top = 16.dp)
                )
            }
        }

        uiState.error != null && uiState.courses.isEmpty() -> {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "Ошибка: ${uiState.error}",
                    color = Color.Red
                )
            }
        }

        filteredCourses.isEmpty() && !uiState.isLoading -> {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(text = if (courseTitle.isNotEmpty()) "Курс '$courseTitle' не найден" else "Нет доступных курсов")
            }
        }

        else -> {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                items(filteredCourses) { course ->
                    Column {
                        Text(
                            text = course.title,
                            style = MaterialTheme.typography.headlineSmall,
                            color = MaterialTheme.colorScheme.onBackground
                        )

                        for (chapter in uiState.chapters.filter { it.courseId == course.id }) {
                            Text(
                                text = chapter.title,
                                style = MaterialTheme.typography.titleMedium,
                                color = MaterialTheme.colorScheme.primary,
                                modifier = Modifier.padding(
                                    top = 8.dp
                                )
                            )

                            for (lesson in uiState.lessons.filter { it.chapterId == chapter.id }) {
                                Text(
                                    text = lesson.title,
                                    style = MaterialTheme.typography.bodyLarge,
                                    color = MaterialTheme.colorScheme.secondary,
                                    modifier = Modifier.padding(
                                        top = 4.dp,
                                        start = 16.dp
                                    )
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun SecondPage(
    courseTitle: String = "",
    contentViewModel: ContentViewModel = viewModel(
        factory = ContentViewModelFactory(
            AppContainer(LocalContext.current.applicationContext).contentRepository
        )
    )
) {
    val uiState by contentViewModel.uiState.collectAsStateWithLifecycle()
    val context = LocalContext.current

    androidx.compose.runtime.LaunchedEffect(uiState.syncFailed) {
        if (uiState.syncFailed && uiState.courses.isNotEmpty()) {
            android.widget.Toast.makeText(
                context,
                "Обновление не удалось. Используются сохраненные данные.",
                android.widget.Toast.LENGTH_LONG
            ).show()
        }
    }

    Scaffold(
        modifier = Modifier.fillMaxSize()
    ) { innerPadding ->
        SecondPageContent(innerPadding, uiState, courseTitle)
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewSecondPageLoading() {
    MuseTheme {
        Scaffold { innerPadding ->
            SecondPageContent(
                innerPadding = innerPadding,
                uiState = ContentUiState(isLoading = true)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewSecondPageLoaded() {
    val mockCourses = listOf(
        CourseEntity(id = 1, title = "Математика"),
        CourseEntity(id = 2, title = "Физика")
    )
    val mockChapters = listOf(
        ChapterEntity(id = 1, courseId = 1, title = "Алгебра", position = 1),
        ChapterEntity(id = 2, courseId = 1, title = "Геометрия", position = 2)
    )
    val mockLessons = listOf(
        LessonEntity(
            id = 1, chapterId = 1, courseId = 1,
            chapterTitle = "Алгебра", chapterPosition = 1,
            title = "Уравнения", content = "...", position = 1
        ),
        LessonEntity(
            id = 2, chapterId = 1, courseId = 1,
            chapterTitle = "Алгебра", chapterPosition = 1,
            title = "Неравенства", content = "...", position = 2
        )
    )

    MuseTheme {
        Scaffold { innerPadding ->
            SecondPageContent(
                innerPadding = innerPadding,
                uiState = ContentUiState(
                    courses = mockCourses,
                    chapters = mockChapters,
                    lessons = mockLessons,
                    isLoading = false
                )
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewSecondPageError() {
    MuseTheme {
        Scaffold { innerPadding ->
            SecondPageContent(
                innerPadding = innerPadding,
                uiState = ContentUiState(
                    isLoading = false,
                    error = "401 Unauthorized"
                )
            )
        }
    }
}