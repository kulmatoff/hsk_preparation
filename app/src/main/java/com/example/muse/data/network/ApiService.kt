package com.example.muse.data.network

import retrofit2.http.GET

interface ApiService {

    @GET("content/version")
    suspend fun getContentVersion(): ContentVersionResponse

    @GET("courses")
    suspend fun getCourses(): CoursesResponse

    @GET("chapters")
    suspend fun getChapters(): ChaptersResponse

    @GET("lessons")
    suspend fun getLessons(): LessonsResponse
}