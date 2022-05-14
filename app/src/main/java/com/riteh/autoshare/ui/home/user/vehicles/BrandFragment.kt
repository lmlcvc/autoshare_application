package com.riteh.autoshare.ui.home.user.vehicles

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.riteh.autoshare.adapters.BrandListAdapter
import com.riteh.autoshare.databinding.FragmentBrandBinding
import kotlinx.android.synthetic.main.fragment_brand.*

class BrandFragment : Fragment() {

    private var binding: FragmentBrandBinding? = null
    private lateinit var viewModel: VehicleDetailsViewModel

    private var brandsList = mutableListOf<String>()

    // private val sharedViewModel: VehicleDetailsViewModel by activityViewModels()

    companion object {
        fun newInstance() = BrandFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val fragmentBinding = FragmentBrandBinding.inflate(inflater, container, false)
        binding = fragmentBinding

        return fragmentBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding?.brandFragment = this

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

        rv_brands.layoutManager = GridLayoutManager(requireContext(), 1)
        rv_brands.adapter = BrandListAdapter(brandsList, requireContext())
    }

    /**
     * Set navigation click listeners.
     */
    private fun setOnClickListeners() {
        iv_back.setOnClickListener {
            // TODO navigate back
        }

        iv_close.setOnClickListener {
            // TODO cancel vehicle adding
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