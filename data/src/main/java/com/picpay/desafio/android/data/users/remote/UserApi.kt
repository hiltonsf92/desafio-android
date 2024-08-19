package com.picpay.desafio.android.data.users.remote

import retrofit2.Response
import retrofit2.http.GET

internal interface UserApi {

    @GET("users")
    suspend fun getUsers(): Response<List<UserDto>>
}