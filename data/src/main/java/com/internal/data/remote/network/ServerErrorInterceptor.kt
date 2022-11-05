package com.internal.data.remote.network

import com.internal.core.network.ServerError
import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException

class ServerErrorInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val response = try {
            chain.proceed(request)
        } catch (e: IOException) {
            throw ServerError.NetworkError
        }
        if (response.isSuccessful) {
            val source = response.body?.source()
            source?.request(Long.MAX_VALUE)
            return response
        }

        throw ServerError.HttpApiError(
            response.code
        )
    }
}
