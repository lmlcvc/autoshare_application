package com.riteh.autoshare.ui.auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import com.riteh.autoshare.R
import com.riteh.autoshare.databinding.FragmentRegistrationBinding
import com.riteh.autoshare.network.AuthApi
import com.riteh.autoshare.network.Resource
import com.riteh.autoshare.repository.AuthRepository
import com.riteh.autoshare.ui.base.BaseFragment
import com.riteh.autoshare.ui.home.MainActivity
import com.riteh.autoshare.ui.startNewActivity
import kotlinx.android.synthetic.main.fragment_registration.*
import kotlinx.coroutines.launch


class RegistrationFragment :
    BaseFragment<AuthViewModel, FragmentRegistrationBinding, AuthRepository>() {

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel.authResponse.observe(viewLifecycleOwner, Observer {

            when (it) {
                is Resource.Success -> {
                    lifecycleScope.launch {
                        userPreferences.saveUserInfo(it.value.user)
                        userPreferences.saveUserToken(it.value.token)

                        Toast.makeText(requireContext(), "Signup successful!", Toast.LENGTH_SHORT).show()

                        requireActivity().startNewActivity(MainActivity::class.java)
                    }
                }

                is Resource.Failure -> {
                    Toast.makeText(requireContext(), "Signup failed", Toast.LENGTH_SHORT).show()
                }
            }
        })

        binding.registerFragmentButton.setOnClickListener {
            val name = binding.registerFragmentName.text.toString().trim()
            val surname = binding.registerFragmentSurname.text.toString().trim()
            val email = binding.registerFragmentEmail.text.toString().trim()
            val password = binding.registerFragmentPassword.text.toString().trim()
            val confirmPassword = binding.registerFragmentPasswordConfirm.text.toString().trim()

            viewModel.validate(name, surname, email, password, confirmPassword)

            // TODO: intent to mainactivity if validated
        }

        tv_to_login.setOnClickListener { view ->
            view.findNavController().navigate(R.id.action_registrationFragment_to_loginFragment)
        }

    }

    override fun getViewModel() = AuthViewModel::class.java

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentRegistrationBinding.inflate(inflater, container, false)

    override fun getFragmentRepository() =
        AuthRepository(remoteDataSource.buildApi(AuthApi::class.java))
}