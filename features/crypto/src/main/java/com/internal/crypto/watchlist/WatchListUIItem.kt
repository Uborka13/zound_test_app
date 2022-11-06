package com.internal.crypto.watchlist

import com.internal.crypto.common.DetailedCryptosUIItem

data class WatchListUIItem(
    var isLoading: Boolean = false,
    var isError: Boolean = false,
    var errorMsg: String? = null,
    var errorMsgRes: Int? = null,
    val uiItemList: List<DetailedCryptosUIItem> = emptyList()
) {
    companion object {
        fun loading() = WatchListUIItem(
            isLoading = true,
            isError = false,
            errorMsg = null
        )

        fun error(errorMsg: String) = WatchListUIItem(
            isLoading = false,
            isError = true,
            errorMsg = errorMsg
        )

        fun error(errorMsgRes: Int) = WatchListUIItem(
            isLoading = false,
            isError = true,
            errorMsgRes = errorMsgRes
        )

        fun success(uiItemList: List<DetailedCryptosUIItem>) = WatchListUIItem(
            isLoading = false,
            uiItemList = uiItemList
        )
    }
}
