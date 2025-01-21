package com.desafio.android.presentation.login.presentation

import androidx.lifecycle.viewModelScope
import com.desafio.android.core.presentation.BaseMviViewModel
import com.desafio.android.core.presentation.ViewResource
import com.desafio.android.data.model.SuccessObject
import com.desafio.android.domain.login.LoginUseCase
import com.desafio.android.presentation.login.compose.Credentials
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class LoginViewModel(private val useCase: LoginUseCase) :
    BaseMviViewModel<ViewIntent, ViewState, ViewEffect>() {
    override fun initialState(): ViewState = ViewState()

    override fun intent(intent: ViewIntent) {
        viewModelScope.launch {
            when (intent) {
                is ViewIntent.DoLogin -> {
                    if (!intent.credentials.wrongPassword) {
                        useCase.encrypt(intent.credentials).catch {
                            it.toString()
                        }.collect {
                            setState {
                                copy(item = ViewResource.Success(SuccessObject(byteArray = it)))
                            }
                        }
                    } else {
                        setEffect { ViewEffect.WrongPasswordFeedback }
                    }
                }

                is ViewIntent.DoDecrypt -> {
                    useCase.decrypt().catch {
                        it.toString()
                    }.collect {
                        setState {
                            copy(item = ViewResource.Success(SuccessObject(decryptedString = it)))
                        }
                    }
                }

                is ViewIntent.NavigateToLogs -> {
                    setEffect { ViewEffect.OpenUserList }
                }
            }
        }
    }
}


sealed class ViewIntent : BaseMviViewModel.BaseViewIntent {
    class DoLogin(val credentials: Credentials) : ViewIntent()
    data object DoDecrypt : ViewIntent()
    data object NavigateToLogs : ViewIntent()
}

data class ViewState(val item: ViewResource<SuccessObject>? = null) : BaseMviViewModel.BaseViewState

sealed class ViewEffect :BaseMviViewModel.BaseViewEffect {
    data object WrongPasswordFeedback : ViewEffect()
    data object OpenUserList : ViewEffect()


}

