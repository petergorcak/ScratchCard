package com.gorcak.scratchcard.card.domain

import com.gorcak.scratchcard.card.domain.model.Version
import com.gorcak.scratchcard.core.ACTIVATION_SUCCESS_VALUE

class ActivationValidator {

    fun isActivationValid(version: Version) : Boolean {
        return version.android > ACTIVATION_SUCCESS_VALUE
    }

}