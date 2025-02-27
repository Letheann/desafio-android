package com.desafio.android.presentation.presentation

import android.content.Intent
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleRegistry
import androidx.lifecycle.lifecycleScope
import com.desafio.android.core.di.ID
import com.desafio.android.core.theme.SimpleloginTheme
import com.desafio.android.presentation.compose.RecyclerCompose
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel


class WelcomeActivity : AppCompatActivity() {

    private val viewModel: WelcomeViewModel by viewModel<WelcomeViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SimpleloginTheme {
                RecyclerCompose(viewModel,
                    invokeClick = { id ->
                        viewModel.intent(ViewIntent.OnClickCard(id))
                    })
            }
        }
        LifecycleRegistry(this).currentState = Lifecycle.State.CREATED
        handleViewEffect()
    }

    private fun handleViewEffect() = lifecycleScope.launch {
        viewModel.viewEffect.collect { effect ->
            when (effect) {
                is ViewEffect.OpenExchangeDetails -> {
                    val intent = Intent(this@WelcomeActivity, DetailActivity::class.java).apply {
                        putExtra(ID, effect.id)
                    }
                    startActivity(intent)
                }
            }
        }
    }
}

