package com.desafio.android.presentation.presentation

import androidx.lifecycle.viewModelScope
import com.desafio.android.core.presentation.BaseMviViewModel
import com.desafio.android.core.presentation.ViewResource
import com.desafio.android.data.model.Exchange
import com.desafio.android.domain.ExchangeUseCase
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

class WelcomeViewModel(private val useCase: ExchangeUseCase) :
    BaseMviViewModel<ViewIntent, ViewState, ViewEffect>() {
    override fun initialState(): ViewState = ViewState()

    override fun intent(intent: ViewIntent) {
        viewModelScope.launch {
            when (intent) {
                is ViewIntent.GetExchange -> {
                    useCase.getExchanges().onStart {
                        setState {
                            copy(items = ViewResource.Loading())
                        }
                    }.catch {
                        setState {
                            copy(items = ViewResource.Error())
                        }
                    }.collect {
                        setState {
                            copy(items = ViewResource.Success(data = it))
                        }
                        useCase.saveExchanges(it)
                    }
                }

                is ViewIntent.OnClickCard -> {
                    setEffect { ViewEffect.OpenExchangeDetails(intent.id) }
                }

                is ViewIntent.GetExchangeById -> {
                    useCase.getExchangesByCacheAndId(intent.exchangeId).catch {
                        setState {
                            copy(items = ViewResource.Error())
                        }
                    }.collect {
                        setState {
                            copy(items = ViewResource.Success(data = listOf(it)))
                        }
                    }
                }
            }
        }
    }
}


sealed class ViewIntent : BaseMviViewModel.BaseViewIntent {
    data object GetExchange : ViewIntent()
    data class OnClickCard(val id: String) : ViewIntent()
    data class GetExchangeById(val exchangeId: String) : ViewIntent()
}

data class ViewState(
    val items: ViewResource<List<Exchange>>? = null
) : BaseMviViewModel.BaseViewState

sealed class ViewEffect : BaseMviViewModel.BaseViewEffect {
    data class OpenExchangeDetails(val id: String) : ViewEffect()
}

