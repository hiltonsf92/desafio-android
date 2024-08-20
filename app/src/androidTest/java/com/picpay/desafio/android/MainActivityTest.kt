package com.picpay.desafio.android

import androidx.lifecycle.Lifecycle
import androidx.test.core.app.launchActivity
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.hasChildCount
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withEffectiveVisibility
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.filters.MediumTest
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import androidx.test.platform.app.InstrumentationRegistry
import com.picpay.desafio.android.domain.common.Either
import com.picpay.desafio.android.domain.users.entities.User
import com.picpay.desafio.android.domain.users.repositories.UserRepository
import com.picpay.desafio.android.domain.users.usecases.GetUsersUseCase
import com.squareup.picasso.Picasso
import com.squareup.picasso.Request
import com.squareup.picasso.RequestHandler
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.core.context.loadKoinModules
import org.koin.dsl.module

private val mockUsers = listOf(
    User(
        name = "Sandrine Spinka",
        username = "Tod86",
        image = "https://randomuser.me/api/portraits/men/1.jpg"
    ),
    User(
        name = "Carli Carroll",
        username = "Constantin_Sawayn",
        image = "https://randomuser.me/api/portraits/men/2.jpg"
    ),
    User(
        name = "Annabelle Reilly",
        username = "Lawrence_Nader62",
        image = "https://randomuser.me/api/portraits/men/3.jpg"
    )
)

private class FakeRequestHandler : RequestHandler() {
    override fun canHandleRequest(data: Request) = true
    override fun load(request: Request, networkPolicy: Int): Result? = null
}

private class FakeUserRepository : UserRepository {
    override suspend fun findAll(): Flow<Either<List<User>, String>> =
        flowOf(Either.Right(mockUsers))
}

@MediumTest
@RunWith(AndroidJUnit4ClassRunner::class)
class MainActivityTest {

    private val context = InstrumentationRegistry.getInstrumentation().targetContext

    @Before
    fun setup() {
        loadKoinModules(
            module {
                single {
                    Picasso.Builder(context)
                        .addRequestHandler(FakeRequestHandler())
                        .build()
                }
                factory { GetUsersUseCase(FakeUserRepository()) }
            }
        )
        val mainActivity = launchActivity<MainActivity>()
        mainActivity.moveToState(Lifecycle.State.RESUMED)
    }

    @Test
    fun shouldDisplayTitle() = runTest {
        val expectedTitle = context.getString(R.string.title)

        onView(withId(R.id.title)).check(matches(isDisplayed()))
        onView(withId(R.id.title)).check(matches(withText(expectedTitle)))
    }

    @Test
    fun shouldDisplayListItem() = runTest {
        onView(withId(R.id.recyclerView)).check(matches(withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE)))
        onView(withId(R.id.recyclerView)).check(matches(hasChildCount(mockUsers.size)))

        onView(withText(mockUsers[0].name)).check(matches(isDisplayed()))
        onView(withText(mockUsers[0].username)).check(matches(isDisplayed()))

        onView(withText(mockUsers[1].name)).check(matches(isDisplayed()))
        onView(withText(mockUsers[1].username)).check(matches(isDisplayed()))

        onView(withText(mockUsers[2].name)).check(matches(isDisplayed()))
        onView(withText(mockUsers[2].username)).check(matches(isDisplayed()))
    }
}