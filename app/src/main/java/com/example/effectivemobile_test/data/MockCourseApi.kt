package com.example.effectivemobile_test.data

import android.content.Context
import com.example.effectivemobile_test.R
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class MockCourseApi(private val context: Context) : CourseApi {
    override suspend fun getCourses(): List<Course> {
        return try {
            val inputStream = context.resources.openRawResource(R.raw.courses)
            val jsonString = inputStream.bufferedReader().use { it.readText() }

            val responseType = object : TypeToken<CoursesResponse>() {}.type
            val response = Gson().fromJson<CoursesResponse>(jsonString, responseType)
            response?.courses ?: emptyList()
        } catch (e: Exception) {
            e.printStackTrace()
            listOf(
                Course(
                    id = 0,
                    title = "Тестовый курс",
                    text = "Данные не загрузились",
                    price = "0",
                    rate = "0",
                    startDate = "",
                    hasLike = false,
                    publishDate = ""
                )
            )
        }
    }
}