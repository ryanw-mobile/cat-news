/*
 * Copyright (c) 2024. Ryan Wong (hello@ryanwong.co.uk)
 */

package uk.ryanwong.catnews.storydetail.data.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import io.ktor.client.HttpClient
import uk.ryanwong.catnews.storydetail.data.remote.MockStoryService
import uk.ryanwong.catnews.storydetail.data.remote.StoryService

@Module
@InstallIn(ViewModelComponent::class)
object DataModule {

    @Provides
    @ViewModelScoped
    fun provideStoryService(httpClient: HttpClient): StoryService {
        return MockStoryService()
    }
}
