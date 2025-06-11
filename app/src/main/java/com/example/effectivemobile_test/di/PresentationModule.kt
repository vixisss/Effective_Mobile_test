package com.example.effectivemobile_test.di

import com.example.effectivemobile_test.presentation.MainViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val presentationModule = module {
    viewModel {
        MainViewModel(get(), get())
    }
}