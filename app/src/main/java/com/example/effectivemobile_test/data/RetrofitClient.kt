package com.example.effectivemobile_test.data

import android.content.Context

object RetrofitClient {
    fun createMockService(context: Context): CourseApi {
        return MockCourseApi(context)
    }
}