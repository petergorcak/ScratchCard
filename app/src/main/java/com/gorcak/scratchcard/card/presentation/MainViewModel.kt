package com.gorcak.scratchcard.card.presentation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gorcak.scratchcard.R
import com.gorcak.scratchcard.card.domain.DataResult
import com.gorcak.scratchcard.card.domain.Repository
import com.gorcak.scratchcard.card.domain.ScratchCardState
import com.gorcak.scratchcard.card.domain.Storage
import com.gorcak.scratchcard.core.StringValue
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class MainViewModel(
    private val storage: Storage,
    private val repository: Repository
) : ViewModel() {

    private val eventChannel = Channel<MainEvent>()
    val events = eventChannel.receiveAsFlow()

    var state by mutableStateOf(MainState())
        private set



    init {
        storage.scratchCardState
            .onEach { newCard ->
                state = state.copy(
                    scratchCardState = newCard
                )
            }
            .launchIn(viewModelScope)
    }


    fun activateCard() {
        if(state.isActivating){
            return
        }
        viewModelScope.launch {
            when (val card = state.scratchCardState) {
                is ScratchCardState.Activated -> {
                    eventChannel.send(MainEvent.Error(StringValue.Resource(R.string.error_already_activated)))
                }
                is ScratchCardState.Scratched -> {
                    state = state.copy(isActivating = true)
                    val result = repository.activateCard(card.code)
                    state = state.copy(isActivating = false)
                    when (result) {
                        is DataResult.Error -> {
                            eventChannel.send(MainEvent.Error(result.error.message))
                        }

                        is DataResult.Success -> {
                            eventChannel.send(MainEvent.ActivationSuccess)
                        }
                    }
                }
                ScratchCardState.Unscratched -> {
                    eventChannel.send(MainEvent.Error(StringValue.Resource(R.string.error_need_scratch)))
                }
            }
        }
    }


}