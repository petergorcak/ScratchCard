package com.gorcak.scratchcard.card.presentation

import com.gorcak.scratchcard.core.StringValue


sealed interface MainEvent {
    data object ActivationSuccess : MainEvent
    data class Error(val message: StringValue) : MainEvent

}