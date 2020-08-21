package com.picpay.desafio.android.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlin.coroutines.CoroutineContext

abstract class BaseViewModel : ViewModel(), CoroutineScope {

    override val coroutineContext: CoroutineContext = Dispatchers.Main
    protected val jobs = ArrayList<Job>()

    override fun onCleared() {
        super.onCleared()
        jobs.forEach {
            if (!it.isCancelled) it.cancel()
        }
    }
}