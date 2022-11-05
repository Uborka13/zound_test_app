package com.internal.core.network

import java.io.IOException

sealed class ServerError : IOException() {
    data class HttpApiError(
        val httpStatusCode: Int
    ) : ServerError()

    object NetworkError : ServerError()
}
