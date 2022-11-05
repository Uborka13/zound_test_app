package com.internal.core.network.ui

import com.internal.core.network.ServerError
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.CoroutineStart
import kotlinx.coroutines.Job

interface ServerErrorHandler {
    fun launch(
        scope: CoroutineScope,
        start: CoroutineStart = CoroutineStart.DEFAULT,
        apiErrorHandler: ((ServerError.HttpApiError) -> Unit)? = null,
        networkErrorHandler: ((ServerError.NetworkError) -> Unit)? = null,
        block: suspend CoroutineScope.() -> Unit
    ): Job
}
