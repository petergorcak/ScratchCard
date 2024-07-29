package com.gorcak.scratchcard.card.data

import com.gorcak.scratchcard.card.domain.ScratchCardState
import com.gorcak.scratchcard.card.domain.Storage
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow


class MemoryStorage : Storage {
    private val _scratchCardState = MutableStateFlow<ScratchCardState>(ScratchCardState.Unscratched)
    override val scratchCardState = _scratchCardState.asStateFlow()

    override suspend fun setScratched(code: String) {
        _scratchCardState.value = ScratchCardState.Scratched(code)
    }

    override suspend fun setActivated(code: String) { 
        _scratchCardState.value = ScratchCardState.Activated(code)
    }
}