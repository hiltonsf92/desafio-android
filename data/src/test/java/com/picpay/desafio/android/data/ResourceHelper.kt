package com.picpay.desafio.android.data

import com.google.common.reflect.TypeToken
import com.google.gson.GsonBuilder

object ResourceHelper {
    fun loadFile(name: String): String {
        return javaClass.classLoader
            ?.getResourceAsStream(name)
            ?.readBytes()?.let { String(it) } ?: String()
    }

    inline fun <reified T> loadFile(name: String): T {
        val json = loadFile(name)
        return GsonBuilder()
            .serializeNulls()
            .setLenient()
            .create()
            .fromJson(json, object : TypeToken<T>() {}.type)
    }
}