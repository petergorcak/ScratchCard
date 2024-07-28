package com.gorcak.scratchcard.card.presentation.scratch

sealed interface ScratchAction {
    data object OnBack : ScratchAction
    data object Scratch : ScratchAction
}