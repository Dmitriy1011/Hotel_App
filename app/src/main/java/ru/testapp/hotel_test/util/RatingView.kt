package ru.testapp.hotel_test.util

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.cardview.widget.CardView
import androidx.core.content.withStyledAttributes
import ru.testapp.hotel_test.R
import ru.testapp.hotel_test.databinding.ViewRatingBinding

class RatingView(
    context: Context,
    attrs: AttributeSet
) : CardView(context, attrs) {
    private var binding: ViewRatingBinding? = null

    var score: Int = 0
        set(value) {
            field = value
            updateRating()
        }

    private fun updateRating() {
        binding?.apply {
            rating.text = score.toString()
            when (score) {
                1 -> ratingLabel.text = "Ужасно"
                2 -> ratingLabel.text = "Плохо"
                3 -> ratingLabel.text = "Удовлетворительно"
                4 -> ratingLabel.text = "Хорошо"
                5 -> ratingLabel.text = "Превосходно"
            }
        }
    }

    init {
        context.withStyledAttributes(attrs, R.styleable.RatingView) {
            binding = ViewRatingBinding.inflate(
                LayoutInflater.from(context),
                this@RatingView,
                false
            )
            score = getInt(R.styleable.RatingView_score, 0)
            addView(binding?.root)
        }
    }
}