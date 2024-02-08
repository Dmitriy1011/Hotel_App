package ru.testapp.hotel_test.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.google.android.material.chip.Chip
import ru.testapp.hotel_test.R
import ru.testapp.hotel_test.databinding.ItemHotelRoomBinding
import ru.testapp.hotel_test.dto.Room
import kotlin.random.Random

interface OnIteractionListenerRooms {
    fun onClickedGoToReservation(room: Room) {}
}

class HotelRoomsAdapter(
    private val listener: OnIteractionListenerRooms
) : ListAdapter<Room, HotelRoomsAdapter.RoomViewHolder>(RoomDifCallback()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RoomViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return RoomViewHolder(
            ItemHotelRoomBinding.inflate(layoutInflater, parent, false),
            listener = listener,
            parent.context
        )
    }

    override fun onBindViewHolder(holder: RoomViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class RoomViewHolder(
        private val binding: ItemHotelRoomBinding,
        private val listener: OnIteractionListenerRooms,
        private val context: Context
    ) : ViewHolder(
        binding.root
    ) {
        companion object {
            var adapter = ViewPagerImageAdapter()
        }

        fun bind(hotelRoom: Room) {
            binding.apply {
                roomName.text = hotelRoom.name
                totalPrice.text = hotelRoom.price.toString()
                totalPriceText.text = hotelRoom.pricePer

                val viewPager2 = binding.viewPagerHotelRoom

                chooseNumberButton.setOnClickListener {
                    listener.onClickedGoToReservation(hotelRoom)
                }

                viewPagerHotelRoom.adapter = adapter

                circleIndicator.setViewPager(viewPager2)

                val chipGroup = binding.chipsGroupRoom
                hotelRoom.peculiarities.forEach {
                    val chip = LayoutInflater.from(context)
                        .inflate(R.layout.card_chip, chipGroup, false) as Chip
                    chip.id = Random.nextInt()
                    chip.text = it
                    chipGroup.addView(chip)
                }
            }
        }
    }

    class RoomDifCallback : DiffUtil.ItemCallback<Room>() {
        override fun areItemsTheSame(oldItem: Room, newItem: Room): Boolean {
            if (oldItem::class != newItem::class) {
                return false
            }

            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Room, newItem: Room): Boolean {
            return oldItem == newItem
        }

    }
}
