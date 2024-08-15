package com.picpay.desafio.android.domain.features.users.usecases

import com.google.common.truth.Truth.assertThat
import com.picpay.desafio.android.domain.common.Either
import com.picpay.desafio.android.domain.features.users.entities.User
import com.picpay.desafio.android.domain.features.users.repositories.UserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runTest
import org.junit.Test

private const val message = "Ocorreu um erro. Tente novamente."

private val mockUsers = listOf(
    User(
        name = "Sandrine Spinka",
        username = "Tod86",
        image = "https://randomuser.me/api/portraits/men/1.jpg"
    ),
    User(
        name = "Carli Carroll",
        username = "Constantin_Sawayn",
        image = "https://randomuser.me/api/portraits/men/2.jpg"
    ),
    User(
        name = "Annabelle Reilly",
        username = "Lawrence_Nader62",
        image = "https://randomuser.me/api/portraits/men/3.jpg"
    )
)

private class FakeUserRepository(private val isSuccessful: Boolean = true) : UserRepository {
    override suspend fun findAll(): Flow<Either<List<User>, String>> = flow {
        if (isSuccessful) {
            emit(Either.Right(mockUsers))
        } else {
            emit(Either.Left(message))
        }
    }
}

class GetUsersUseCaseTest {

    @Test
    fun getUsersUseCase_ResponseSuccess_ListUsers() = runTest {
        val getUsersUseCase = GetUsersUseCase(FakeUserRepository())

        val result = getUsersUseCase().first()

        assertThat(result.isRight()).isTrue()
        assertThat(result.getRight()?.size).isEqualTo(mockUsers.size)
        assertThat(result.getRight()?.first()).isInstanceOf(User::class.java)
    }

    @Test
    fun getUsersUseCase_ResponseFailure_Message() = runTest {
        val getUsersUseCase = GetUsersUseCase(FakeUserRepository(isSuccessful = false))

        val result = getUsersUseCase().first()

        assertThat(result.isLeft()).isTrue()
        assertThat(result.getLeft()).isEqualTo(message)
        assertThat(result.getLeft()).isInstanceOf(String::class.java)
    }
}