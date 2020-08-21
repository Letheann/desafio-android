package com.picpay.desafio.android.helper.extensions

import android.app.Activity
import android.view.View
import android.widget.Toast


fun Activity.toast(text: String) {
    Toast.makeText(this, text, Toast.LENGTH_SHORT)
        .show()
}

fun View.hide() {
    this.visibility = View.GONE
}

fun View.show() {
    this.visibility = View.VISIBLE
}