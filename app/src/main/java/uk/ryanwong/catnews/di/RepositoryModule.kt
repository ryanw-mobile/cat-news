/*
 * Copyright (c) 2024. Ryan Wong (hello@ryanwong.co.uk)
 */

package uk.ryanwong.catnews.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.CoroutineDispatcher
import uk.ryanwong.catnews.data.datasource.local.daos.NewsListDao
import uk.ryanwong.catnews.data.datasource.local.daos.StoryDao
import uk.ryanwong.catnews.data.datasource.remote.interfaces.NewsListService
import uk.ryanwong.catnews.data.datasource.remote.interfaces.StoryService
import uk.ryanwong.catnews.domain.repository.interfaces.NewsListRepository
import uk.ryanwong.catnews.domain.repository.interfaces.StoryDetailRepository
import uk.ryanwong.catnews.domain.util.nicedateformatter.NiceDateFormatter
import uk.ryanwong.catnews.repository.NewsListRepositoryImpl
import uk.ryanwong.catnews.repository.StoryDetailRepositoryImpl

@Module
@InstallIn(ViewModelComponent::class)
object RepositoryModule {
    @Provides
    @ViewModelScoped
    fun provideNewsListRepository(
        newsListService: NewsListService,
        newsListDao: NewsListDao,
        niceDateFormatter: NiceDateFormatter,
        @DispatcherModule.IoDispatcher dispatcher: CoroutineDispatcher,
    ): NewsListRepository {
        return NewsListRepositoryImpl(
            newsListService = newsListService,
            newsListDao = newsListDao,
            niceDateFormatter = niceDateFormatter,
            dispatcher = dispatcher,
        )
    }

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
