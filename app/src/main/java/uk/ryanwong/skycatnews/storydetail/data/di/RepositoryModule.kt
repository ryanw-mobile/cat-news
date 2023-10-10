/*
 * Copyright (c) 2022. Ryan Wong (hello@ryanwong.co.uk)
 */

package uk.ryanwong.skycatnews.storydetail.data.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.CoroutineDispatcher
import uk.ryanwong.skycatnews.app.di.DispatcherModule
import uk.ryanwong.skycatnews.storydetail.data.local.StoryDao
import uk.ryanwong.skycatnews.storydetail.data.remote.StoryService
import uk.ryanwong.skycatnews.storydetail.data.repository.StoryDetailRepository
import uk.ryanwong.skycatnews.storydetail.data.repository.StoryDetailRepositoryImpl

@Module
@InstallIn(ViewModelComponent::class)
object RepositoryModule {
    @Provides
    @ViewModelScoped
    fun provideStoryDetailRepository(
        storyService: StoryService,
        storyDao: StoryDao,
        @DispatcherModule.IoDispatcher dispatcher: CoroutineDispatcher,
    ): StoryDetailRepository {
        return StoryDetailRepositoryImpl(
            storyService = storyService,
            storyDao = storyDao,
            dispatcher = dispatcher,
        )
    }
}
