package com.gorcak.scratchcard.card.data.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class VersionResponse(
    @Json(name = "ios") val ios: String,
    @Json(name = "iosTM") val iosTM: String,
    @Json(name = "iosRA") val iosRA: String,
    @Json(name = "iosRA_2") val iosRA_2: String,
    @Json(name = "android") val android: Int,
    @Json(name = "androidTM") val androidTM: Int,
    @Json(name = "androidRA") val androidRA: Int,
)