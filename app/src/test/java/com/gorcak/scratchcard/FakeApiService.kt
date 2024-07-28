package com.gorcak.scratchcard

import com.gorcak.scratchcard.card.data.model.VersionResponse



class FakeApiService : com.gorcak.scratchcard.card.data.ApiService {

    var responseToReturn = invalidResponse


    override suspend fun getVersion(code: String): VersionResponse {
         return responseToReturn
    }

}