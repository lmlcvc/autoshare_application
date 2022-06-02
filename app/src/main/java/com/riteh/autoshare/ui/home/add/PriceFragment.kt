package com.riteh.autoshare.ui.home.add

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.NavHostFragment.Companion.findNavController
import com.riteh.autoshare.R
import com.riteh.autoshare.databinding.FragmentPriceBinding
import kotlinx.android.synthetic.main.fragment_price.*
import kotlinx.android.synthetic.main.fragment_price.iv_close


class PriceFragment : Fragment() {

    private var binding: FragmentPriceBinding? = null
    private val sharedViewModel: AddViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val fragmentBinding = FragmentPriceBinding.inflate(inflater, container, false)
        binding = fragmentBinding

        return fragmentBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding?.priceFragment = this

        setInitialValues()
        setKeyChangeListeners()
        setOnClickListeners()
        btn_next.isClickable = false
    }


    private fun setInitialValues() {
        if(sharedViewModel.dailyPrice.value!! != -1.0) {
            et_daily_price.setText(sharedViewModel.dailyPrice.value!!.toString())
        }

        if(sharedViewModel.distanceLimit.value!! != -1.0) {
            et_kilometer.setText(sharedViewModel.distanceLimit.value!!.toString())
        }

        if(sharedViewModel.extraPrice.value!! != -1.0) {
            et_extra_price.setText(sharedViewModel.extraPrice.value!!.toString())
        }
    }


    private fun setKeyChangeListeners() {
        et_daily_price.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {
                checkRequiredInputs()
            }

            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
        })

        et_kilometer.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {
                checkRequiredInputs()
            }

            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
        })

        et_extra_price.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {
                checkRequiredInputs()
            }

            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
        })
    }


    private fun checkRequiredInputs() {
        if (et_daily_price.text.toString().isNotEmpty()
            && et_kilometer.text.toString().isNotEmpty()
            && et_extra_price.text.toString().isNotEmpty()
        ) {
            btn_next.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.basic_red))
            btn_next.isClickable = true
        } else {
            btn_next.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.light_grey))
            btn_next.isClickable = false
        }
    }


    private fun setOnClickListeners() {
        iv_close.setOnClickListener {
            requireActivity().finish()
        }

        // TODO: button functional only if all fields are filled and validated
        btn_next.setOnClickListener {
            findNavController(this).navigate(R.id.action_priceFragment_to_locationFragment)
            saveValuesToViewModel()
        }
    }


    private fun saveValuesToViewModel() {
        sharedViewModel.dailyPrice.value = et_daily_price.text.toString().toDouble()
        sharedViewModel.distanceLimit.value = et_kilometer.text.toString().toDouble()
        sharedViewModel.extraPrice.value = et_extra_price.text.toString().toDouble()
    }
}