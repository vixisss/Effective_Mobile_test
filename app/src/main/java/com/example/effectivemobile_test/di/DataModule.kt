package com.example.effectivemobile_test.di

import android.content.Context
import androidx.room.Room
import com.example.effectivemobile_test.data.CourseApi
import com.example.effectivemobile_test.data.MockCourseApi
import com.example.effectivemobile_test.data.RetrofitClient
import com.example.effectivemobile_test.db.AppDataBase
import com.example.effectivemobile_test.db.CoursesDbConverter
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val dataModule = module {
    single<CourseApi> {
        MockCourseApi(androidContext())
    }

    factory { provideRetrofitClient(androidContext()) }

    single {
        Room.databaseBuilder(androidContext(), AppDataBase::class.java, "database.db")
            .build()
    }

    single { CoursesDbConverter() }

    single {
        val context = get<Context>()
        val converter = get<CoursesDbConverter>()
        AppDataBase.create(context, converter)
    }

}

fun provideRetrofitClient(context: Context): CourseApi {
    return RetrofitClient.createMockService(context)
}