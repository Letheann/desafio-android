package com.picpay.desafio.android.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.picpay.desafio.android.helper.extensions.add
import com.picpay.desafio.android.models.events.ViewEvents
import com.picpay.desafio.android.repository.usecases.GetUsers
import kotlinx.coroutines.launch

class MainActivityViewModel(private val users: GetUsers) : BaseViewModel() {

    private val state = MutableLiveData<ViewEvents>()
    fun viewState(): LiveData<ViewEvents> = state

    fun getUsers() {
        jobs add launch {
            val response = users.execute()
            if (response != null) {
                state.postValue(ViewEvents.SuccessGetUsers(response))
            } else {
                state.postValue(ViewEvents.ErrorRequest)
            }
        }
    }

}