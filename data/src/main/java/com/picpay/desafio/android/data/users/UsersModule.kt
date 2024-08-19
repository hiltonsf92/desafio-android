package com.picpay.desafio.android.data.users

import com.picpay.desafio.android.data.core.DesafioAndroidDatabase
import com.picpay.desafio.android.data.core.DesafioAndroidDatabaseFactory
import com.picpay.desafio.android.data.core.RetrofitFactory
import com.picpay.desafio.android.data.users.local.UserDao
import com.picpay.desafio.android.data.users.local.UserLocalDatasource
import com.picpay.desafio.android.data.users.local.UserLocalDatasourceImpl
import com.picpay.desafio.android.data.users.remote.UserApi
import com.picpay.desafio.android.data.users.remote.UserRemoteDatasource
import com.picpay.desafio.android.data.users.remote.UserRemoteDatasourceImpl
import com.picpay.desafio.android.data.users.repositories.UserRepositoryImpl
import com.picpay.desafio.android.domain.features.users.repositories.UserRepository
import org.koin.dsl.module
import retrofit2.Retrofit

object UsersModule {
    private val local = module {
        single<DesafioAndroidDatabase> { DesafioAndroidDatabaseFactory.createInstance(get()) }
        single<UserDao> { get<DesafioAndroidDatabase>().createUserDao() }
        factory<UserLocalDatasource> { UserLocalDatasourceImpl(get()) }
    }

    private val remote = module {
        single<Retrofit> { RetrofitFactory.createInstance() }
        single<UserApi> { get<Retrofit>().create(UserApi::class.java) }
        factory<UserRemoteDatasource> { UserRemoteDatasourceImpl(get()) }
    }

    val all = module {
        includes(listOf(local, remote))
        factory<UserRepository> { UserRepositoryImpl(get(), get()) }
    }
}