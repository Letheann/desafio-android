package com.desafio.android.core.di

import org.koin.core.qualifier.Qualifier
import org.koin.core.qualifier.QualifierValue

object QualifierRetrofit : Qualifier {
    override val value: QualifierValue
        get() = "QualifierMarvelRetrofit"
}

object QualifierApi : Qualifier {
    override val value: QualifierValue
        get() = "QualifierApi"
}