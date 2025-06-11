package com.example.effectivemobile_test.db

import com.example.effectivemobile_test.data.Course

class CoursesDbConverter {
    fun map(course: Course) : CoursesEntity {
        return CoursesEntity(
            id = course.id,
            title = course.title,
            text = course.text,
            price = course.price,
            rate = course.rate,
            startDate = course.startDate,
            hasLike = course.hasLike,
            publishDate = course.publishDate
        )
    }

    fun map(courseEntity: CoursesEntity) : Course {
        return Course(
            courseEntity.id,
            courseEntity.title,
            courseEntity.text,
            courseEntity.price,
            courseEntity.rate,
            courseEntity.startDate,
            courseEntity.hasLike,
            courseEntity.publishDate
        )
    }
}
