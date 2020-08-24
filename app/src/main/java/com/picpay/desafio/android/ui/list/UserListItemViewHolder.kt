package com.picpay.desafio.android.ui.list

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.picpay.desafio.android.helper.extensions.hide
import com.picpay.desafio.android.helper.extensions.loadImage
import com.picpay.desafio.android.helper.extensions.show
import kotlinx.android.synthetic.main.list_item_user.view.*

class UserListItemViewHolder(
    itemView: View
) : RecyclerView.ViewHolder(itemView) {

    fun bind(user: com.example.coredata.models.User) {
        itemView.name.text = user.name
        itemView.username.text = user.username
        itemView.progressBar.show()
        itemView.picture.loadImage(user.img, ::progressGone)
    }

    private fun progressGone(){
        itemView.progressBar.hide()
    }
}

