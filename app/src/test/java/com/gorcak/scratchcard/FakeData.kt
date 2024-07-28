package com.gorcak.scratchcard

import com.gorcak.scratchcard.card.data.model.VersionResponse


val validResponse = VersionResponse(
    ios = "123",
    iosTM = "123",
    iosRA = "123",
    iosRA_2 =   "123",
    android = 277029,
    androidTM = 277029,
    androidRA = 277029
)


val invalidResponse = VersionResponse(
    ios = "123",
    iosTM = "123",
    iosRA = "123",
    iosRA_2 =   "123",
    android = 277028,
    androidTM = 277028,
    androidRA = 277028
)