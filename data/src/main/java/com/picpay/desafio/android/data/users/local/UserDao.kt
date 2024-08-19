package com.picpay.desafio.android.data.users.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
internal interface UserDao {
    @Query("SELECT * FROM users")
    suspend fun getAll(): List<UserModel>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(users: List<UserModel>)
}