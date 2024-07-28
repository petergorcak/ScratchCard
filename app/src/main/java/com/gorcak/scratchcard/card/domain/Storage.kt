package com.gorcak.scratchcard.card.domain

import kotlinx.coroutines.flow.Flow

interface Storage {
    val scratchCardState: Flow<ScratchCardState>

    suspend fun setScratched(code: String)

    suspend fun setActivated(code: String)

}

