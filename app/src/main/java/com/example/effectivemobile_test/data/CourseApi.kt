package com.example.effectivemobile_test.data

import retrofit2.http.GET

interface CourseApi {
    @GET("courses")
    suspend fun getCourses() : List<Course>
}