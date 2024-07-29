package com.gorcak.scratchcard.card.presentation.scratch

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gorcak.scratchcard.R
import com.gorcak.scratchcard.card.domain.DataResult
import com.gorcak.scratchcard.card.domain.Repository
import com.gorcak.scratchcard.card.domain.ScratchCardValidator
import com.gorcak.scratchcard.card.domain.Storage
import com.gorcak.scratchcard.core.StringValue
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class ScratchViewModel(
    private val storage: Storage,
    private val repository: Repository,
    private val validator: ScratchCardValidator
) : ViewModel() {

    private val eventChannel = Channel<ScratchEvent>()
    val events = eventChannel.receiveAsFlow()

    var state by mutableStateOf(ScratchState())
        private set

    init {
        storage.scratchCardState
            .onEach { newCard ->
                state = state.copy(
                    scratchData = newCard,
                    canScratch = validator.canBeScratched(newCard)
                )
            }
            .launchIn(viewModelScope)
    }

    fun onAction(action: ScratchAction) {
        viewModelScope.launch {
            when (action) {
                ScratchAction.Scratch -> {
                    if(!validator.canBeScratched(state.scratchData)){
                        eventChannel.send(ScratchEvent.Error(StringValue.Resource(R.string.error_scratching)))
                        return@launch
                    }
                    state = state.copy(isScratching = true)
                    val result = repository.scratchCard()
                    state = state.copy(isScratching = false)
                    when (result) {
                        is DataResult.Error -> {
                            eventChannel.send(ScratchEvent.Error(result.error.message))
                        }

                        is DataResult.Success -> {
                            eventChannel.send(ScratchEvent.ScratchSuccess)
                        }
                    }
                }

                ScratchAction.OnBack -> Unit
            }
        }
    }
}