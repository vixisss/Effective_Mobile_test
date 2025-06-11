package com.example.effectivemobile_test.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.effectivemobile_test.data.MockCourseApi
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

@Database(
    version = 3,
    entities = [CoursesEntity::class],
    exportSchema = true,

)
abstract class AppDataBase : RoomDatabase() {
    abstract fun coursesDao(): CoursesDao

    companion object {
        fun create(context: Context, converter: CoursesDbConverter): AppDataBase {
            val db = Room.databaseBuilder(
                context,
                AppDataBase::class.java,
                "courses.db"
            ).build()
            CoroutineScope(Dispatchers.IO).launch {
                val dao = db.coursesDao()
                if (dao.getAllFavorites().first().isEmpty()) {
                    val mockApi = MockCourseApi(context)
                    val courses = mockApi.getCourses()
                    courses.filter { it.hasLike }.forEach {
                        dao.addToFavorites(converter.map(it))
                    }
                }
            }
            return db
        }
    }
}