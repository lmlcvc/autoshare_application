package com.riteh.autoshare.ui.home.user.vehicles

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.riteh.autoshare.R
import com.riteh.autoshare.databinding.FragmentDetailsBinding
import kotlinx.android.synthetic.main.fragment_details.*


class DetailsFragment : Fragment() {

    private var binding: FragmentDetailsBinding? = null
    private lateinit var viewModel: VehicleInfoViewModel

    // private val sharedViewModel: VehicleDetailsViewModel by activityViewModels()

    companion object {
        fun newInstance() = DetailsFragment()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val fragmentBinding = FragmentDetailsBinding.inflate(inflater, container, false)
        binding = fragmentBinding
        return fragmentBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding?.detailsFragment = this

        setOnClickListeners()
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this)[VehicleInfoViewModel::class.java]
        // TODO: Use the ViewModel
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
            // TODO: validation

            findNavController().navigate(R.id.action_detailsFragment_to_vehicleAddingCompletedFragment)
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