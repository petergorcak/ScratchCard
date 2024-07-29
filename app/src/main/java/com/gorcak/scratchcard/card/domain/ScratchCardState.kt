package com.gorcak.scratchcard.card.domain

sealed interface ScratchCardState {

    fun code() : String = throw RuntimeException("Called in wrong card state: [$this]")

    data object Unscratched : ScratchCardState

    data class Scratched(val code: String) : ScratchCardState {
        override fun code() = code
    }

    data class Activated(val code: String) : ScratchCardState {
        override fun code() = code
    }
}
