package com.picpay.desafio.android.data.users.repositories

import com.google.common.truth.Truth.assertThat
import com.picpay.desafio.android.data.ResourceHelper
import com.picpay.desafio.android.data.users.local.UserLocalDatasource
import com.picpay.desafio.android.data.users.remote.UserDto
import com.picpay.desafio.android.data.users.remote.UserRemoteDatasource
import com.picpay.desafio.android.data.users.remote.toModelList
import com.picpay.desafio.android.domain.users.repositories.UserRepository
import kotlinx.coroutines.flow.last
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.mockito.ArgumentMatchers.anyList
import org.mockito.Mock
import org.mockito.Mockito.times
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations

private const val ERROR_MESSAGE = "Ocorreu um erro. Tente novamente."

class UserRepositoryImplTest {

    @Mock
    private lateinit var userRemoteDatasource: UserRemoteDatasource

    @Mock
    private lateinit var userLocalDatasource: UserLocalDatasource

    private lateinit var userRepository: UserRepository

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        userRepository = UserRepositoryImpl(userRemoteDatasource, userLocalDatasource)
    }

    @Test
    fun findAll_ResponseSuccess_ListUsers() = runTest {
        val mockUsers = ResourceHelper.loadFile<List<UserDto>>("users.json")
        `when`(userRemoteDatasource.getUsers()).thenReturn(mockUsers)
        `when`(userLocalDatasource.getAll()).thenReturn(mockUsers.toModelList())

        val result = userRepository.findAll().last()

        verify(userLocalDatasource, times(1)).insertAll(anyList())

        assertThat(result.isRight()).isTrue()
        assertThat(result.getRight()).isNotEmpty()
    }

    @Test
    fun findAll_ResponseNull_ErrorMessage() = runTest {
        `when`(userRemoteDatasource.getUsers()).thenReturn(null)

        val result = userRepository.findAll().last()

        assertThat(result.isLeft()).isTrue()
        assertThat(result.getLeft()).isEqualTo(ERROR_MESSAGE)
    }

    @Test
    fun findAll_ResponseException_ErrorMessage() = runTest {
        `when`(userRemoteDatasource.getUsers()).thenThrow(RuntimeException())

        val result = userRepository.findAll().last()

        assertThat(result.isLeft()).isTrue()
        assertThat(result.getLeft()).isEqualTo(ERROR_MESSAGE)
    }
}