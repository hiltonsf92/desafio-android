package com.picpay.desafio.android.data.users.remote

import com.picpay.desafio.android.data.users.local.UserModel
import com.picpay.desafio.android.domain.users.entities.User

internal data class UserDto(
    val id: Int,
    val name: String,
    val img: String,
    val username: String
)

internal fun UserDto.toModel(): UserModel = UserModel(
    id = this.id,
    name = this.name,
    img = this.img,
    username = this.username
)

internal fun List<UserDto>.toModelList(): List<UserModel> = this.map { it.toModel() }.toList()

internal fun UserDto.toEntity(): User = User(
    name = this.name,
    image = this.img,
    username = this.username
)

internal fun List<UserDto>.toEntityList(): List<User> = this.map { it.toEntity() }.toList()
