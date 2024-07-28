package com.gorcak.scratchcard.card.data

import com.gorcak.scratchcard.card.data.model.VersionResponse
import com.gorcak.scratchcard.card.domain.model.Version


fun VersionResponse.toVersion() = Version(
    ios = ios,
    iosTM = iosTM,
    iosRA = iosRA,
    iosRA2 = iosRA_2,
    android = android,
    androidTM = androidTM,
    androidRA = androidRA
)