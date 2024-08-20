package com.picpay.desafio.android

import android.app.Application
import com.picpay.desafio.android.data.users.UsersModule
import com.picpay.desafio.android.features.contacts.di.ContactsModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class DesafioAndroidApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@DesafioAndroidApplication)
            androidLogger()
            modules(
                ContactsModule.all,
                UsersModule.all
            )
        }
    }
}