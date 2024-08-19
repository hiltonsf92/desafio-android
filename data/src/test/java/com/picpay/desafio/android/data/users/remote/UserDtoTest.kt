package com.picpay.desafio.android.data.users.remote

import com.google.common.truth.Truth.assertThat
import com.picpay.desafio.android.data.users.local.UserModel
import org.junit.Test

class UserDtoTest {

    @Test
    fun toModelTest() {
        val dto = UserDto(
            id = 1,
            name = "Sandrine Spinka",
            username = "Tod86",
            img = "https://randomuser.me/api/portraits/men/1.jpg"
        )

        val model = dto.toModel()

        assertThat(model).isInstanceOf(UserModel::class.java)
        assertThat(model.id).isEqualTo(dto.id)
        assertThat(model.name).isEqualTo(dto.name)
        assertThat(model.img).isEqualTo(dto.img)
        assertThat(model.username).isEqualTo(dto.username)
    }

    @Test
    fun toModelListTest() {
        val dtos = listOf(
            UserDto(
                id = 1,
                name = "Sandrine Spinka",
                username = "Tod86",
                img = "https://randomuser.me/api/portraits/men/1.jpg"
            ),
            UserDto(
                id = 2,
                name = "Carli Carroll",
                username = "Constantin_Sawayn",
                img = "https://randomuser.me/api/portraits/men/2.jpg"
            ),
            UserDto(
                id = 3,
                name = "Annabelle Reilly",
                username = "Lawrence_Nader62",
                img = "https://randomuser.me/api/portraits/men/3.jpg"
            )
        )

        val models = dtos.toModelList()

        assertThat(models).hasSize(dtos.size)
        assertThat(models.first()).isInstanceOf(UserModel::class.java)
        assertThat(models.first().id).isEqualTo(dtos.first().id)
        assertThat(models.first().name).isEqualTo(dtos.first().name)
        assertThat(models.first().username).isEqualTo(dtos.first().username)
        assertThat(models.first().img).isEqualTo(dtos.first().img)
    }
}