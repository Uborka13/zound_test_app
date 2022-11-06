package com.internal.data.remote.service

import com.internal.data.remote.model.rates.ConvertResponse
import com.internal.data.remote.model.rates.Rates
import retrofit2.http.GET
import retrofit2.http.Query

interface RatesService {

    @GET("/convert")
    suspend fun convertRates(
        @Query("from") from: Rates,
        @Query("to") to: Rates
    ): ConvertResponse
}
