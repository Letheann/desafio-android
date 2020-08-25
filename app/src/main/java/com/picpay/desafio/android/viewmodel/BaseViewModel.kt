package com.picpay.desafio.android.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.Job

abstract class BaseViewModel : ViewModel() {

    val jobs = ArrayList<Job>()

    override fun onCleared() {
        super.onCleared()
        jobs.forEach {
            if (!it.isCancelled) it.cancel()
        }
    }
}