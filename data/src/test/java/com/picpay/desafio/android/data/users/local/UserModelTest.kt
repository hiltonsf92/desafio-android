package com.picpay.desafio.android.data.users.local

import com.google.common.truth.Truth.assertThat
import com.picpay.desafio.android.domain.features.users.entities.User
import org.junit.Test

class UserModelTest {

    @Test
    fun toEntityTest() {
        val model = UserModel(
            id = 1,
            name = "Sandrine Spinka",
            username = "Tod86",
            img = "https://randomuser.me/api/portraits/men/1.jpg"
        )

        val entity = model.toEntity()

        assertThat(entity).isInstanceOf(User::class.java)
        assertThat(entity.name).isEqualTo(model.name)
        assertThat(entity.image).isEqualTo(model.img)
        assertThat(entity.username).isEqualTo(model.username)
    }

    @Test
    fun toEntityListTest() {
        val models = listOf(
            UserModel(
                id = 1,
                name = "Sandrine Spinka",
                username = "Tod86",
                img = "https://randomuser.me/api/portraits/men/1.jpg"
            ),
            UserModel(
                id = 2,
                name = "Carli Carroll",
                username = "Constantin_Sawayn",
                img = "https://randomuser.me/api/portraits/men/2.jpg"
            ),
            UserModel(
                id = 3,
                name = "Annabelle Reilly",
                username = "Lawrence_Nader62",
                img = "https://randomuser.me/api/portraits/men/3.jpg"
            )
        )

        val entities = models.toEntityList()

        assertThat(entities).hasSize(models.size)
        assertThat(entities.first()).isInstanceOf(User::class.java)
        assertThat(entities.first().name).isEqualTo(models.first().name)
        assertThat(entities.first().username).isEqualTo(models.first().username)
        assertThat(entities.first().image).isEqualTo(models.first().img)
    }
}