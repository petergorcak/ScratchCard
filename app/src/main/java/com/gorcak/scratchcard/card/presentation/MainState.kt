package com.gorcak.scratchcard.card.presentation

import com.gorcak.scratchcard.card.domain.ScratchCardState

data class MainState(
    val scratchCardState: ScratchCardState = ScratchCardState.Unscratched,
    val isActivating: Boolean = false,
    val canBeActivated: Boolean = false,
)
