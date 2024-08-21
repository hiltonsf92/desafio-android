package com.picpay.desafio.android.data.users.local

internal class UserLocalDatasourceImpl(
    private val userDao: UserDao
) : UserLocalDatasource {
    override suspend fun getAll(): List<UserModel>? {
        val users = userDao.getAll()
        if (users.isEmpty()) {
            return null
        }
        return users
    }

    override suspend fun insertAll(users: List<UserModel>) {
        userDao.insertAll(users)
    }
}