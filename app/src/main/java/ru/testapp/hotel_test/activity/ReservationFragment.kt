package ru.testapp.hotel_test.activity

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import ru.testapp.hotel_test.R
import ru.testapp.hotel_test.adapter.TouristsAdapter
import ru.testapp.hotel_test.databinding.FragmentReservationBinding
import ru.testapp.hotel_test.viemodel.ReservationViewModel
import ru.testapp.hotel_test.viemodel.TouristsViewModel

@AndroidEntryPoint
class ReservationFragment : Fragment(R.layout.fragment_reservation) {

    private val touristViewModel: TouristsViewModel by viewModels()

    @OptIn(ExperimentalCoroutinesApi::class)
    private val reservationViewModel: ReservationViewModel by viewModels()

    @OptIn(ExperimentalCoroutinesApi::class)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val binding = FragmentReservationBinding.bind(view)

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                reservationViewModel.dataReservation.collect {
                    binding.apply {
                        departureFromDescr.text = it?.departure
                        arrivalToDescr.text = it?.arrivalCountry
                        dateStart.text = it?.tourDateStart
                        dateFinish.text = it?.tourDateStop
                        nightsAmountDescr.text = it?.numberOfNights.toString()
                        priceTourText.text = it?.tourPrice.toString()
                        fuelChargeText.text = it?.fuelCharge.toString()
                        serviceChargeText.text = it?.serviceCharge.toString()
                        reservationRatingView.score = it?.ratingOfHotel ?: 0
                    }
                }
            }
        }

        val touristsAdapter = TouristsAdapter()
        binding.touristsList.adapter = touristsAdapter

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                touristViewModel.touristsData.collect {
                    touristsAdapter.submitList(it)
                }
            }
        }

        binding.addTouristButtonView.setOnClickListener {
            touristViewModel.addTourist()
        }

        binding.toPaymentButton.setOnClickListener {
            findNavController().navigate(R.id.action_reservationFragment_to_paidFragment)
        }

        reservationViewModel.dataState.observe(viewLifecycleOwner) {
            if (it.error) {
                binding.errorText.visibility = View.VISIBLE
                Toast.makeText(
                    requireContext(),
                    R.string.reservation_loading_error,
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }
}