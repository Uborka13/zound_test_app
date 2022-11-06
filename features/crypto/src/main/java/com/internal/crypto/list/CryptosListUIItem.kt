package com.internal.crypto.list

data class WatchListItem(
    var isLoading: Boolean = false,
    var symbol: String? = null
) {
    companion object {
        fun loading() = WatchListItem(
            isLoading = true
        )

        fun success(symbol: String) = WatchListItem(
            isLoading = false,
            symbol = symbol
        )
    }
}

data class CryptosListUIItem(
    var isLoading: Boolean = false,
    var isError: Boolean = false,
    var errorMsg: String? = null,
    var errorMsgRes: Int? = null
) {
    companion object {
        fun loading() = CryptosListUIItem(
            isLoading = true,
            isError = false,
            errorMsg = null
        )

        fun error(errorMsg: String) = CryptosListUIItem(
            isLoading = false,
            isError = true,
            errorMsg = errorMsg
        )

        fun error(errorMsgRes: Int) = CryptosListUIItem(
            isLoading = false,
            isError = true,
            errorMsgRes = errorMsgRes
        )

        fun success() = CryptosListUIItem(
            isLoading = false,
            isError = false
        )
    }
}
