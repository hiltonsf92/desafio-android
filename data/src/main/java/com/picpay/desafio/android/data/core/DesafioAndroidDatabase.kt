package com.picpay.desafio.android.data.core

import androidx.room.Database
import androidx.room.RoomDatabase
import com.picpay.desafio.android.data.users.local.UserDao
import com.picpay.desafio.android.data.users.local.UserModel

@Database(entities = [UserModel::class], version = 1, exportSchema = false)
internal abstract class DesafioAndroidDatabase : RoomDatabase() {
    abstract fun createUserDao(): UserDao
}