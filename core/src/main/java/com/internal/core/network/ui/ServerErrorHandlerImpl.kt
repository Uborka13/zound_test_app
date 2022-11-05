package com.internal.core.network.ui

import com.internal.core.BuildConfig
import com.internal.core.network.ServerError
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.CoroutineStart
import kotlinx.coroutines.launch

class ServerErrorHandlerImpl : ServerErrorHandler {

    override fun launch(
        scope: CoroutineScope,
        start: CoroutineStart,
        apiErrorHandler: ((ServerError.HttpApiError) -> Unit)?,
        networkErrorHandler: ((ServerError.NetworkError) -> Unit)?,
        block: suspend CoroutineScope.() -> Unit
    ) = scope.launch(
        defaultExceptionHandler(apiErrorHandler, networkErrorHandler),
        start,
        block
    )

    private val defaultExceptionHandler: (
        apiErrorHandler: ((ServerError.HttpApiError) -> Unit)?,
        networkErrorHandler: ((ServerError.NetworkError) -> Unit)?
    ) -> CoroutineExceptionHandler =
        { apiErrorHandler, networkErrorHandler ->
            CoroutineExceptionHandler { _, error ->
                when (error) {
                    is ServerError.HttpApiError -> {
                        apiErrorHandler?.let { handler ->
                            handler(error)
                        }
                    }
                    is ServerError.NetworkError -> {
                        networkErrorHandler?.let { handler ->
                            handler(error)
                        }
                    }
                    else -> {
                        if (BuildConfig.DEBUG) {
                            error.printStackTrace()
                        }
                    }
                }
            }
        }
}
