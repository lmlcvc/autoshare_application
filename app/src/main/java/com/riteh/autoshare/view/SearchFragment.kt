package com.riteh.autoshare.view

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.core.util.Pair as APair
import com.androidisland.vita.VitaOwner
import com.androidisland.vita.vita
import com.google.android.material.datepicker.MaterialDatePicker
import com.riteh.autoshare.R
import com.riteh.autoshare.viewmodel.SearchViewModel
import kotlinx.android.synthetic.main.search_fragment.*
import kotlinx.android.synthetic.main.search_fragment.view.*


// TODO: date range picker initialization values and saving to viewmodel
// TODO: date range picker design
class SearchFragment : Fragment() {

    companion object {
        fun newInstance() = SearchFragment()
    }

    private lateinit var viewModel: SearchViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view: View = inflater.inflate(R.layout.search_fragment, container, false)

        setEventListeners(view)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = vita.with(VitaOwner.Multiple(this)).getViewModel()

        viewModel.location.observe(viewLifecycleOwner) {
            tv_location.text = it
        }
    }


    private fun setEventListeners(view: View) {
        view.tv_location.setOnClickListener {
            val intent = Intent(requireActivity(), LocationInputActivity::class.java)
            startActivity(intent)
        }

        view.tv_calendar.setOnClickListener {
            val range = APair(
                MaterialDatePicker.thisMonthInUtcMilliseconds(),
                MaterialDatePicker.todayInUtcMilliseconds()
            )
            val dateRangePicker =
                MaterialDatePicker.Builder.dateRangePicker()
                    .setTitleText("Select dates")
                    .setSelection(range)
                    .build()

            dateRangePicker.show(parentFragmentManager, "a")
        }


    }

}