package com.riteh.autoshare.ui.home.user.vehicles

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.riteh.autoshare.adapters.ModelListAdapter
import com.riteh.autoshare.databinding.FragmentModelBinding
import kotlinx.android.synthetic.main.fragment_model.*
import kotlinx.android.synthetic.main.fragment_model.iv_close

class ModelFragment : Fragment() {

    private var binding: FragmentModelBinding? = null
    private lateinit var viewModel: VehicleDetailsViewModel

    private var modelsList = mutableListOf<String>()

    // private val sharedViewModel: VehicleDetailsViewModel by activityViewModels()

    companion object {
        fun newInstance() = ModelFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val fragmentBinding = FragmentModelBinding.inflate(inflater, container, false)
        binding = fragmentBinding
        return fragmentBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding?.modelFragment = this

        setUpRecyclerView()
        setOnClickListeners()
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this)[VehicleDetailsViewModel::class.java]
        // TODO: Use the ViewModel
    }

    /**
     * Set up recyclerview with brand suggestions.
     */
    private fun setUpRecyclerView() {
        // api call

        rv_models.layoutManager = GridLayoutManager(requireContext(), 1)
        rv_models.adapter = ModelListAdapter(modelsList, requireContext())
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
    }

    /**
     * Clear out the binding object.
     */
    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

}