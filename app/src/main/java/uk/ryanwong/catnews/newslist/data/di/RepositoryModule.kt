/*
 * Copyright (c) 2022. Ryan Wong (hello@ryanwong.co.uk)
 */

package uk.ryanwong.catnews.newslist.data.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.CoroutineDispatcher
import uk.ryanwong.catnews.app.util.nicedateformatter.NiceDateFormatter
import uk.ryanwong.catnews.di.DispatcherModule
import uk.ryanwong.catnews.newslist.data.local.NewsListDao
import uk.ryanwong.catnews.newslist.data.remote.NewsListService
import uk.ryanwong.catnews.newslist.data.repository.NewsListRepository
import uk.ryanwong.catnews.newslist.data.repository.NewsListRepositoryImpl

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
}
