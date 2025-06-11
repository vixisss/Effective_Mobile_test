package com.example.effectivemobile_test.domain.usecase

import com.example.effectivemobile_test.data.Course
import com.example.effectivemobile_test.domain.CourseRepository

class GetCoursesUseCase(private val repository: CourseRepository) {
    suspend operator fun invoke(): List<Course> {
        return repository.getCourses()
    }
}