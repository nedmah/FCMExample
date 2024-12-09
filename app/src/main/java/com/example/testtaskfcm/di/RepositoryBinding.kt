package com.example.testtaskfcm.di

import com.example.testtaskfcm.data.local.repository.SharedPrefsTokenRepository
import com.example.testtaskfcm.domain.repository.TokenRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface RepositoryBinding {


    @Singleton
    @Binds
    fun bindTokenRepository(tokenRepository: SharedPrefsTokenRepository) : TokenRepository
}

@EntryPoint
@InstallIn(SingletonComponent::class)
interface ServiceEntryPoint {
    fun getTokenRepository(): TokenRepository
}