package com.picpay.desafio.android.data.users.local

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.filters.SmallTest
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import com.google.common.truth.Truth.assertThat
import com.picpay.desafio.android.data.core.DesafioAndroidDatabase
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

private val mockUsers = listOf(
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

@RunWith(AndroidJUnit4ClassRunner::class)
@SmallTest
class UserLocalDatasourceImplTest {

    private lateinit var database: DesafioAndroidDatabase
    private lateinit var userLocalDatasource: UserLocalDatasource

    @Before
    fun setup() {
        database = Room.inMemoryDatabaseBuilder(
            context = ApplicationProvider.getApplicationContext(),
            DesafioAndroidDatabase::class.java
        ).build()
        userLocalDatasource = UserLocalDatasourceImpl(database.createUserDao())
    }

    @After
    fun cleanup() {
        database.close()
    }

    @Test
    fun getAll_InsertedAll_ListUsers() = runTest {
        userLocalDatasource.insertAll(mockUsers)

        val users = userLocalDatasource.getAll()!!

        assertThat(users).hasSize(mockUsers.size)
        assertThat(users[0].id).isEqualTo(mockUsers[0].id)
        assertThat(users[0].name).isEqualTo(mockUsers[0].name)
        assertThat(users[0].username).isEqualTo(mockUsers[0].username)
        assertThat(users[0].img).isEqualTo(mockUsers[0].img)
    }
}