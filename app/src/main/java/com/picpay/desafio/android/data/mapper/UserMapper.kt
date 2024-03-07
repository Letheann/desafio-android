package com.picpay.desafio.android.data.mapper

import com.picpay.desafio.android.data.model.User
import com.picpay.desafio.android.data.model.UserDto
import com.picpay.desafio.android.core.repository.BaseMapper

object UserMapper : BaseMapper<UserDto, User>() {
    override fun transformTo(source: UserDto): User =
        User(
            id = source.id ?: 0,
            username = source.username ?: "",
            name = source.name ?: "",
            img = source.img ?: ""
        )
}
