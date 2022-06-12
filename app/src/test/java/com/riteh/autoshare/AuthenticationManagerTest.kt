package com.riteh.autoshare

import com.riteh.autoshare.data.LoginBody
import com.riteh.autoshare.data.SignUpBody
import com.riteh.autoshare.responses.AuthResponse
import com.riteh.autoshare.responses.User
import com.riteh.autoshare.util.MockResponseFileReader
import com.riteh.autoshare.util.managers.AuthenticationManager
import junit.framework.Assert.assertNull
import kotlinx.coroutines.runBlocking
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.koin.core.context.loadKoinModules
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.dsl.koinApplication
import org.koin.dsl.module
import org.koin.test.KoinTest
import org.koin.test.get
import java.net.URLDecoder
import kotlin.test.assertNotNull

class AuthenticationManagerTest : KoinTest {

    private lateinit var server: MockWebServer

    @Before
    fun initTest() {
        stopKoin()

        server = MockWebServer()
        val baseUrl = server.url("")

        val modules = listOf(module {
            single { AuthenticationManager(baseUrl.toUrl().toString()) }
        })


        startKoin {
            koinApplication { }
            loadKoinModules(modules)
        }
    }

    @After
    fun shutdown() {
        server.shutdown()
        stopKoin()
    }

    /**
     * Test whether login parameters received on the server are same as ones sent to it.
     * Assumes a successful request.
     */
    @Test
    fun `login sends proper body`() {
        server.apply {
            enqueue(MockResponse().setBody(MockResponseFileReader("auth_success.json").content))
        }

        get<AuthenticationManager>().apply {
            runBlocking { loginBlocking() }
        }

        val testBody = LoginBody(AuthenticationManager.email, AuthenticationManager.password)
        val requestBody = URLDecoder.decode(server.takeRequest().body.readUtf8(), "UTF-8")

        val requestParams = getParametersFromRequestBody(requestBody)
        val requestEmail = requestParams[0]
        val requestPassword = requestParams[1]

        assertEquals(requestEmail, testBody.email)
        assertEquals(requestPassword, testBody.password)
    }


    /**
     * Test whether login parameters received on the server are same as ones sent to it.
     * Assumes a successful request.
     */
    @Test
    fun `registration sends proper body`() {
        server.apply {
            enqueue(MockResponse().setBody(MockResponseFileReader("auth_success.json").content))
        }

        get<AuthenticationManager>().apply {
            runBlocking { signUpBlocking() }
        }

        val testBody = SignUpBody(
            AuthenticationManager.name,
            AuthenticationManager.surname,
            AuthenticationManager.email,
            AuthenticationManager.password
        )
        val requestBody = URLDecoder.decode(server.takeRequest().body.readUtf8(), "UTF-8")

        val requestParams = getParametersFromRequestBody(requestBody)
        val requestName = requestParams[0]
        val requestSurname = requestParams[1]
        val requestEmail = requestParams[2]
        val requestPassword = requestParams[3]

        assertEquals(requestName, testBody.name)
        assertEquals(requestSurname, testBody.surname)
        assertEquals(requestEmail, testBody.email)
        assertEquals(requestPassword, testBody.password)
    }


    /**
     * Tests login response saved to AuthenticationManager through API call.
     * Assumes successful response.
     */
    @Test
    fun `login receives success response`() {
        server.apply {
            enqueue(MockResponse().setBody(MockResponseFileReader("auth_success.json").content))
        }

        var loginResponse: AuthResponse
        var user: User?
        var token: String?

        get<AuthenticationManager>().apply {
            runBlocking { loginResponse = loginBlocking() }
        }

        server.takeRequest()

        get<AuthenticationManager>().apply {
            user = getUserFromManager()
            token = getTokenFromManager()
        }

        assertNotNull(user)
        assertNotNull(token)
        assertEquals(loginResponse.user, user)
        assertEquals(loginResponse.token, token)
    }


    /**
     * Tests register response saved to AuthenticationManager through API call.
     * Assumes successful response.
     */
    @Test
    fun `register receives success response`() {
        server.apply {
            enqueue(MockResponse().setBody(MockResponseFileReader("auth_success.json").content))
        }

        var signUpResponse: AuthResponse
        var user: User?
        var token: String?

        get<AuthenticationManager>().apply {
            runBlocking { signUpResponse = signUpBlocking() }
        }

        server.takeRequest()

        get<AuthenticationManager>().apply {
            user = getUserFromManager()
            token = getTokenFromManager()
        }

        assertNotNull(user)
        assertNotNull(token)
        assertEquals(signUpResponse.user, user)
        assertEquals(signUpResponse.token, token)
    }


    /**
     * Tests login response saved to AuthenticationManager through API call.
     * Assumes failed response.
     */
    @Test
    fun `login receives fail response`() {
        server.apply {
            enqueue(MockResponse().setBody(MockResponseFileReader("auth_fail.json").content))
        }

        var loginResponse: AuthResponse

        get<AuthenticationManager>().apply {
            runBlocking { loginResponse = loginBlocking() }
        }

        server.takeRequest()

        assertNull(loginResponse.user)
        assertNull(loginResponse.token)
    }


    /**
     * Tests register response saved to AuthenticationManager through API call.
     * Assumes failed response.
     */
    @Test
    fun `register receives fail response`() {
        server.apply {
            enqueue(MockResponse().setBody(MockResponseFileReader("auth_fail.json").content))
        }

        var signUpResponse: AuthResponse

        get<AuthenticationManager>().apply {
            runBlocking { signUpResponse = signUpBlocking() }
        }

        server.takeRequest()

        assertNull(signUpResponse.user)
        assertNull(signUpResponse.token)
    }


    private fun getParametersFromRequestBody(requestBody: String): List<String> {
        val regex = Regex("=(.*?)&")

        // transform matches to list of strings
        val matchesTmp = regex.findAll(requestBody)
        val matches: MutableList<String> = mutableListOf()
        matchesTmp.forEach { matchResult ->
            matches.add(matchResult.groupValues[1])
        }

        // append last value (it doesn't end in '&' so it's not in initial result)
        matches.add(requestBody.substringAfterLast("="))

        return matches
    }
}