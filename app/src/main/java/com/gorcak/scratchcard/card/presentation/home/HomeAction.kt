package com.gorcak.scratchcard.card.presentation.home

sealed interface HomeAction {
    data object OnScratch : HomeAction
    data object OnActivate : HomeAction
}