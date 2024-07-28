package com.gorcak.scratchcard.card.presentation.scratch

import com.gorcak.scratchcard.core.StringValue

sealed interface ScratchEvent {
    data class Error(val message: StringValue) : ScratchEvent
    data object ScratchSuccess : ScratchEvent
}