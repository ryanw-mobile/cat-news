/*
 * Copyright (c) 2024. Ryan Wong (hello@ryanwong.co.uk)
 */

package uk.ryanwong.catnews.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import io.ktor.client.HttpClient
import uk.ryanwong.catnews.data.datasource.remote.FakeNewsListService
import uk.ryanwong.catnews.data.datasource.remote.FakeStoryService
import uk.ryanwong.catnews.data.datasource.remote.interfaces.NewsListService
import uk.ryanwong.catnews.data.datasource.remote.interfaces.StoryService

@Module
@InstallIn(ViewModelComponent::class)
object DataModule {

    @Provides
    @ViewModelScoped
    fun provideNewsListService(httpClient: HttpClient): NewsListService {
        return FakeNewsListService()
    }

    @Provides
    @ViewModelScoped
    fun provideStoryService(httpClient: HttpClient): StoryService {
        return FakeStoryService()
    }
}
