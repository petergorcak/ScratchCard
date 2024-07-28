package com.gorcak.scratchcard.card.domain

import com.gorcak.scratchcard.core.StringValue


data class ErrorData(val message: StringValue, val exception: Exception? = null)
sealed interface DataResult<out D> {
    data class Success<out D>(val data: D) : DataResult<D>
    data class Error(val error: ErrorData) : DataResult<Nothing>
}