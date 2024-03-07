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
                is ViewIntent.UpdateUiChars -> {
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
                    }
                }

                is ViewIntent.OnClickCard -> {
                    setEffect { ViewEffect.ShowToastItem }
                }
            }
        }
    }
}


sealed class ViewIntent : BaseMviViewModel.BaseViewIntent {
    object UpdateUiChars : ViewIntent()
    object OnClickCard : ViewIntent()

    object Update
}

data class ViewState(val items: ViewResource<List<User>> = ViewResource.Loading()) :
    BaseMviViewModel.BaseViewState

sealed class ViewEffect : BaseMviViewModel.BaseViewEffect {
    object ShowToastItem : ViewEffect()
}

