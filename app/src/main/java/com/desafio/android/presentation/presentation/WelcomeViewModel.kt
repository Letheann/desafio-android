package com.desafio.android.presentation.presentation

import androidx.lifecycle.viewModelScope
import com.desafio.android.core.presentation.BaseMviViewModel
import com.desafio.android.core.presentation.ViewResource
import com.example.shared.commonMain.data.dto.HPCharacter
import com.example.shared.commonMain.usecase.CharacterUseCase
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

class WelcomeViewModel(private val useCase: CharacterUseCase) :
    BaseMviViewModel<ViewIntent, ViewState, ViewEffect>() {
    override fun initialState(): ViewState = ViewState()

    override fun intent(intent: ViewIntent) {
        viewModelScope.launch {
            when (intent) {
                is ViewIntent.GetHPCharaters -> {
                    useCase.getCharacters().onStart {
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
                    }
                }

                is ViewIntent.OnClickCard -> {
                    setEffect { ViewEffect.EffectToView }
                }
            }
        }
    }
}


sealed class ViewIntent : BaseMviViewModel.BaseViewIntent {
    data object GetHPCharaters : ViewIntent()
    data class OnClickCard(val id: String) : ViewIntent()
}

data class ViewState(
    val items: ViewResource<List<HPCharacter>>? = null
) : BaseMviViewModel.BaseViewState

sealed class ViewEffect : BaseMviViewModel.BaseViewEffect {
    data object EffectToView : ViewEffect()
}

