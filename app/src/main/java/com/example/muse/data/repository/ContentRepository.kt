package com.example.muse.data.repository

import androidx.room.withTransaction
import com.example.muse.data.local.AppDatabase
import com.example.muse.data.local.ChapterEntity
import com.example.muse.data.local.ContentVersionEntity
import com.example.muse.data.local.CourseEntity
import com.example.muse.data.local.LessonEntity
import com.example.muse.data.network.ApiService

class ContentRepository(
    private val api: ApiService,
    private val database: AppDatabase
) {

    private val dao = database.contentDao()

    suspend fun getCachedCourses(): List<CourseEntity> {
        return dao.getCourses()
    }

    suspend fun getCachedChapters(): List<ChapterEntity> {
        return dao.getChapters()
    }

    suspend fun getCachedLessons(): List<LessonEntity> {
        return dao.getLessons()
    }

    suspend fun synchronize(): Boolean {
        val remoteVersion = api.getContentVersion().version
        val localVersion = dao.getContentVersion()?.version

        if (localVersion == remoteVersion) {
            return false
        }

        val courses = api.getCourses().courses
        val chapters = api.getChapters().chapters
        val lessons = api.getLessons().lessons

        database.withTransaction {
            dao.deleteLessons()
            dao.deleteChapters()
            dao.deleteCourses()

            dao.insertCourses(
                courses.map {
                    CourseEntity(
                        id = it.id,
                        title = it.title
                    )
                }
            )

            dao.insertChapters(
                chapters.map {
                    ChapterEntity(
                        id = it.id,
                        courseId = it.courseId,
                        title = it.title,
                        position = it.position
                    )
                }
            )

            dao.insertLessons(
                lessons.map {
                    LessonEntity(
                        id = it.id,
                        chapterId = it.chapterId,
                        courseId = it.courseId,
                        chapterTitle = it.chapterTitle,
                        chapterPosition = it.chapterPosition,
                        title = it.title,
                        content = it.content,
                        position = it.position
                    )
                }
            )

            dao.insertContentVersion(
                ContentVersionEntity(
                    version = remoteVersion
                )
            )
        }

        return true
    }
}