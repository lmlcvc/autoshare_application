package com.riteh.autoshare.ui.auth

import android.util.Log
import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.riteh.autoshare.network.Resource
import com.riteh.autoshare.repository.AuthRepository
import com.riteh.autoshare.responses.LoginResponse
import com.riteh.autoshare.responses.SignUpResponse
import kotlinx.coroutines.launch

class AuthViewModel(
    private val repository: AuthRepository
) : ViewModel() {

    private val _signUpResponse : MutableLiveData<Resource<SignUpResponse>> = MutableLiveData()
    val signUpResponse: LiveData<Resource<SignUpResponse>>
    get() = _signUpResponse

    private val _loginResponse : MutableLiveData<Resource<LoginResponse>> = MutableLiveData()
    val loginResponse: LiveData<Resource<LoginResponse>>
    get() = _loginResponse
    fun login(
        email: String,
        password: String
    ) = viewModelScope.launch {
        _loginResponse.value = repository.login(email, password)
    }

    fun saveAuthToken(token: String) = viewModelScope.launch {
        repository.saveAuthToken(token)
    }

    fun validate(name: String,
                 surname: String,
                 email: String,
                 password: String,
                confirmPassword: String) {
        if(name.isEmpty() || surname.isEmpty() || password.isEmpty() || confirmPassword.isEmpty() || email.isEmpty()){
            return
        }
        if((password != confirmPassword) || password.length < 6){
            return
        }
        signUp(name, surname, email, password)
    }

    fun signUp(
        name: String,
        surname: String,
        email: String,
        password: String
    ) = viewModelScope.launch {

       /*ove podatke treba spremiti u bazu*/
        _signUpResponse.value = repository.userSignUp(name, surname, email, password)
    }
}