package com.internal.data.remote.service

import com.internal.data.remote.model.crypto.CryptoResponseItem
import retrofit2.http.GET

interface CryptoService {
    @GET("/sapi/v1/tickers/24hr")
    suspend fun getCryptos(): List<CryptoResponseItem>
}
