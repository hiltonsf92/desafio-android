package com.picpay.desafio.android.data.users.remote

import com.picpay.desafio.android.data.users.local.UserModel

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
