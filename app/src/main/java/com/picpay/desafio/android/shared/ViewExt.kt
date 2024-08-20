package com.picpay.desafio.android.shared

import android.view.View
import androidx.core.view.isVisible

fun View.hidden() {
     if (isVisible) visibility = View.GONE
}

fun View.show() {
     if (!isVisible) visibility = View.VISIBLE
}