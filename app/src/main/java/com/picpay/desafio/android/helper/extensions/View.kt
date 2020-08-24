package com.picpay.desafio.android.helper.extensions

import android.app.Activity
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import com.picpay.desafio.android.R
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.list_item_user.view.*


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

fun ImageView.loadImage(url: String, clickListener: () -> Unit) {
    Picasso.get()
        .load(url)
        .error(R.drawable.ic_round_account_circle)
        .into(this, object : Callback {
            override fun onSuccess() {
                clickListener.invoke()
            }

            override fun onError(e: Exception?) {
                clickListener.invoke()
            }
        })
}