package com.riteh.autoshare.ui.home.user.vehicles

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.riteh.autoshare.R
import com.riteh.autoshare.databinding.FragmentDetailsBinding
import kotlinx.android.synthetic.main.fragment_details.*
import java.util.regex.Pattern


class DetailsFragment : Fragment() {

    private var binding: FragmentDetailsBinding? = null

    private val sharedViewModel: VehicleInfoViewModel by activityViewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val fragmentBinding = FragmentDetailsBinding.inflate(inflater, container, false)
        binding = fragmentBinding
        return fragmentBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding?.detailsFragment = this

        setOnClickListeners()
        setKeyChangeListeners()
        button.isClickable = false
    }


    /**
     * Set navigation click listeners.
     */
    private fun setOnClickListeners() {
        iv_back.setOnClickListener {
            // navigate back
        }

        iv_close.setOnClickListener {
            // cancel vehicle adding
        }

        button.setOnClickListener {
            // reformatirati datum u yyyy-mm-dd

            findNavController().navigate(R.id.action_detailsFragment_to_vehicleAddingCompletedFragment)
        }
    }


    /**
     * Validate if date matches DD-MM-YYYY pattern.
     *
     * @return True if yes
     */
    private fun dateIsValid(date: String): Boolean {
        val pattern: Pattern = Pattern.compile(
            "^\\d{2}-\\d{2}-\\d{4}$"
        )

        return pattern.matcher(date).matches()
    }

    /**
     * Set listeners for editable items.
     *
     * - required fields input watchers for button enabling/disabling
     * - formatters for license and date fields
     */
    private fun setKeyChangeListeners() {
        et_seats.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {
                checkRequiredInputs()
            }

            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
        })

        et_doors.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {
                checkRequiredInputs()
            }

            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
        })

        et_year.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {
                checkRequiredInputs()
            }

            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
        })

        et_license.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {
                checkRequiredInputs()
            }

            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                applyLicenseFormatting()
            }
        })

        et_expiration.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {
                checkRequiredInputs()
            }

            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                if (before <= 0) {  // if text is being written, not deleted
                    applyDateFormatting(before)
                }
            }
        })
    }


    /**
     * Check if required text input fields have been filled and change button functionality accordingly.
     */
    private fun checkRequiredInputs() {
        if (et_seats.text.toString().isNotEmpty()
            && et_doors.text.toString().isNotEmpty()
            && et_year.text.toString().isNotEmpty()
            && et_license.text.toString().isNotEmpty()
            && dateIsValid(et_expiration.text.toString())
        ) {
            button.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.basic_red))
            button.isClickable = true
        } else {
            button.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.light_grey))
            button.isClickable = false
        }
    }


    /**
     * Format license string to be uppercase and contain only alphanumeric characters.
     * Handle moving the cursor to end of string when setText is applied.
     */
    private fun applyLicenseFormatting() {
        val currentLicenseText = et_license.text.toString()
        if (!currentLicenseText[currentLicenseText.lastIndex].isLetterOrDigit()
            && currentLicenseText.lastIndex > -1
        ) {
            val tmpLicenseText = et_license.text.toString().dropLast(1)

            et_license.setText(tmpLicenseText)
            et_license.setSelection(et_license.length())
        }
    }


    /**
     * Add a "-" after day or month input.
     * Handle moving the cursor to end of string when setText is applied.
     */
    private fun applyDateFormatting(before: Int) {
        if (et_expiration.text.toString().length <= before) return

        when (et_expiration.text.toString().length) {
            2 -> {
                val tmpExpirationText = "${et_expiration.text.toString()}-"
                et_expiration.setText(tmpExpirationText)
                et_expiration.setSelection(et_expiration.length())
            }
            5 -> {
                val tmpExpirationText = "${et_expiration.text.toString()}-"
                et_expiration.setText(tmpExpirationText)
                et_expiration.setSelection(et_expiration.length())
            }
            else -> return

        }
    }


    /**
     * Clear out the binding object.
     */
    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

}