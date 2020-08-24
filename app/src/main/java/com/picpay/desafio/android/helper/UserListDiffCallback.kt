package com.picpay.desafio.android.helper

import androidx.recyclerview.widget.DiffUtil

class UserListDiffCallback(
    private val oldList: List<com.example.coredata.models.User>,
    private val newList: List<com.example.coredata.models.User>
) : DiffUtil.Callback() {

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].username == newList[newItemPosition].username
    }

    override fun getOldListSize(): Int {
        return oldList.size
    }

    override fun getNewListSize(): Int {
        return newList.size
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return true
    }
}