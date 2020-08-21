package com.picpay.desafio.android.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.coredata.models.User
import com.example.coredata.repository.usecases.GetUsers
import com.picpay.desafio.android.helper.extensions.add
import com.picpay.desafio.android.models.ErrorRequest
import com.picpay.desafio.android.models.SuccessGetUsers
import com.picpay.desafio.android.models.ViewEvents
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class MainActivityViewModel(private val users: GetUsers) : BaseViewModel() {

    private val state = MutableLiveData<ViewEvents<List<User>>>()
    fun viewState(): LiveData<ViewEvents<List<User>>> = state

    fun getUsers() {
        jobs add launch {
            users.execute().collect {
                if (it != null) {
                    state.postValue(SuccessGetUsers(it))
                } else {
                    state.postValue(ErrorRequest)
                }
            }
        }
    }

}