package com.picpay.desafio.android.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.coredata.models.User
import com.example.coredata.models.request.ViewEvents
import com.example.coredata.repository.usecases.users.IGetUsers
import com.picpay.desafio.android.helper.extensions.add
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class MainActivityViewModel(private val users: IGetUsers) : BaseViewModel() {

    private val state = MutableLiveData<ViewEvents<List<User>>>()
    fun viewState(): LiveData<ViewEvents<List<User>>> = state

    init {
        getUsers()
    }

    private fun getUsers() {
        jobs add viewModelScope.launch {
            users.execute().collect {
                state.postValue(it)
            }
        }
    }


}