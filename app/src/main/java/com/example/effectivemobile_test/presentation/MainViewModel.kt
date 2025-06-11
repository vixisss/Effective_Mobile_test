package com.example.effectivemobile_test.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.effectivemobile_test.data.Course
import com.example.effectivemobile_test.domain.usecase.GetCoursesUseCase
import com.example.effectivemobile_test.domain.usecase.ToggleFavoriteUseCase
import kotlinx.coroutines.launch

class MainViewModel(
    private val getCoursesUseCase: GetCoursesUseCase,
    private val toggleFavoriteUseCase: ToggleFavoriteUseCase
) : ViewModel() {

    private val _originalCourses = mutableListOf<Course>()
    private val _displayedCourses = MutableLiveData<List<Course>>()
    val courses: LiveData<List<Course>> = _displayedCourses

    private val _favoriteCourses = MutableLiveData<List<Course>>()
    val favoriteCourses: LiveData<List<Course>> = _favoriteCourses

    var isSorted = false
    private var sortDescending = false

    init {
        loadCourses()
    }

    fun loadCourses() {
        viewModelScope.launch {
            try {
                val newCourses = getCoursesUseCase()
                _originalCourses.clear()
                _originalCourses.addAll(newCourses)
                applyCurrentSort()
            } catch (e: Exception) {
                // Обработка ошибок
            }
        }
    }

    fun toggleFavorite(courseId: Int) {
        viewModelScope.launch {
            try {
                val updatedCourses = toggleFavoriteUseCase(courseId)
                updatedCourses.forEach { updatedCourse ->
                    val index = _originalCourses.indexOfFirst { it.id == updatedCourse.id }
                    if (index != -1) {
                        _originalCourses[index] = updatedCourse
                    }
                }
                applyCurrentSort()
            } catch (e: Exception) {
                // Обработка ошибок
            }
        }
    }

    fun toggleSorting() {
        isSorted = !isSorted
        applyCurrentSort()
    }

    fun setSorting(descending: Boolean) {
        sortDescending = descending
        isSorted = true
        applyCurrentSort()
    }

    private fun applyCurrentSort() {
        val resultList = when {
            !isSorted -> _originalCourses.toList()
            sortDescending -> _originalCourses.sortedByDescending { it.publishDate }
            else -> _originalCourses.sortedBy { it.publishDate }
        }

        val distinctList = resultList.distinctBy { it.id }

        _displayedCourses.value = distinctList
        _favoriteCourses.value = distinctList.filter { it.hasLike }
    }
}