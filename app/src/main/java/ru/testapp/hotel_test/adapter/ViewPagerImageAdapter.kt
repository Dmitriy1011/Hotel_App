package ru.testapp.hotel_test.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ru.testapp.hotel_test.databinding.ViewpagerImageItemBinding
import ru.testapp.hotel_test.handler.loadImage

class ViewPagerImageAdapter :
    ListAdapter<String, ViewPagerImageAdapter.Pager2ViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Pager2ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return Pager2ViewHolder(
            ViewpagerImageItemBinding.inflate(inflater, parent, false)
        )
    }

    override fun onBindViewHolder(holder: Pager2ViewHolder, position: Int) {
        holder.bind(currentList[position])
    }

    inner class Pager2ViewHolder(
        private val binding: ViewpagerImageItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(url: String) {
            binding.viewPagerImage.loadImage(url)
        }
    }

    class DiffCallback : DiffUtil.ItemCallback<String>() {
        override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: String, newItem: String): Boolean {
            return oldItem == newItem
        }

    }
}