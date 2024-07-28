package com.gorcak.scratchcard.card.domain.model


data class Version(
    val ios: String,
    val iosTM: String,
    val iosRA: String,
    val iosRA2: String,
    val android: Int,
    val androidTM: Int,
    val androidRA: Int,
)