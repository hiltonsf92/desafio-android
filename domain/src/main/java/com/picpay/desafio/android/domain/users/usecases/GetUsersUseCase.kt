package com.picpay.desafio.android.domain.users.usecases

import com.picpay.desafio.android.domain.common.Either
import com.picpay.desafio.android.domain.users.entities.User
import com.picpay.desafio.android.domain.users.repositories.UserRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

class GetUsersUseCase(
    private val userRepository: UserRepository,
    private val dispatcher: CoroutineDispatcher = Dispatchers.Default
) {
    suspend operator fun invoke(): Flow<Either<List<User>, String>> = withContext(dispatcher) {
        userRepository.findAll()
    }
}