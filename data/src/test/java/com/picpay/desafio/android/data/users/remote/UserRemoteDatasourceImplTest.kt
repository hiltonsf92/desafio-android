package com.picpay.desafio.android.data.users.remote

import com.google.common.truth.Truth.assertThat
import com.picpay.desafio.android.data.ResourceHelper
import kotlinx.coroutines.test.runTest
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class UserRemoteDatasourceImplTest {

    private lateinit var server: MockWebServer
    private lateinit var userRemoteDatasource: UserRemoteDatasource

    @Before
    fun setup() {
        server = MockWebServer()
        server.start()
        val retrofit = Retrofit.Builder()
            .baseUrl(server.url("/"))
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        userRemoteDatasource = UserRemoteDatasourceImpl(retrofit.create(UserApi::class.java))
    }

    @After
    fun cleanup() {
        server.shutdown()
    }

    @Test
    fun getUsers_ResponseSuccess_ListUsers() = runTest {
        val responseBody = ResourceHelper.loadFile("users.json")
        server.enqueue(MockResponse().setBody(responseBody))

        val result = userRemoteDatasource.getUsers()
        server.takeRequest()

        assertThat(result).isNotNull()
        assertThat(result).isNotEmpty()
    }

    @Test
    fun getUsers_ResponseFailure_Null() = runTest {
        server.enqueue(MockResponse().setResponseCode(500))

        val result = userRemoteDatasource.getUsers()
        server.takeRequest()

        assertThat(result).isNull()
    }
}