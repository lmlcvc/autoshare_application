package com.riteh.autoshare.ui.home.add

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment.Companion.findNavController
import com.androidisland.vita.VitaOwner
import com.androidisland.vita.vita
import com.riteh.autoshare.R
import kotlinx.android.synthetic.main.fragment_price.*
import kotlinx.android.synthetic.main.search_fragment.*


class PriceFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_price, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setOnClickListeners()
    }

    private fun setOnClickListeners() {
        btn_prev.setOnClickListener {
            findNavController(this).navigate(R.id.action_priceFragment_to_vehicleSelectFragment)
        }

        btn_next.setOnClickListener {
            findNavController(this).navigate(R.id.action_priceFragment_to_locationFragment)
        }
    }
}