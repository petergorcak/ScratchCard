package com.gorcak.scratchcard.card.presentation.activation

sealed interface ActivationScreenAction {
    data object OnBack : ActivationScreenAction
    data object Activate : ActivationScreenAction
}