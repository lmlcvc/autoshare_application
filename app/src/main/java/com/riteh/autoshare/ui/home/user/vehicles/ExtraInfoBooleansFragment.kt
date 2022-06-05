package com.riteh.autoshare.ui.home.user.vehicles

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.riteh.autoshare.R
import com.riteh.autoshare.databinding.FragmentExtraInfoBooleansBinding
import kotlinx.android.synthetic.main.fragment_brand.*
import kotlinx.android.synthetic.main.fragment_extra_info_booleans.*
import kotlinx.android.synthetic.main.fragment_extra_info_booleans.iv_close

class ExtraInfoBooleansFragment : Fragment() {

    private var binding: FragmentExtraInfoBooleansBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_extra_info_booleans, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding?.extraInfoBooleansFragment = this

        setOnClickListeners()
    }

    private fun setOnClickListeners() {
        btn_prev.setOnClickListener {
            findNavController().navigate(R.id.action_extraInfoBooleansFragment_to_extraInfoFillInFragment)
        }

        iv_close.setOnClickListener {
            requireActivity().finish()
        }
    }
}