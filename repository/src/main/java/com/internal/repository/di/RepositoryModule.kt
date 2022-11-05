package com.internal.repository.di

import com.internal.data.remote.service.CryptoService
import com.internal.data.remote.service.RatesService
import com.internal.repository.Repository
import com.internal.repository.RepositoryImpl
import com.internal.repository.mapper.RepositoryMapper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun providesRepository(
        cryptoService: CryptoService,
        ratesService: RatesService,
        mapper: RepositoryMapper
    ): Repository {
        return RepositoryImpl(cryptoService, ratesService, mapper)
    }

    @Provides
    fun providesMapper() = RepositoryMapper()
}
