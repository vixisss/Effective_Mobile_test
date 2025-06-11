package com.example.effectivemobile_test.data.repository

import com.example.effectivemobile_test.data.Course
import com.example.effectivemobile_test.data.CourseApi
import com.example.effectivemobile_test.db.AppDataBase
import com.example.effectivemobile_test.db.CoursesDbConverter
import com.example.effectivemobile_test.domain.CourseRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.withContext

class CourseRepositoryImpl(
    private val api: CourseApi,
    private val db: AppDataBase,
    private val converter: CoursesDbConverter
) : CourseRepository {
    private var cachedCourses: List<Course> = emptyList()
    private val dao = db.coursesDao()

    override suspend fun getCourses(): List<Course> {
        return withContext(Dispatchers.IO) {
            if (cachedCourses.isEmpty()) {
                cachedCourses = api.getCourses()
                cachedCourses = syncWithLocalFavorites(cachedCourses)
            }
            cachedCourses
        }
    }

    override suspend fun toggleFavorite(courseId: Int): List<Course> {
        return withContext(Dispatchers.IO) {
            val course = cachedCourses.firstOrNull { it.id == courseId } ?: return@withContext cachedCourses
            val isFavorite = dao.isFavorite(courseId)

            if (isFavorite) {
                dao.removeFromFavorites(converter.map(course))
            } else {
                dao.addToFavorites(converter.map(course))
            }

            cachedCourses = cachedCourses.map {
                if (it.id == courseId) it.copy(hasLike = !isFavorite) else it
            }
            cachedCourses
        }
    }

    private suspend fun syncWithLocalFavorites(remoteCourses: List<Course>): List<Course> {
        val favoriteIds = dao.getAllFavorites().first().map { it.id }
        return remoteCourses.map { course ->
            if (favoriteIds.contains(course.id)) {
                course.copy(hasLike = true)
            } else {
                course
            }
        }
    }
}