package com.picpay.desafio.android.data.users.remote

internal class UserRemoteDatasourceImpl(
    private val userApi: UserApi
) : UserRemoteDatasource {
    override suspend fun getUsers(): List<UserDto>? {
        val response = userApi.getUsers()
        if (response.isSuccessful) {
            return response.body()!!
        }
        return null
    }
}