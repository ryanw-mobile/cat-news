/*
 * Copyright (c) 2022. Ryan Wong (hello@ryanwong.co.uk)
 */

package uk.ryanwong.skycatnews.newslist.data.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import dagger.hilt.testing.TestInstallIn
import uk.ryanwong.skycatnews.newslist.data.remote.MockNewsListService
import uk.ryanwong.skycatnews.newslist.data.remote.NewsListService

@Module
@InstallIn(ViewModelComponent::class)
object DataModule {

    @Provides
    @ViewModelScoped
    fun provideNewsListService(): NewsListService {
        return MockNewsListService()
    }
}
