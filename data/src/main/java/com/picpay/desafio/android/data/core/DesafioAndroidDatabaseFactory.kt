package com.picpay.desafio.android.data.core

import android.content.Context
import androidx.room.Room

internal object DesafioAndroidDatabaseFactory {
    fun createInstance(applicationContext: Context): DesafioAndroidDatabase {
        return Room.databaseBuilder(
            applicationContext,
            DesafioAndroidDatabase::class.java,
            "desafio-android"
        ).build()
    }
}