package com.picpay.desafio.android.data.users.local

internal class UserLocalDatasourceImpl(
    private val userDao: UserDao
) : UserLocalDatasource {
    override suspend fun getAll() = userDao.getAll()

    override suspend fun insertAll(users: List<UserModel>) {
        userDao.insertAll(users)
    }
}