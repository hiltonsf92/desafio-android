package com.picpay.desafio.android.shared

import kotlinx.coroutines.flow.MutableStateFlow

sealed class State<out T : Any> {
    data object None : State<Nothing>()
    data object Loading : State<Nothing>()
    data class Success<out T : Any>(val data: T) : State<T>()
    data class Error(val message: String) : State<Nothing>()

    val state: Any?
        get() = when (this) {
            is Success -> data
            is Error -> message
            else -> null
        }

    fun handle(
        loading: () -> Unit,
        success: (data: T) -> Unit,
        error: (message: String) -> Unit
    ) {
        when (this) {
            is Loading -> loading()
            is Success -> success(data)
            is Error -> error(message)
            else -> Unit
        }
    }

    fun hasError() = this is Error
}

fun <T : Any> mutableStateOf(): MutableStateFlow<State<T>> {
    return MutableStateFlow(State.None)
}

fun <T : Any> MutableStateFlow<State<T>>.loading() {
    this.value = State.Loading
}

fun <T : Any> MutableStateFlow<State<T>>.success(data: T) {
    this.value = State.Success(data)
}

fun <T : Any> MutableStateFlow<State<T>>.error(message: String) {
    this.value = State.Error(message)
}