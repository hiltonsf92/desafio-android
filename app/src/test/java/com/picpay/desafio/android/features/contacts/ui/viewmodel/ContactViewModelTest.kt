package com.picpay.desafio.android.features.contacts.ui.viewmodel

import com.google.common.truth.Truth.assertThat
import com.nhaarman.mockitokotlin2.doReturn
import com.picpay.desafio.android.domain.common.Either
import com.picpay.desafio.android.domain.users.entities.User
import com.picpay.desafio.android.domain.users.usecases.GetUsersUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.last
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations

private const val ERROR_MESSAGE = "Ocorreu um erro. Tente novamente."

class ContactViewModelTest {

    @Mock
    private lateinit var getUsersUseCase: GetUsersUseCase

    private lateinit var contactViewModel: ContactViewModel

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        contactViewModel = ContactViewModel(getUsersUseCase, Dispatchers.Unconfined)
    }

    @Test
    fun listContacts_ReturnSuccess_ListUsers() = runTest {
        val mock = listOf(
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

        doReturn(flowOf(Either.Right(mock))).`when`(getUsersUseCase).invoke()

        contactViewModel.listContacts()
        backgroundScope.launch { contactViewModel.contactListState.last() }

        @Suppress("UNCHECKED_CAST")
        val users = contactViewModel.contactListState.value.state as List<User>

        assertThat(users).hasSize(mock.size)
        assertThat(users).isEqualTo(mock)
    }

    @Test
    fun listContacts_ReturnError_Message() = runTest {
        doReturn(flowOf(Either.Left(ERROR_MESSAGE))).`when`(getUsersUseCase).invoke()

        contactViewModel.listContacts()
        backgroundScope.launch { contactViewModel.contactListState.last() }

        assertThat(contactViewModel.contactListState.value.hasError()).isTrue()
        assertThat(contactViewModel.contactListState.value.state).isEqualTo(ERROR_MESSAGE)
    }
}