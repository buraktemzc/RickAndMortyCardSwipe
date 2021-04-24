package com.ebt.rickandmortycardswipe.presentation.di

import com.ebt.rickandmortycardswipe.data.api.ApiService
import com.ebt.rickandmortycardswipe.data.repository.datasource.CharacterRemoteDataSource
import com.ebt.rickandmortycardswipe.data.repository.datasourceimpl.CharacterRemoteDataSourceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RemoteDataModule {

    @Singleton
    @Provides
    fun provideCharacterRemoteDataSource(
        apiService: ApiService
    ): CharacterRemoteDataSource {
        return CharacterRemoteDataSourceImpl(apiService)
    }
}