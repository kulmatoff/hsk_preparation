package com.example.muse.data.network

data class ContentVersionResponse(
    val version: Int
)

data class CoursesResponse(
    val courses: List<CourseDto>
)

data class ChaptersResponse(
    val chapters: List<ChapterDto>
)

data class LessonsResponse(
    val lessons: List<LessonDto>
)

data class CourseDto(
    val id: Int,
    val title: String
)

data class ChapterDto(
    val id: Int,
    val courseId: Int,
    val title: String,
    val position: Int
)

data class LessonDto(
    val id: Int,
    val chapterId: Int,
    val courseId: Int,
    val chapterTitle: String,
    val chapterPosition: Int,
    val title: String,
    val content: String,
    val position: Int
)