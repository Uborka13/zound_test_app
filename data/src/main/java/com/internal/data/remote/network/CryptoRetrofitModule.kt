package com.internal.data.remote.network

import com.internal.data.remote.service.CryptoService
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import javax.inject.Named

@Module
@InstallIn(SingletonComponent::class)
object CryptoRetrofitModule {

    @OptIn(ExperimentalSerializationApi::class)
    @Provides
    @Named("CryptoRetrofit")
    fun providesRetrofit(
        okHttpClient: OkHttpClient,
        json: Json
    ): Retrofit {
        val contentType = "application/json".toMediaType()
        return Retrofit.Builder()
            .baseUrl("https://api.wazirx.com")
            .client(okHttpClient)
            .addConverterFactory(json.asConverterFactory(contentType))
            .build()
    }

    @Provides
    fun providesCryptoService(@Named("CryptoRetrofit") retrofit: Retrofit): CryptoService {
        return retrofit.create(CryptoService::class.java)
    }
}
