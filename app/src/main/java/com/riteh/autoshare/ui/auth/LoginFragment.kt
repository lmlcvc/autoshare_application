package com.riteh.autoshare.ui.auth

import android.os.Bundle
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import com.riteh.autoshare.R
import com.riteh.autoshare.databinding.FragmentLoginBinding
import com.riteh.autoshare.network.AuthApi
import com.riteh.autoshare.network.Resource
import com.riteh.autoshare.repository.AuthRepository
import com.riteh.autoshare.ui.base.BaseFragment
import com.riteh.autoshare.ui.home.MainActivity
import com.riteh.autoshare.ui.startNewActivity
import com.riteh.autoshare.ui.visible
import kotlinx.android.synthetic.main.fragment_login.*
import kotlinx.coroutines.launch


class LoginFragment : BaseFragment<AuthViewModel, FragmentLoginBinding, AuthRepository>() {
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        binding.progressbar.visible(false)

        viewModel.loginResponse.observe(viewLifecycleOwner, Observer {
            binding.progressbar.visible(false)
            when (it) {
                is Resource.Success -> {
                    lifecycleScope.launch {
                        userPreferences.saveAuthToken(it.value.token)
                        requireActivity().startNewActivity(MainActivity::class.java)
                    }
                }

                is Resource.Failure -> {
                    Toast.makeText(requireContext(), "Login Fail", Toast.LENGTH_SHORT).show()
                }
            }
        })

        setOnCLickListeners()


    }

    override fun getViewModel() = AuthViewModel::class.java

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentLoginBinding.inflate(inflater, container, false)

    override fun getFragmentRepository() =
        AuthRepository(remoteDataSource.buildApi(AuthApi::class.java), userPreferences)


    private fun setOnCLickListeners() {
        binding.loginFragmentButton.setOnClickListener {
            val email = binding.loginFragmentEmail.text.toString().trim()
            val pasword = binding.loginFragmentPassword.text.toString().trim()
            binding.progressbar.visible(true)

            viewModel.login(email, pasword)
        }

        loginFragmentRegisterLink.setOnClickListener { view ->
            view.findNavController().navigate(R.id.action_loginFragment_to_registrationFragment)
        }

        iv_show.setOnClickListener {
            loginFragmentPassword.transformationMethod =
                HideReturnsTransformationMethod.getInstance()

            iv_hide.visibility = View.VISIBLE
            iv_show.visibility = View.GONE
        }

        iv_hide.setOnClickListener {
            loginFragmentPassword.transformationMethod = PasswordTransformationMethod.getInstance()

            iv_show.visibility = View.VISIBLE
            iv_hide.visibility = View.GONE
        }
    }
}