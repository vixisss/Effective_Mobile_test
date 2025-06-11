package com.example.effectivemobile_test.ui.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.example.effectivemobile_test.R
import com.example.effectivemobile_test.data.Course

class FavoriteCoursesAdapter(
    private var courses: List<Course>,
    private val onFavoriteClick: (Course) -> Unit
) : RecyclerView.Adapter<CoursesViewHolder>() {

    private val favoriteCourses: List<Course>
        get() = courses.filter { it.hasLike }

    @SuppressLint("NotifyDataSetChanged")
    fun updateCourses(newCourses: List<Course>) {
        this.courses = newCourses
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CoursesViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_course, parent, false)
        return CoursesViewHolder(view)
    }

    override fun onBindViewHolder(holder: CoursesViewHolder, position: Int) {
        val course = favoriteCourses[position]
        holder.bind(course)

        holder.itemView.findViewById<ImageView>(R.id.favoriteItem).setOnClickListener {
            onFavoriteClick(course)
        }
    }

    override fun getItemCount(): Int = favoriteCourses.size
}