package com.picpay.desafio.android.data.users.repositories

import com.picpay.desafio.android.data.users.local.UserLocalDatasource
import com.picpay.desafio.android.data.users.local.toEntityList
import com.picpay.desafio.android.data.users.remote.UserRemoteDatasource
import com.picpay.desafio.android.data.users.remote.toModelList
import com.picpay.desafio.android.domain.common.Either
import com.picpay.desafio.android.domain.users.entities.User
import com.picpay.desafio.android.domain.users.repositories.UserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow

internal class UserRepositoryImpl(
    private val userRemoteDatasource: UserRemoteDatasource,
    private val userLocalDatasource: UserLocalDatasource
) : UserRepository {
    override suspend fun findAll(): Flow<Either<List<User>, String>> = flow {
        val response = userRemoteDatasource.getUsers()
        if (response != null) {
            userLocalDatasource.insertAll(response.toModelList())
            emit(Either.Right(userLocalDatasource.getAll().toEntityList()))
        } else {
            emit(Either.Left("Ocorreu um erro. Tente novamente."))
        }
    }.catch {
        emit(Either.Left("Ocorreu um erro. Tente novamente."))
    }
}