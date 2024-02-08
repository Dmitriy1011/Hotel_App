package ru.testapp.hotel_test.adapter

import android.telephony.PhoneNumberFormattingTextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import ru.testapp.hotel_test.R
import ru.testapp.hotel_test.databinding.ViewTouristBinding
import ru.testapp.hotel_test.dto.Tourist


class TouristsAdapter() :
    ListAdapter<Tourist, TouristsAdapter.TouristViewHolder>(DiffTouristCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TouristViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return TouristViewHolder(ViewTouristBinding.inflate(layoutInflater, parent, false))
    }

    override fun onBindViewHolder(holder: TouristViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class TouristViewHolder(
        private val binding: ViewTouristBinding
    ) : ViewHolder(binding.root) {
        fun bind(tourist: Tourist) {
            binding.apply {
                touristCardTitle.text = tourist.title
                nameFieldEditTExt.addTextChangedListener(PhoneNumberFormattingTextWatcher())

                if (tourist.isCardVisible) {
                    cardWithInputs.visibility = View.VISIBLE
                    toggleTouristCardIconImage.setImageResource(R.drawable.baseline_keyboard_arrow_up_24)
                } else {
                    cardWithInputs.visibility = View.GONE
                    toggleTouristCardIconImage.setImageResource(R.drawable.baseline_keyboard_arrow_down_24)
                }

                toggleTouristCardIcon.setOnClickListener {
                    if (tourist.isCardVisible) {
                        toggleTouristCardIconImage.setImageResource(R.drawable.baseline_keyboard_arrow_down_24)
                        cardWithInputs.visibility = View.GONE
                        tourist.isCardVisible = !tourist.isCardVisible
                    } else {
                        toggleTouristCardIconImage.setImageResource(R.drawable.baseline_keyboard_arrow_up_24)
                        cardWithInputs.visibility = View.VISIBLE
                        tourist.isCardVisible = !tourist.isCardVisible
                    }
                }
            }
        }
    }

    class DiffTouristCallback : DiffUtil.ItemCallback<Tourist>() {
        override fun areItemsTheSame(oldItem: Tourist, newItem: Tourist): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Tourist, newItem: Tourist): Boolean {
            return oldItem == newItem
        }
    }
}