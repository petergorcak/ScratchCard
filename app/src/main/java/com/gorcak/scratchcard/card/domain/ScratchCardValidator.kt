package com.gorcak.scratchcard.card.domain

import com.gorcak.scratchcard.card.domain.model.Version
import com.gorcak.scratchcard.core.ACTIVATION_SUCCESS_VALUE

class ScratchCardValidator {

    fun isActivationValid(version: Version) : Boolean {
        return version.android > ACTIVATION_SUCCESS_VALUE
    }

    fun canBeScratched(card: ScratchCardState) : Boolean {
        return when (card) {
            ScratchCardState.Unscratched -> true
            else -> false
        }
    }

    fun canBeActivated(card: ScratchCardState) : Boolean {
        return when (card) {
            is ScratchCardState.Scratched -> true
            else -> false
        }
    }

}