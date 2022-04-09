package ui.home.search

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.core.util.Pair as APair
import com.androidisland.vita.VitaOwner
import com.androidisland.vita.vita
import com.google.android.material.datepicker.CalendarConstraints
import com.google.android.material.datepicker.DateValidatorPointForward
import com.google.android.material.datepicker.MaterialDatePicker
import com.riteh.autoshare.R
import kotlinx.android.synthetic.main.search_fragment.*
import kotlinx.android.synthetic.main.search_fragment.view.*


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

        viewModel.dateRange.observe(viewLifecycleOwner) {
            tv_calendar.text = it
        }
    }


    private fun setEventListeners(view: View) {
        view.tv_location.setOnClickListener {
            val intent = Intent(requireActivity(), LocationInputActivity::class.java)
            startActivity(intent)
        }

        view.tv_calendar.setOnClickListener {
            val range = APair(
                viewModel.startDate.value?.time,
                viewModel.endDate.value?.time
            )

            val constraints = CalendarConstraints.Builder()
                .setValidator(DateValidatorPointForward.now()).build()

            val dateRangePicker =
                MaterialDatePicker.Builder.dateRangePicker()
                    .setTitleText("Select dates")
                    .setSelection(range)
                    .setCalendarConstraints(constraints)
                    .setTheme(R.style.ThemeOverlay_App_DatePicker)
                    .build()

            dateRangePicker.addOnPositiveButtonClickListener {
                dateRangePicker.selection?.let { it1 -> viewModel.setDates(it1) }
            }

            dateRangePicker.show(parentFragmentManager, "a")
        }


    }

}