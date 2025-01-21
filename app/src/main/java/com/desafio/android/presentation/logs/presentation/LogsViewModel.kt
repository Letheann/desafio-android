package com.desafio.android.presentation.logs.presentation

import androidx.lifecycle.viewModelScope
import com.desafio.android.core.presentation.BaseMviViewModel
import com.desafio.android.core.presentation.ViewResource
import com.desafio.android.data.model.Logs
import com.desafio.android.domain.user.LogsUseCase
import kotlinx.coroutines.launch

class LogsViewModel(private val useCase: LogsUseCase) :
    BaseMviViewModel<ViewIntent, ViewState, ViewEffect>() {

    override fun initialState(): ViewState = ViewState()

    override fun intent(intent: ViewIntent) {
        viewModelScope.launch {
            when (intent) {
                is ViewIntent.UpdateUi -> {
                    useCase.getLogsByCache().collect {
                        setState {
                            copy(
                                items = ViewResource.Success(
                                    data = it
                                )
                            )
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
    object UpdateUi : ViewIntent()
    object OnClickCard : ViewIntent()
}

data class ViewState(val items: ViewResource<List<Logs>>? = null) :
    BaseMviViewModel.BaseViewState

sealed class ViewEffect :
    BaseMviViewModel.BaseViewEffect {
    object ShowToastItem : ViewEffect()
}

