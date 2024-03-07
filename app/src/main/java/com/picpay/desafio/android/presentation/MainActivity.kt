package com.picpay.desafio.android.presentation

import android.os.Bundle
import android.widget.Toast
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleRegistry
import androidx.lifecycle.lifecycleScope
import com.picpay.desafio.android.presentation.compose.RecyclerCompose
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {

    private val viewModel by viewModel<MainActivityViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            RecyclerCompose(viewModel,
                invokeClick = {
                    viewModel.intent(ViewIntent.OnClickCard)
                },
                refreshItems = {
                    viewModel.intent(ViewIntent.UpdateUiUsersByCache)
                })
        }
        handleViewEffect()
        LifecycleRegistry(this).markState(Lifecycle.State.CREATED)
    }

    private fun handleViewEffect() = lifecycleScope.launch {
        viewModel.viewEffect.collect { effect ->
            when (effect) {
                is ViewEffect.ShowToastItem -> {
                    Toast.makeText(this@MainActivity, "itemClicked", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}
