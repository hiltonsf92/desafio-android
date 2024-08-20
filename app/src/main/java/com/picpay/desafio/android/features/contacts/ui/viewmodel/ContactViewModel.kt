package com.picpay.desafio.android.features.contacts.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.picpay.desafio.android.domain.users.entities.User
import com.picpay.desafio.android.domain.users.usecases.GetUsersUseCase
import com.picpay.desafio.android.shared.error
import com.picpay.desafio.android.shared.loading
import com.picpay.desafio.android.shared.mutableStateOf
import com.picpay.desafio.android.shared.success
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

class ContactViewModel(
    private val getUsersUseCase: GetUsersUseCase,
    private val dispatcher: CoroutineDispatcher = Dispatchers.Main
) : ViewModel() {

    private var _contactListState = mutableStateOf<List<User>>()
    val contactListState get() = _contactListState.asStateFlow()

    @Suppress("UNCHECKED_CAST")
    fun listContacts() = viewModelScope.launch(dispatcher) {
        _contactListState.value.state?.run { this as? List<User> }?.let {
            _contactListState.success(it)
        } ?: also {
            getUsersUseCase()
                .onStart { _contactListState.loading() }
                .collectLatest {
                    when {
                        it.isRight() -> _contactListState.success(it.getRight()!!)
                        it.isLeft() -> _contactListState.error(it.getLeft()!!)
                    }
                }
        }
    }
}