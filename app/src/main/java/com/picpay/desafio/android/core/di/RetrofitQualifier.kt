package com.picpay.desafio.android.core.di

import org.koin.core.qualifier.Qualifier
import org.koin.core.qualifier.QualifierValue

object QualifierRetrofit : Qualifier {
    override val value: QualifierValue
        get() = "QualifierMarvelRetrofit"
}

object QualifierPicPayApi : Qualifier {
    override val value: QualifierValue
        get() = "QualifierMarvelApi"
}