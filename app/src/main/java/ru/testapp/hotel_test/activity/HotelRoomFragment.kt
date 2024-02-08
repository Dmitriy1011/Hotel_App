package ru.testapp.hotel_test.activity

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import ru.testapp.hotel_test.R
import ru.testapp.hotel_test.activity.HotelFragment.Companion.hotelNameArg
import ru.testapp.hotel_test.adapter.HotelRoomsAdapter
import ru.testapp.hotel_test.adapter.OnIteractionListenerRooms
import ru.testapp.hotel_test.databinding.FragmentHotelRoomBinding
import ru.testapp.hotel_test.dto.Room
import ru.testapp.hotel_test.viemodel.HotelRoomsViewModel

@AndroidEntryPoint
class HotelRoomFragment : Fragment(R.layout.fragment_hotel_room) {

    @OptIn(ExperimentalCoroutinesApi::class)
    private val viewModel: HotelRoomsViewModel by viewModels()

    @OptIn(ExperimentalCoroutinesApi::class)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val binding = FragmentHotelRoomBinding.bind(view)
        val adapterImages = HotelRoomsAdapter.RoomViewHolder.adapter

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.roomsData.collectLatest {
                    it.forEach { room ->
                        adapterImages.submitList(room.imageUrls)
                    }
                }
            }
        }

        val hotelName = arguments?.hotelNameArg

        val toolbar = Toolbar(requireContext())
        toolbar.title = hotelName

        val adapter = HotelRoomsAdapter(object : OnIteractionListenerRooms {
            override fun onClickedGoToReservation(room: Room) {
                findNavController().navigate(
                    R.id.action_hotelRoomFragment_to_reservationFragment
                )
            }
        })


        binding.roomsList.adapter = adapter

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.roomsData.collect { rooms ->
                    adapter.submitList(rooms)
                }
            }
        }

        viewModel.dataState.observe(viewLifecycleOwner) {
            if (it.error) {
                binding.errorText.visibility = View.VISIBLE
                Toast.makeText(
                    requireContext(),
                    R.string.rooms_loading_error,
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }
}