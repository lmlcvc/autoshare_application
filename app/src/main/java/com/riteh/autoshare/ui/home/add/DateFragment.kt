package com.riteh.autoshare.ui.home.add

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.NavHostFragment
import com.google.android.material.datepicker.CalendarConstraints
import com.google.android.material.datepicker.DateValidatorPointForward
import com.google.android.material.datepicker.MaterialDatePicker
import com.riteh.autoshare.R
import com.riteh.autoshare.databinding.FragmentDateBinding
import kotlinx.android.synthetic.main.fragment_date.*
import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking
import retrofit2.HttpException

class DateFragment : Fragment() {

    private var binding: FragmentDateBinding? = null
    private val sharedViewModel: AddViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val fragmentBinding = FragmentDateBinding.inflate(inflater, container, false)
        binding = fragmentBinding

        return fragmentBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding?.dateFragment = this

        sharedViewModel.startDateString.observe(viewLifecycleOwner) {
            tv_start.text = it
        }

        sharedViewModel.endDateString.observe(viewLifecycleOwner) {
            tv_end.text = it
        }


        setOnClickListeners()
    }

    private fun setOnClickListeners() {
        iv_close.setOnClickListener {
            requireActivity().finish()
        }

        rl_start.setOnClickListener {
            constructStartDatePicker()
        }

        rl_end.setOnClickListener {
            constructEndDatePicker()
        }

        btn_prev.setOnClickListener {
            NavHostFragment.findNavController(this)
                .navigate(R.id.action_dateFragment_to_locationFragment)
        }

        // TODO: API calls should function as one
            // if one receives success and the other one fails,
            // the availability should not exist
        btn_next.setOnClickListener {
            try {
                runBlocking {
                    async {
                        sharedViewModel.addVehicleRentInfo()
                        sharedViewModel.createAvailability()
                    }
                }

                NavHostFragment.findNavController(this)
                    .navigate(R.id.action_dateFragment_to_addingCompletedFragment)
            } catch (e: HttpException) {
                NavHostFragment.findNavController(this)
                    .navigate(R.id.action_dateFragment_to_addingUnsuccessfulFragment)
            }
        }
    }


    private fun constructStartDatePicker() {
        val constraints = CalendarConstraints.Builder()
            .setValidator(DateValidatorPointForward.now()).build()

        val selection: Long =
            if (sharedViewModel.startDateString.value.equals(getString(R.string.start_date))) {
                MaterialDatePicker.todayInUtcMilliseconds()
            } else {
                sharedViewModel.startDate.value!!.time
            }

        val dateRangePicker =
            MaterialDatePicker.Builder.datePicker()
                .setTitleText("Start date")
                .setSelection(selection)
                .setCalendarConstraints(constraints)
                .setTheme(R.style.ThemeOverlay_App_DatePicker)
                .build()

        dateRangePicker.addOnPositiveButtonClickListener {
            dateRangePicker.selection?.let { date -> sharedViewModel.setStartDate(date) }
        }

        dateRangePicker.show(parentFragmentManager, "a")
    }


    private fun constructEndDatePicker() {
        val constraints: CalendarConstraints =
            if (sharedViewModel.endDateString.value.equals(getString(R.string.start_date))) {
                CalendarConstraints.Builder()
                    .setValidator(DateValidatorPointForward.now()).build()
            } else {
                CalendarConstraints.Builder()
                    .setValidator(DateValidatorPointForward.from(sharedViewModel.startDate.value!!.time))
                    .build()
            }

        val selection: Long =
            if (sharedViewModel.endDateString.value.equals(getString(R.string.end_date))) {
                MaterialDatePicker.todayInUtcMilliseconds()
            } else {
                sharedViewModel.endDate.value!!.time
            }

        val dateRangePicker =
            MaterialDatePicker.Builder.datePicker()
                .setTitleText("End date")
                .setSelection(selection)
                .setCalendarConstraints(constraints)
                .setTheme(R.style.ThemeOverlay_App_DatePicker)
                .build()

        dateRangePicker.addOnPositiveButtonClickListener {
            dateRangePicker.selection?.let { date -> sharedViewModel.setEndDate(date) }
        }

        dateRangePicker.show(parentFragmentManager, "a")
    }
}