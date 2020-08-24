package com.picpay.desafio.android.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.coredata.models.User
import com.example.coredata.models.request.ErrorRequest
import com.example.coredata.models.request.SuccessRequest
import com.example.coredata.models.request.ViewEvents
import com.example.coredata.repository.usecases.users.IGetUsers
import com.picpay.desafio.android.helper.extensions.add
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class MainActivityViewModel(private val users: IGetUsers) : BaseViewModel() {

    private val state = MutableLiveData<ViewEvents<List<User>>>()
    fun viewState(): LiveData<ViewEvents<List<User>>> = state

    fun getUsers() {
        jobs add launch {
            users.execute().collect {
                if (it != null) {
                    state.postValue(SuccessRequest(it))
                } else {
                    state.postValue(ErrorRequest)
                }
            }
        }
    }

}