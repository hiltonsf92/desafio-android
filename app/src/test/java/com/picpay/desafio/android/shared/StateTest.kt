package com.picpay.desafio.android.shared

import com.google.common.truth.Truth.assertThat
import com.picpay.desafio.android.domain.users.entities.User
import kotlinx.coroutines.flow.last
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.runTest
import org.junit.Test

class StateTest {

    @Test
    fun mutableStateOf_FlowSuccess_User() = runTest {
        val mock = User(
            name = "Sandrine Spinka",
            username = "Tod86",
            image = "https://randomuser.me/api/portraits/men/1.jpg"
        )

        val stateFlow = mutableStateOf<User>()

        stateFlow.loading()
        backgroundScope.launch { stateFlow.last() }

        assertThat(stateFlow.value).isEqualTo(State.Loading)

        stateFlow.success(mock)
        backgroundScope.launch { stateFlow.last() }

        val state = stateFlow.value.state as User

        assertThat(state.name).isEqualTo(mock.name)
        assertThat(state.username).isEqualTo(mock.username)
        assertThat(state.image).isEqualTo(mock.image)
    }

    @Test
    fun mutableStateOf_FlowError_Message() = runTest {
        val stateFlow = mutableStateOf<String>()

        stateFlow.loading()
        backgroundScope.launch { stateFlow.last() }

        assertThat(stateFlow.value).isEqualTo(State.Loading)

        stateFlow.error("Error")
        backgroundScope.launch { stateFlow.last() }

        assertThat(stateFlow.value.hasError()).isTrue()
        assertThat(stateFlow.value.state).isEqualTo("Error")
    }

    @Test
    fun mutableStateOf_HandleSuccess_User() = runTest {
        val mock = User(
            name = "Sandrine Spinka",
            username = "Tod86",
            image = "https://randomuser.me/api/portraits/men/1.jpg"
        )

        val stateFlow = mutableStateOf<User>()

        stateFlow.loading()
        backgroundScope.launch { stateFlow.last() }

        stateFlow.value.handle(
            loading = { assertThat(stateFlow.value).isEqualTo(State.Loading) },
            success = {},
            error = {}
        )

        stateFlow.success(mock)
        backgroundScope.launch { stateFlow.last() }

        stateFlow.value.handle(
            loading = {},
            success = { state ->
                assertThat(state.name).isEqualTo(mock.name)
                assertThat(state.username).isEqualTo(mock.username)
                assertThat(state.image).isEqualTo(mock.image)
            },
            error = {}
        )
    }

    @Test
    fun mutableStateOf_HandleError_Message() = runTest {
        val stateFlow = mutableStateOf<String>()

        stateFlow.loading()
        backgroundScope.launch { stateFlow.last() }

        stateFlow.value.handle(
            loading = { assertThat(stateFlow.value).isEqualTo(State.Loading) },
            success = {},
            error = {}
        )

        stateFlow.error("Error")
        backgroundScope.launch { stateFlow.last() }

        stateFlow.value.handle(
            loading = {},
            success = {},
            error = { assertThat(stateFlow.value.state).isEqualTo("Error") }
        )
    }
}