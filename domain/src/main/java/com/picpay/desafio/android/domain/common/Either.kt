package com.picpay.desafio.android.domain.common

sealed class Either<out R, out L> {
    data class Right<out R>(val value: R) : Either<R, Nothing>()
    data class Left<out L>(val value: L) : Either<Nothing, L>()

    fun isRight() = this is Right

    fun getRight() = (this as? Right)?.value

    fun isLeft() = this is Left

    fun getLeft() = (this as? Left)?.value
}