package com.picpay.desafio.android.presentation

import android.os.Bundle
import android.os.PersistableBundle
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import br.com.desafio.androidapp.R
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.picpay.desafio.android.presentation.compose.RecyclerCompose
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import org.koin.androidx.viewmodel.ext.android.viewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

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
                    viewModel.intent(ViewIntent.UpdateUiChars)
                })
        }
        handleViewEffect()
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
