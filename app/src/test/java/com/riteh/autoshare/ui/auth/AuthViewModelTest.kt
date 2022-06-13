package com.riteh.autoshare.ui.auth
import com.riteh.autoshare.network.RemoteDataSource
import com.riteh.autoshare.repository.AuthRepository
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.mock
import org.mockito.kotlin.any
import org.mockito.kotlin.whenever


internal class AuthViewModelTest {
    private lateinit var remoteDataSource: RemoteDataSource
    private lateinit var repository: AuthRepository
    private lateinit var authViewModel: AuthViewModel

    @Before
    fun setup(){
        @Mock
        remoteDataSource = mock(RemoteDataSource::class.java)
        @Mock
        repository = mock(AuthRepository::class.java)
        @InjectMocks
        authViewModel = mock(AuthViewModel::class.java)
        whenever(authViewModel.validate(any(), any(), any(), any(), any())).thenCallRealMethod()
        whenever(authViewModel.signUp(any(), any(), any(), any())).thenReturn(null)
    }

    @Test
    fun `when confirm password not matching then fail validation`() {
        val name  = "test"
        val surname = "test"
        val email = "test@gmail.com"
        val password = "Password1234"
        val confirmPassword = "password4321"

        val response = authViewModel.validate(name, surname, email, password, confirmPassword)
        assertFalse(response)
    }

    @Test
    fun `when password too short then fail validation`() {
        val name  = "test"
        val surname = "test"
        val email = "test@gmail.com"
        val  password = "1234"
        val confirmPassword = "1234"

        val response = authViewModel.validate(name, surname, email, password, confirmPassword)
        assertFalse(response)
    }

    @Test
    fun `when name empty then fail validation`() {
        val name  = ""
        val surname = "test"
        val email = "test@gmail.com"
        val  password = "1234"
        val confirmPassword = "1234"

        val response = authViewModel.validate(name, surname, email, password, confirmPassword)
        assertFalse(response)
    }

    @Test
    fun `when surname empty then fail validation`() {
        val name  = "test"
        val surname = ""
        val email = "test@gmail.com"
        val  password = "1234"
        val confirmPassword = "1234"

        val response = authViewModel.validate(name, surname, email, password, confirmPassword)
        assertFalse(response)
    }

    @Test
    fun `when email empty then fail validation`() {
        val name  = "test"
        val surname = "test"
        val email = ""
        val  password = "1234"
        val confirmPassword = "1234"

        val response = authViewModel.validate(name, surname, email, password, confirmPassword)
        assertFalse(response)
    }

    @Test
    fun `when all fields empty then fail validation`() {
        val name  = ""
        val surname = ""
        val email = ""
        val  password = ""
        val confirmPassword = ""

        val response = authViewModel.validate(name, surname, email, password, confirmPassword)
        assertFalse(response)
    }

    @Test
    fun `when all fields valid then pass validation`() {
        val name  = "test"
        val surname = "test"
        val email = "test@gmail.com"
        val password = "Password1234"
        val confirmPassword = "Password1234"

        val response = authViewModel.validate(name, surname, email, password, confirmPassword)
        assertTrue(response)
    }
}