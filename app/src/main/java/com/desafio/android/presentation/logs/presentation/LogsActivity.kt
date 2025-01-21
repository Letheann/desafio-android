package com.desafio.android.presentation.logs.presentation

import android.os.Bundle
import android.widget.Toast
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.desafio.android.core.theme.SimpleloginTheme
import com.desafio.android.presentation.logs.compose.RecyclerCompose
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class LogsActivity : AppCompatActivity() {

    private val viewModel by viewModel<LogsViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SimpleloginTheme {
                RecyclerCompose(viewModel,
                    invokeClick = {
                        viewModel.intent(ViewIntent.OnClickCard)
                    })
            }
        }
        handleViewEffect()
    }

    private fun handleViewEffect() = lifecycleScope.launch {
        viewModel.viewEffect.collect { effect ->
            when (effect) {
                is ViewEffect.ShowToastItem -> {
                    Toast.makeText(this@LogsActivity, "itemClicked", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}
