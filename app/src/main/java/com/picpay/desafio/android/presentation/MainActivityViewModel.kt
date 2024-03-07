package com.picpay.desafio.android.presentation

import androidx.lifecycle.viewModelScope
import com.picpay.desafio.android.core.presentation.BaseMviViewModel
import com.picpay.desafio.android.core.presentation.ViewResource
import com.picpay.desafio.android.data.model.User
import com.picpay.desafio.android.domain.PicPayUseCase
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onEmpty
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

class MainActivityViewModel(private val useCase: PicPayUseCase) :
    BaseMviViewModel<ViewIntent, ViewState, ViewEffect>() {

    override fun initialState(): ViewState = ViewState()

    override fun intent(intent: ViewIntent) {
        viewModelScope.launch {
            when (intent) {
                is ViewIntent.UpdateUiUsers -> {
                    useCase.getUsers().onStart {
                        setState {
                            copy(items = ViewResource.Loading())
                        }
                    }.catch {
                        setState {
                            copy(items = ViewResource.Error())
                        }
                    }.onEmpty {
                        setState {
                            copy(items = ViewResource.Empty())
                        }
                    }.collect {
                        setState {
                            copy(items = ViewResource.Success(data = it))
                        }
                        useCase.saveUsers(it)
                    }
                }

                is ViewIntent.OnClickCard -> {
                    setEffect { ViewEffect.ShowToastItem }
                }

                is ViewIntent.UpdateUiUsersByCache -> {
                    useCase.getUsersByCache().catch {
                        intent(ViewIntent.UpdateUiUsers)
                    }.onEmpty {
                        intent(ViewIntent.UpdateUiUsers)
                    }.collect {
                        setState {
                            copy(items = ViewResource.Success(data = it))
                        }
                    }
                }
            }
        }
    }
}


sealed class ViewIntent : BaseMviViewModel.BaseViewIntent {
    object UpdateUiUsers : ViewIntent()
    object UpdateUiUsersByCache : ViewIntent()
    object OnClickCard : ViewIntent()
}

data class ViewState(val items: ViewResource<List<User>>? = null) :
    BaseMviViewModel.BaseViewState

sealed class ViewEffect : BaseMviViewModel.BaseViewEffect {
    object ShowToastItem : ViewEffect()
}

