package com.gorcak.scratchcard.card.data

import com.gorcak.scratchcard.card.data.model.VersionResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("version")
    suspend fun getVersion(@Query("sort") code : String) : VersionResponse
}