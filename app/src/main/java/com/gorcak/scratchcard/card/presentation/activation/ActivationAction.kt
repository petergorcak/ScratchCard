package com.gorcak.scratchcard.card.presentation.activation

sealed interface ActivationAction {
    data object OnBack : ActivationAction
    data object Activate : ActivationAction
}