package com.desafio.android.core.di

import org.koin.core.qualifier.Qualifier
import org.koin.core.qualifier.QualifierValue
object QualifierMasterKey : Qualifier {
    override val value: QualifierValue
        get() = "QualifierMasterKey"
}

object QualifierEncryptedSharedPreferences : Qualifier {
    override val value: QualifierValue
        get() = "QualifierEncryptedSharedPreferences"
}