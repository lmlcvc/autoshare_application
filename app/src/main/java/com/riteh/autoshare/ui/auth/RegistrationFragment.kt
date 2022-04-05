package com.riteh.autoshare.ui.auth

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import com.riteh.autoshare.R
import com.riteh.autoshare.databinding.FragmentLoginBinding
import com.riteh.autoshare.databinding.FragmentRegistrationBinding
import com.riteh.autoshare.network.AuthApi
import com.riteh.autoshare.network.Resource
import com.riteh.autoshare.repository.AuthRepository
import com.riteh.autoshare.ui.base.BaseFragment
import com.riteh.autoshare.ui.home.MainActivity
import com.riteh.autoshare.ui.startNewActivity
import com.riteh.autoshare.ui.visable
import kotlinx.android.synthetic.main.fragment_login.*
import kotlinx.android.synthetic.main.fragment_registration.*
import kotlinx.coroutines.launch


class RegistrationFragment : BaseFragment<AuthViewModel, FragmentRegistrationBinding, AuthRepository>() {
    /*private lateinit var viewModel: AuthViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_registration, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        registerFragmentButton.setOnClickListener {
            val name = registerFragmentName.text.toString().trim()
            val surname = registerFragmentSurname.text.toString().trim()
            val email = registerFragmentEmail.text.toString().trim()
            val password = registerFragmentPassword.text.toString().trim()
            val confirmPassword = registerFragmentPasswordConfirm.text.toString().trim()

            viewModel.validate(name,surname, email, password, confirmPassword)
        }
    }*/

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel.loginResponse.observe(viewLifecycleOwner, Observer {

            when(it){
                is Resource.Success -> {
                    lifecycleScope.launch{
                         userPreferences.saveAuthToken(it.value.token)
                        requireActivity().startNewActivity(MainActivity::class.java)
                    }
                }

                is Resource.Failure -> {
                    Toast.makeText(requireContext(), "Login Fail", Toast.LENGTH_SHORT).show()
                }
            }
        })

        binding.registerFragmentButton.setOnClickListener {
            val name = binding.registerFragmentName.text.toString().trim()
            val surname = binding.registerFragmentSurname.text.toString().trim()
            val email = binding.registerFragmentEmail.text.toString().trim()
            val password = binding.registerFragmentPassword.text.toString().trim()
            val confirmPassword = binding.registerFragmentPasswordConfirm.text.toString().trim()

            viewModel.validate(name,surname, email, password, confirmPassword)
        }


    }
    override fun getViewModel() = AuthViewModel::class.java

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentRegistrationBinding.inflate(inflater, container, false)

    override fun getFragmentRepository() = AuthRepository(remoteDataSource.buildApi(AuthApi::class.java), userPreferences)
}