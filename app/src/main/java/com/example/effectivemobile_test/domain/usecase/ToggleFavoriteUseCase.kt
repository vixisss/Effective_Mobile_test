package com.example.effectivemobile_test.domain.usecase

import com.example.effectivemobile_test.data.Course
import com.example.effectivemobile_test.domain.CourseRepository

class ToggleFavoriteUseCase(private val repository: CourseRepository) {
    suspend operator fun invoke(courseId: Int): List<Course> {
        return repository.toggleFavorite(courseId)
    }
}