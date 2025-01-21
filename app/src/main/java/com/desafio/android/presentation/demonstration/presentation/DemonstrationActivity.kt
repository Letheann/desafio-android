package com.desafio.android.presentation.demonstration.presentation

import android.content.Intent
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.desafio.android.core.theme.SimpleloginTheme
import com.desafio.android.presentation.demonstration.compose.VerticalAlignedComponent
import com.desafio.android.presentation.login.presentation.LoginViewModel
import com.desafio.android.presentation.login.presentation.ViewEffect
import com.desafio.android.presentation.login.presentation.ViewIntent
import com.desafio.android.presentation.logs.presentation.LogsActivity
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class DemonstrationActivity : AppCompatActivity() {

    private val viewModel: LoginViewModel by viewModel<LoginViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SimpleloginTheme {
                VerticalAlignedComponent(viewModel, intent.getStringExtra(com.desafio.android.core.di.ENCRYPTED)) {
                    viewModel.intent(ViewIntent.NavigateToLogs)
                }
            }
        }
        handleViewEffect()
    }

    private fun handleViewEffect() = lifecycleScope.launch {
        viewModel.viewEffect.collect { effect ->
            when (effect) {
                ViewEffect.OpenUserList -> {
                    startActivity(Intent(this@DemonstrationActivity, LogsActivity::class.java))
                }

                else -> {}
            }
        }
    }
}