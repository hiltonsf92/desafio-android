package com.picpay.desafio.android.data.users.local

internal interface UserLocalDatasource {
    suspend fun getAll(): List<UserModel>?
    suspend fun insertAll(users: List<UserModel>)
}