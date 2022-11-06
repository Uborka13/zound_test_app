package com.internal.crypto.dashboard

import com.internal.crypto.common.CryptosUIItem

data class CurrencyUIItem(
    var isLoading: Boolean = false,
    var isError: Boolean = false
) {
    companion object {
        fun loading() = CurrencyUIItem(
            isLoading = true,
            isError = false
        )

        fun success() = CurrencyUIItem(
            isLoading = false
        )
    }
}

data class DashboardUIItem(
    var isLoading: Boolean = false,
    var isError: Boolean = false,
    var errorMsg: String? = null,
    var errorMsgRes: Int? = null,
    val uiItemList: List<CryptosUIItem> = emptyList()
) {
    companion object {
        fun loading() = DashboardUIItem(
            isLoading = true,
            isError = false,
            errorMsg = null
        )

        fun error(errorMsg: String) = DashboardUIItem(
            isLoading = false,
            isError = true,
            errorMsg = errorMsg
        )

        fun error(errorMsgRes: Int) = DashboardUIItem(
            isLoading = false,
            isError = true,
            errorMsgRes = errorMsgRes
        )

        fun success(uiItemList: List<CryptosUIItem>) = DashboardUIItem(
            isLoading = false,
            uiItemList = uiItemList
        )
    }
}
