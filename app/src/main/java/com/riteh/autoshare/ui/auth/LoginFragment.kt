package com.riteh.autoshare.ui.auth

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import com.riteh.autoshare.R
import com.riteh.autoshare.databinding.FragmentLoginBinding
import com.riteh.autoshare.network.AuthApi
import com.riteh.autoshare.network.Resource
import com.riteh.autoshare.repository.AuthRepository
import com.riteh.autoshare.ui.base.BaseFragment


class LoginFragment : BaseFragment<AuthViewModel, FragmentLoginBinding, AuthRepository>() {
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        /*viewModel.loginResponse.observe(viewLifecycleOwner, Observer {
            when(it){
                is Resource.Success -> {
                    Toast.makeText(requireContext(), it.toString(), Toast.LENGTH_LONG).show()
                }

                is Resource.Failure -> {
                    Toast.makeText(requireContext(), "Login Fail", Toast.LENGTH_SHORT).show()
                }
            }
        })*/

        binding.loginFragmentButton.setOnClickListener {
            val email = binding.loginFragmentEmail.text.toString().trim()
            val pasword = binding.loginFragmentPassword.text.toString().trim()

//            viewModel.login(email, pasword)
        }
    }
    override fun getViewModel() = AuthViewModel::class.java

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentLoginBinding.inflate(inflater, container, false)

//    override fun getFragmentRepository() = AuthRepository(remoteDataSource.buildApi(AuthApi::class.java))


}