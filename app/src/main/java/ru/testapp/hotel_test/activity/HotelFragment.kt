package ru.testapp.hotel_test.activity

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.google.android.material.chip.Chip
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import ru.testapp.hotel_test.R
import ru.testapp.hotel_test.adapter.ViewPagerImageAdapter
import ru.testapp.hotel_test.databinding.FragmentHotelBinding
import ru.testapp.hotel_test.util.HotelNameArg
import ru.testapp.hotel_test.viemodel.HotelsViewModel
import kotlin.random.Random

@AndroidEntryPoint
class HotelFragment : Fragment(R.layout.fragment_hotel) {

    @OptIn(ExperimentalCoroutinesApi::class)
    private val viewModel: HotelsViewModel by viewModels()

    @OptIn(ExperimentalCoroutinesApi::class)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {


        val binding = FragmentHotelBinding.bind(view)
        val viewPager2 = binding.viewPager2
        val adapter = ViewPagerImageAdapter()
        viewPager2.adapter = adapter

        val indicator = binding.circleIndicatorHotel

        val chipGroup = binding.chipGroupHotel

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.hotelData.collectLatest {
                    adapter.submitList(it?.imageUrls)
                    indicator.setViewPager(viewPager2)
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.hotelData.collectLatest {
                    it?.aboutTheHotel?.peculiarities?.forEach { info ->
                        val chip = LayoutInflater.from(requireContext())
                            .inflate(R.layout.card_chip, chipGroup, false) as Chip
                        chip.id = Random.nextInt()
                        chip.text = info
                        chipGroup.addView(chip)
                    }
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.hotelData.collectLatest { hotel ->
                    binding.apply {
                        hotelName.text = hotel?.name
                        hotelAddress.text = hotel?.address
                        priceText.text = hotel?.minimalPrice.toString()
                        priceForIt.text = hotel?.priceForIt
                        hotelDescription.text = hotel?.aboutTheHotel?.description
                    }
                }
            }
        }

        val hotelName = viewModel.hotelData.value?.name

        binding.toRoomFragmentButton.setOnClickListener {
            findNavController().navigate(
                R.id.action_hotelFragment_to_hotelRoomFragment,
                Bundle().apply {
                    hotelNameArg = hotelName
                })
        }

        viewModel.hotelDataState.observe(viewLifecycleOwner) {
            if (it.error) {
                binding.errorText.visibility = View.VISIBLE
                Toast.makeText(
                    requireContext(),
                    R.string.hotels_loading_error,
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }

    companion object {
        var Bundle.hotelNameArg: String? by HotelNameArg
    }
}