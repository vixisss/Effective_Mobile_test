package com.example.effectivemobile_test.ui.adapter

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.example.effectivemobile_test.R
import com.example.effectivemobile_test.data.Course
import com.example.effectivemobile_test.utils.DpToPx

class CoursesViewHolder (itemView: View) : RecyclerView.ViewHolder(itemView) {
    private val title: TextView = itemView.findViewById(R.id.titleItem)
    private val description: TextView = itemView.findViewById(R.id.textItem)
    private val price: TextView = itemView.findViewById(R.id.priceItem)
    private val rate: TextView = itemView.findViewById(R.id.rateItem)
    private val startDate: TextView = itemView.findViewById(R.id.startDateItem)
    private val favoriteIcon: ImageView = itemView.findViewById(R.id.favoriteItem)
    private val imageLogo: ImageView = itemView.findViewById(R.id.imageItem)

    fun bind(course: Course) {
        title.text = course.title
        description.text = course.text
        price.text = "${course.price} ₽"
        rate.text = course.rate
        startDate.text = formatDate(course.startDate)

        favoriteIcon.setImageResource(
            if (course.hasLike) R.drawable.bookmark_green
            else R.drawable.bookmark
        )

        Glide.with(itemView)
            .load(R.drawable.logo1)
            .transform(
                CenterCrop(),
                RoundedCorners(DpToPx.dpToPx(16F, itemView.context))
            )
            .placeholder(R.drawable.placeholder)
            .into(imageLogo)

    }


    private fun formatDate(dateString: String): String {
        return try {
            val inputFormat = java.text.SimpleDateFormat("yyyy-MM-dd", java.util.Locale.getDefault())
            val date = inputFormat.parse(dateString) ?: return dateString

            val outputFormat = java.text.SimpleDateFormat("d MMMM yyyy", java.util.Locale("ru"))
            outputFormat.format(date)
        } catch (e: Exception) {
            dateString
        }
    }
}