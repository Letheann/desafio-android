package com.picpay.desafio.android.data.mapper

import com.picpay.desafio.android.data.model.User
import com.picpay.desafio.android.data.model.UserDto
import com.picpay.desafio.android.core.repository.BaseMapper

object UserDtoMapper : BaseMapper<User, UserDto>() {
    override fun transformTo(source: User): UserDto =
        UserDto(
            id = source.id,
            username = source.username,
            name = source.name,
            img = source.img
        )
}
