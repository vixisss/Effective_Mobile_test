package com.example.effectivemobile_test.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface CoursesDao {
    @Insert
    suspend fun addToFavorites(course: CoursesEntity)

    @Delete
    suspend fun removeFromFavorites(course: CoursesEntity)

    @Query("SELECT * FROM courses")
    fun getAllFavorites(): Flow<List<CoursesEntity>>

    @Query("SELECT EXISTS(SELECT * FROM courses WHERE id = :courseId)")
    suspend fun isFavorite(courseId: Int): Boolean

}