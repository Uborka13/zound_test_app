package com.internal.core.network

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.internal.core.BuildConfig
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.CoroutineStart
import kotlinx.coroutines.launch

open class ServerErrorViewModel : ViewModel() {

    protected fun launch(
        start: CoroutineStart = CoroutineStart.DEFAULT,
        apiErrorHandler: ((ServerError.HttpApiError) -> Unit)? = null,
        networkErrorHandler: ((ServerError.NetworkError) -> Unit)? = null,
        block: suspend CoroutineScope.() -> Unit
    ) = viewModelScope.launch(
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
