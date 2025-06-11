package com.example.effectivemobile_test.domain

import com.example.effectivemobile_test.data.Course

interface CourseRepository {
    suspend fun getCourses(): List<Course>
    suspend fun toggleFavorite(courseId: Int): List<Course>
}