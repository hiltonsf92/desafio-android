package com.picpay.desafio.android.domain.common

import com.google.common.truth.Truth.assertThat
import org.junit.Test

class EitherTest {

    @Test
    fun isRightTest() {
        val value = "Success"
        val result = Either.Right(value)
        assertThat(result.isRight()).isTrue()
        assertThat(result.getRight()).isEqualTo(value)
    }

    @Test
    fun isLeftTest() {
        val message = "Error"
        val result = Either.Left(message)
        assertThat(result.isLeft()).isTrue()
        assertThat(result.getLeft()).isEqualTo(message)
    }
}