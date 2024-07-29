package com.gorcak.scratchcard.card.presentation

sealed interface MainAction {
    data object Activate : MainAction
}