package com.picpay.desafio.android.features.contacts.di

import com.squareup.picasso.Picasso
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

object PicassoComponent : KoinComponent {
    val instance: Picasso by inject()
}