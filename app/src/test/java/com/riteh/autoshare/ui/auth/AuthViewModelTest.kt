package com.riteh.autoshare.ui.auth
import com.riteh.autoshare.network.AuthApi
import com.riteh.autoshare.network.RemoteDataSource
import com.riteh.autoshare.repository.AuthRepository
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test


class AuthViewModelTest {
    private lateinit var remoteDataSource: RemoteDataSource
    private lateinit var repository: AuthRepository
    private lateinit var authViewModel: AuthViewModel

    @Before
    fun setup(){
        remoteDataSource = RemoteDataSource()
        repository = AuthRepository(remoteDataSource.buildApi(AuthApi::class.java))
        authViewModel = AuthViewModel(repository)
    }

    @Test
    fun validateConfirmPassword() {
        val name  = "test"
        val surname = "test"
        val email = "test@gmail.com"
        val  password = "1234"
        val confirmPassword = "4321"

        val response = authViewModel.validate(name, surname, email, password, confirmPassword)
        assertEquals(false, response);
    }

    @Test
    fun validateLengthPassword() {
        val name  = "test"
        val surname = "test"
        val email = "test@gmail.com"
        val  password = "1234"
        val confirmPassword = "1234"

        val response = authViewModel.validate(name, surname, email, password, confirmPassword)
        assertEquals(false, response);
    }

    @Test
    fun validateName() {
        val name  = ""
        val surname = "test"
        val email = "test@gmail.com"
        val  password = "1234"
        val confirmPassword = "1234"

        val response = authViewModel.validate(name, surname, email, password, confirmPassword)
        assertEquals(false, response);
    }

    @Test
    fun validateSurname() {
        val name  = "test"
        val surname = ""
        val email = "test@gmail.com"
        val  password = "1234"
        val confirmPassword = "1234"

        val response = authViewModel.validate(name, surname, email, password, confirmPassword)
        assertEquals(false, response);
    }

    @Test
    fun validateEmail() {
        val name  = "test"
        val surname = "test"
        val email = ""
        val  password = "1234"
        val confirmPassword = "1234"

        val response = authViewModel.validate(name, surname, email, password, confirmPassword)
        assertEquals(false, response);
    }

    @Test
    fun validateFail() {
        val name  = ""
        val surname = ""
        val email = ""
        val  password = ""
        val confirmPassword = ""

        val response = authViewModel.validate(name, surname, email, password, confirmPassword)
        assertEquals(false, response);
    }
}