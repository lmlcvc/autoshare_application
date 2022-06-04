package com.riteh.autoshare.ui.home.user.vehicles

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.riteh.autoshare.R
import com.riteh.autoshare.databinding.FragmentExtraInfoFillInBinding
import kotlinx.android.synthetic.main.fragment_brand.*
import kotlinx.android.synthetic.main.fragment_extra_info_fill_in.*
import kotlinx.android.synthetic.main.fragment_extra_info_fill_in.iv_close

class ExtraInfoFillInFragment : Fragment() {

    private var binding: FragmentExtraInfoFillInBinding? = null

    // private lateinit var viewModel: ExtraInfoViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_extra_info_fill_in, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding?.extraInfoFillInFragment = this

        setOnClickListeners()
    }

    private fun setOnClickListeners() {
        btn_prev.setOnClickListener {
            findNavController().navigate(R.id.action_extraInfoFillInFragment_to_extraInfoSelectableFragment)
        }

        btn_next.setOnClickListener {
            findNavController().navigate(R.id.action_extraInfoFillInFragment_to_extraInfoBooleansFragment)
        }

        iv_close.setOnClickListener {
            requireActivity().finish()
        }
    }

}