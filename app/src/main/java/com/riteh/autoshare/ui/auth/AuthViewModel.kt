package com.riteh.autoshare.ui.auth

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.riteh.autoshare.network.Resource
import com.riteh.autoshare.repository.AuthRepository
import com.riteh.autoshare.responses.AuthResponse
import kotlinx.coroutines.launch

class AuthViewModel(
    private val repository: AuthRepository
) : ViewModel() {

    private val _authResponse: MutableLiveData<Resource<AuthResponse>> = MutableLiveData()
    val authResponse: LiveData<Resource<AuthResponse>>
        get() = _authResponse


    fun validate(
        name: String,
        surname: String,
        email: String,
        password: String,
        confirmPassword: String
    ): Boolean {
        if (name.isEmpty() || surname.isEmpty() || password.isEmpty() || confirmPassword.isEmpty() || email.isEmpty()) {
            return false
        }
        if ((password != confirmPassword) || password.length < 6) {
            return false
        }
        signUp(name, surname, email, password)
        return true
    }


    fun login(
        email: String,
        password: String
    ) = viewModelScope.launch {
        _authResponse.value = repository.login(email, password)
    }


    fun signUp(
        name: String,
        surname: String,
        email: String,
        password: String
    ) = viewModelScope.launch {
        _authResponse.value = repository.userSignUp(name, surname, email, password)
    }

}