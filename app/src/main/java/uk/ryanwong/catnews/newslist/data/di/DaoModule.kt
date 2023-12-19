/*
 * Copyright (c) 2022. Ryan Wong (hello@ryanwong.co.uk)
 */

package uk.ryanwong.catnews.newslist.data.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import uk.ryanwong.catnews.app.database.LocalDatabase
import uk.ryanwong.catnews.newslist.data.local.NewsListDao

@Module
@InstallIn(SingletonComponent::class)
object DaoModule {
    @Provides
    fun provideNewsListDao(database: LocalDatabase): NewsListDao {
        return database.newsListDao()
    }
}
