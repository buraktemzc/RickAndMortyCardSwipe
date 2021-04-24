package com.ebt.rickandmortycardswipe.presentation.di

import com.ebt.rickandmortycardswipe.data.repository.CharacterRepositoryImpl
import com.ebt.rickandmortycardswipe.data.repository.datasource.CharacterRemoteDataSource
import com.ebt.rickandmortycardswipe.domain.repository.CharacterRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {

    @Singleton
    @Provides
    fun provideCharacterRepository(characterRemoteDataSource: CharacterRemoteDataSource): CharacterRepository {
        return CharacterRepositoryImpl(characterRemoteDataSource)
    }
}