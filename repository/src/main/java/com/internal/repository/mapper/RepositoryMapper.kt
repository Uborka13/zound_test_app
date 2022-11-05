package com.internal.repository.mapper

import com.internal.data.remote.model.crypto.CryptoResponseItem
import com.internal.repository.model.crypto.CryptoRepoModel

class RepositoryMapper {

    fun mapToRepoModel(response: CryptoResponseItem): CryptoRepoModel {
        return CryptoRepoModel(
            askPrice = response.askPrice,
            at = response.at,
            baseAsset = response.baseAsset,
            bidPrice = response.bidPrice,
            highPrice = response.highPrice,
            lastPrice = response.lastPrice,
            lowPrice = response.lowPrice,
            openPrice = response.openPrice,
            quoteAsset = response.quoteAsset,
            symbol = response.symbol,
            volume = response.volume,
            percentage = calculatePercentage(response.lastPrice, response.openPrice)
        )
    }

    private fun calculatePercentage(lastPrice: String, openPrice: String): Float {
        return (lastPrice.toFloat() - openPrice.toFloat()) / openPrice.toFloat() * 100
    }
}
