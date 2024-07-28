package com.gorcak.scratchcard.core

import android.content.Context
import androidx.annotation.StringRes

sealed interface StringValue {
    data class Dynamic(val value: String) : StringValue
    class Resource(
        @StringRes val id: Int,
        val args: Array<Any> = arrayOf()
    ) : StringValue

    fun get(context: Context): String {
        return when (this) {
            is Dynamic -> value
            is Resource -> context.getString(id, *args)
        }
    }
}