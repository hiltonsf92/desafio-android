package com.picpay.desafio.android.data.users.remote

internal interface UserRemoteDatasource {
    suspend fun getUsers(): List<UserDto>?
}