package com.picpay.desafio.android.features.contacts.di

import com.picpay.desafio.android.domain.users.usecases.GetUsersUseCase
import com.picpay.desafio.android.features.contacts.ui.viewmodel.ContactViewModel
import com.squareup.picasso.Picasso
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

object ContactsModule {
    val all = module {
        single<Picasso> { Picasso.get() }
        factory { GetUsersUseCase(get()) }
        viewModel { ContactViewModel(get()) }
    }
}