package com.picpay.desafio.android.domain.users.repositories

import com.picpay.desafio.android.domain.common.Either
import com.picpay.desafio.android.domain.users.entities.User
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    suspend fun findAll(): Flow<Either<List<User>, String>>
}