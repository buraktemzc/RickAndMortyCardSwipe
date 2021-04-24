package com.ebt.rickandmortycardswipe.presentation.di

import com.ebt.rickandmortycardswipe.domain.repository.CharacterRepository
import com.ebt.rickandmortycardswipe.domain.usecase.GetCharactersUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class UseCaseModule {

    @Singleton
    @Provides
    fun provideGetCharactersUseCase(characterRepository: CharacterRepository): GetCharactersUseCase {
        return GetCharactersUseCase(characterRepository)
    }
}