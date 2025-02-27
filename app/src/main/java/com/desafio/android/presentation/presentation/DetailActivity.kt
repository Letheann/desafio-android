package com.desafio.android.presentation.presentation

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import com.desafio.android.core.di.ID
import com.desafio.android.core.theme.SimpleloginTheme
import com.desafio.android.presentation.compose.ExchangeDetail
import org.koin.androidx.viewmodel.ext.android.viewModel


class DetailActivity : AppCompatActivity() {

    private val viewModel: WelcomeViewModel by viewModel<WelcomeViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SimpleloginTheme {
                ExchangeDetail(intent.getStringExtra(ID) ?: "", viewModel)
            }
        }
    }

}

