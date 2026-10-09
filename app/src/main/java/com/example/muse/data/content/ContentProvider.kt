package com.example.muse.data.content

import android.content.Context
import androidx.room.Room
import com.example.muse.data.DemoContent
import com.example.muse.data.SubjectContent
import com.example.muse.data.Subtopic
import com.example.muse.data.Topic
import com.example.muse.data.local.AppDatabase
import com.example.muse.data.local.ChapterEntity
import com.example.muse.data.local.CourseEntity
import com.example.muse.data.local.LessonEntity
import com.example.muse.data.network.RetrofitClient
import com.example.muse.data.repository.ContentRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

/**
 * Единый источник контента для всех экранов.
 *
 * При запуске отдаёт [DemoContent] (мгновенный первый кадр и офлайн-запас),
 * после [refresh] — данные из Room, которые пришли с бэкенда.
 * Экраны подписываются на [subjects], и UI обновляется сам.
 */
object ContentProvider {

    @Volatile
    private var database: AppDatabase? = null

    @Volatile
    private var repository: ContentRepository? = null

    private val _subjects = MutableStateFlow(DemoContent.subjects)
    val subjects: StateFlow<List<SubjectContent>> = _subjects

    fun database(context: Context): AppDatabase =
        database ?: synchronized(this) {
            database ?: Room.databaseBuilder(
                context.applicationContext,
                AppDatabase::class.java,
                "muse.db"
            ).build().also { database = it }
        }

    fun repository(context: Context): ContentRepository =
        repository ?: synchronized(this) {
            repository ?: ContentRepository(
                api = RetrofitClient.api,
                database = database(context)
            ).also { repository = it }
        }

    fun subjectById(id: String): SubjectContent? =
        _subjects.value.firstOrNull { it.id == id }

    /**
     * Подтянуть контент с бэкенда в Room и пересобрать модель экранов.
     * При отсутствии сети/пустой базе остаётся демо-контент.
     */
    suspend fun refresh(context: Context) {
        val repo = repository(context)
        runCatching { repo.synchronize() }

        val courses = repo.getCachedCourses()
        if (courses.isEmpty()) return

        _subjects.value = buildSubjects(
            courses = courses,
            chapters = repo.getCachedChapters(),
            lessons = repo.getCachedLessons()
        )
    }

    private fun buildSubjects(
        courses: List<CourseEntity>,
        chapters: List<ChapterEntity>,
        lessons: List<LessonEntity>
    ): List<SubjectContent> = courses.sortedBy { it.id }.map { course ->
        val courseChapters = chapters
            .filter { it.courseId == course.id }
            .sortedBy { it.position }

        val topics = courseChapters.map { chapter ->
            val subtopics = lessons
                .filter { it.chapterId == chapter.id }
                .sortedBy { it.position }
                .map { Subtopic(it.title) }
            Topic(
                title = chapter.title,
                // Поля видео и дедлайнов появятся, когда подключим YouTube и задания с бэкенда
                videoDuration = "Скоро",
                subtopics = subtopics,
                homeworkTasks = subtopics.size,
                homeworkDeadline = "—"
            )
        }

        SubjectContent(
            id = slugFor(course),
            name = course.title,
            cardSubtitle = "${topics.size} тем",
            pageSubtitle = "${topics.size} тем · CSCA подготовка",
            topics = topics,
            progressDone = 0
        )
    }

    /**
     * Стабильный строковый id предмета, чтобы совпадать с ключами
     * прогресса и стилями (styleFor). TODO: перенести slug в данные бэкенда.
     */
    private fun slugFor(course: CourseEntity): String = when (course.title.lowercase()) {
        "математика" -> "math"
        "физика" -> "physics"
        "химия" -> "chemistry"
        "китайский" -> "chinese"
        else -> "course_${course.id}"
    }
}
