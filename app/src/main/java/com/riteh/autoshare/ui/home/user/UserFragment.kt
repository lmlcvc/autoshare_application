package com.riteh.autoshare.ui.home.user

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.riteh.autoshare.R
import kotlinx.android.synthetic.main.user_fragment.*


class UserFragment : Fragment() {

    companion object {
        fun newInstance() = UserFragment()
    }

    private lateinit var viewModel: UserViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.user_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        btn_card_info.setOnClickListener {
            val intent = Intent(it.context, UserCardInfoActivity::class.java)
            startActivity(intent)
        }
        viewModel = ViewModelProvider(this)[UserViewModel::class.java]

        btn_pers_info.setOnClickListener {
            val intent = Intent(it.context, UserInformationActivity::class.java)
            startActivity(intent)
        }

        btn_my_vehicles.setOnClickListener {
            val intent = Intent(it.context, VehicleListActivity::class.java)
            startActivity(intent)
        }
    }

}