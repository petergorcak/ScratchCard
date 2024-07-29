package com.gorcak.scratchcard.card.presentation.scratch

import com.gorcak.scratchcard.card.domain.ScratchCardState

data class ScratchState(
    val isScratching: Boolean = false,
    val canScratch: Boolean = false,
    val scratchData: ScratchCardState = ScratchCardState.Unscratched
)