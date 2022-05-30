package com.riteh.autoshare.ui.home.add

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.NavHostFragment
import com.riteh.autoshare.R
import com.riteh.autoshare.databinding.FragmentLocationBinding
import kotlinx.android.synthetic.main.fragment_location.*


class LocationFragment : Fragment() {

    private var binding: FragmentLocationBinding? = null
    private val sharedViewModel: AddViewModel by activityViewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_location, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setOnClickListeners()
    }

    private fun setOnClickListeners() {
        btn_prev.setOnClickListener {
            NavHostFragment.findNavController(this).navigate(R.id.action_locationFragment_to_priceFragment)
        }

        btn_next.setOnClickListener {
            NavHostFragment.findNavController(this).navigate(R.id.action_locationFragment_to_dateFragment)
        }
    }
}