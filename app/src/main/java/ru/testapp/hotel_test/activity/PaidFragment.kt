package ru.testapp.hotel_test.activity

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import ru.testapp.hotel_test.R
import ru.testapp.hotel_test.databinding.FragmentPaidBinding

@AndroidEntryPoint
class PaidFragment : Fragment(R.layout.fragment_paid) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val binding = FragmentPaidBinding.bind(view)

        binding.toHotelFragmentButton.setOnClickListener {
            findNavController().navigate(R.id.action_paidFragment_to_hotelFragment)
        }
    }
}