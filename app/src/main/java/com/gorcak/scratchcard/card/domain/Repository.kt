package com.gorcak.scratchcard.card.domain

interface Repository {
    suspend fun scratchCard(): DataResult<Unit>
    suspend fun activateCard(code: String): DataResult<Unit>
}
