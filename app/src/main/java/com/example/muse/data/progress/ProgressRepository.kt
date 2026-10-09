package com.example.muse.data.progress

import android.content.Context
import androidx.room.Room
import com.example.muse.data.SubjectContent
import kotlinx.coroutines.flow.Flow

/**
 * Репозиторий прогресса пользователя.
 * Источник данных — локальная Room-БД; контент (названия тем, заданий)
 * приходит из ContentProvider (бэкенд, с запасным DemoContent).
 */
class ProgressRepository(private val dao: ProgressDao) {

    fun observeAllProgress(): Flow<List<TopicProgressEntity>> = dao.observeAllProgress()

    fun observeHomework(): Flow<List<HomeworkTaskEntity>> = dao.observeHomework()

    fun observePendingHomeworkCount(): Flow<Int> = dao.observePendingHomeworkCount()

    suspend fun setTopicCompleted(subjectId: String, topicIndex: Int, completed: Boolean) {
        if (completed) {
            dao.upsertProgress(
                TopicProgressEntity(
                    subjectId = subjectId,
                    topicIndex = topicIndex,
                    completedAt = System.currentTimeMillis()
                )
            )
        } else {
            dao.clearProgress(subjectId, topicIndex)
        }
    }

    suspend fun setHomeworkDone(id: String, done: Boolean) = dao.setHomeworkDone(id, done)

    /**
     * Заполняет домашние задания из актуального контента.
     * Если набор предметов изменился (обновление с бэкенда) — пересоздаёт список.
     * Внимание: пересоздание сбрасывает отметки «выполнено».
     */
    suspend fun seedHomework(subjects: List<SubjectContent>) {
        val wantedIds = subjects.map { it.id }.toSet()
        if (dao.homeworkCount() > 0 && dao.homeworkSubjectIds().toSet() == wantedIds) return

        dao.deleteAllHomework()

        val tasks = mutableListOf<HomeworkTaskEntity>()
        for (subject in subjects) {
            for ((topicIndex, topic) in subject.topics.withIndex()) {
                for (n in 1..topic.homeworkTasks) {
                    val title = if (n <= topic.subtopics.size) {
                        "Практика: ${topic.subtopics[n - 1].title}"
                    } else {
                        "Дополнительное задание $n"
                    }
                    tasks += HomeworkTaskEntity(
                        id = "${subject.id}_${topicIndex}_$n",
                        subjectId = subject.id,
                        subjectName = subject.name,
                        topicIndex = topicIndex,
                        topicTitle = topic.title,
                        title = title,
                        done = false,
                        deadline = topic.homeworkDeadline
                    )
                }
            }
        }
        dao.insertHomework(tasks)
    }
}

/** Единая точка доступа: одна БД и один репозиторий на приложение. */
object ProgressProvider {

    @Volatile
    private var database: ProgressDatabase? = null

    @Volatile
    private var repository: ProgressRepository? = null

    fun database(context: Context): ProgressDatabase =
        database ?: synchronized(this) {
            database ?: Room.databaseBuilder(
                context.applicationContext,
                ProgressDatabase::class.java,
                "muse_progress.db"
            ).build().also { database = it }
        }

    fun repository(context: Context): ProgressRepository =
        repository ?: synchronized(this) {
            repository ?: ProgressRepository(database(context).progressDao())
                .also { repository = it }
        }
}
