package com.picpay.desafio.android.data.users.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.picpay.desafio.android.domain.users.entities.User

@Entity(tableName = "users")
internal data class UserModel(
    @PrimaryKey
    val id: Int,
    val name: String,
    val img: String,
    val username: String
)

internal fun UserModel.toEntity(): User = User(
    name = this.name,
    image = this.img,
    username = this.username
)

internal fun List<UserModel>.toEntityList(): List<User> = this.map { it.toEntity() }.toList()
