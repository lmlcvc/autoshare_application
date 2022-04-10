package com.riteh.autoshare.ui.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.riteh.autoshare.repository.AuthRepository
import com.riteh.autoshare.repository.BaseRepository
import com.riteh.autoshare.ui.auth.AuthViewModel
import java.lang.IllegalArgumentException

class ViewModelFactory(
    private val repository: BaseRepository
) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when{
            modelClass.isAssignableFrom(AuthViewModel::class.java) -> AuthViewModel(repository as AuthRepository) as T

            else -> throw IllegalArgumentException("ViewModelClass not found")
        }
    }
}