package com.desafio.android.presentation.login.presentation

import android.os.Bundle
import android.view.View
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import com.google.android.material.snackbar.Snackbar
import com.desafio.android.core.theme.SimpleloginTheme
import com.desafio.android.presentation.login.compose.LoginForm
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel


class LoginActivity : AppCompatActivity() {

    private val viewModel: LoginViewModel by viewModel<LoginViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SimpleloginTheme {
                LoginForm(viewModel) { credentials ->
                    viewModel.intent(ViewIntent.DoLogin(credentials))
                }
            }
        }
        handleViewEffect()
    }

    private fun handleViewEffect() = lifecycleScope.launch {
        viewModel.viewEffect.collect { effect ->
            when (effect) {
                ViewEffect.WrongPasswordFeedback -> {
                    val rootView: View = findViewById(android.R.id.content)
                    Snackbar.make(
                        rootView,
                        "Usuário e senha incorretos",
                        Snackbar.LENGTH_SHORT
                    ).apply {
                        setBackgroundTint(
                            ContextCompat.getColor(
                                this@LoginActivity,
                                android.R.color.holo_red_light
                            )
                        )
                        setTextColor(
                            ContextCompat.getColor(
                                this@LoginActivity,
                                android.R.color.white
                            )
                        )
                    }.show()
                }

                else -> {}
            }
        }
    }
}

