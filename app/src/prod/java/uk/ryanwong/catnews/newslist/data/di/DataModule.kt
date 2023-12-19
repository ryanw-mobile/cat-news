/*
 * Copyright (c) 2022. Ryan Wong (hello@ryanwong.co.uk)
 */

package uk.ryanwong.catnews.newslist.data.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import io.ktor.client.HttpClient
import uk.ryanwong.catnews.newslist.data.remote.NewsListService
import uk.ryanwong.catnews.newslist.data.remote.NewsListServiceImpl

@Module
@InstallIn(ViewModelComponent::class)
object DataModule {

    @Provides
    @ViewModelScoped
    fun provideNewsListService(httpClient: HttpClient): NewsListService {
        return NewsListServiceImpl(httpClient = httpClient)
    }
}
