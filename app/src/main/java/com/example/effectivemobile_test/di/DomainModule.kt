package com.example.effectivemobile_test.di

import com.example.effectivemobile_test.domain.CourseRepository
import com.example.effectivemobile_test.data.repository.CourseRepositoryImpl
import com.example.effectivemobile_test.domain.usecase.GetCoursesUseCase
import com.example.effectivemobile_test.domain.usecase.ToggleFavoriteUseCase
import org.koin.dsl.module

val domainModule = module {
    single<CourseRepository> {
        CourseRepositoryImpl(get(), get(), get())
    }

    factory {
        GetCoursesUseCase(get())
    }

    factory {
        ToggleFavoriteUseCase(get())
    }
}