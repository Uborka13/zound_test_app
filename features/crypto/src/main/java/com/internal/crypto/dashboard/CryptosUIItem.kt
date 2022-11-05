package com.internal.crypto.dashboard

data class CryptosUIItem(
    val name: String,
    val quote: String,
    val lastPrice: String,
    val volume: String,
    val percentage: Float
)

data class DashboardUIItem(
    var isLoading: Boolean = false,
    var isError: Boolean = false,
    var errorMsg: String = "",
    val uiItemList: List<CryptosUIItem> = emptyList()
) {
    companion object {
        fun loading() = DashboardUIItem(
            isLoading = true,
            isError = false,
            errorMsg = ""
        )

        fun error(errorMsg: String) = DashboardUIItem(
            isLoading = false,
            isError = true,
            errorMsg = errorMsg
        )

        fun success(uiItemList: List<CryptosUIItem>) = DashboardUIItem(
            isLoading = false,
            uiItemList = uiItemList
        )
    }
}
