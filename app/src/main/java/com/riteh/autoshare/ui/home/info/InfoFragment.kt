package com.riteh.autoshare.ui.home.info

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.riteh.autoshare.R
import kotlinx.android.synthetic.main.info_fragment.*

class InfoFragment : Fragment() {

    companion object {
        fun newInstance() = InfoFragment()
    }

    private lateinit var viewModel: InfoViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.info_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this)[InfoViewModel::class.java]

        setUpListeners()
    }

    private fun setUpListeners() {
        btn_fuel.setOnClickListener {
            val intent = Intent(requireActivity(), FuelPricesActivity::class.java)
            startActivity(intent)
        }
    }

}