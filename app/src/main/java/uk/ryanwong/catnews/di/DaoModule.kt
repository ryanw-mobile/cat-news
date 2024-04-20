/*
 * Copyright (c) 2024. Ryan Wong (hello@ryanwong.co.uk)
 */

package uk.ryanwong.catnews.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import uk.ryanwong.catnews.data.datasource.local.daos.NewsListDao
import uk.ryanwong.catnews.data.datasource.local.daos.StoryDao
import uk.ryanwong.catnews.data.datasource.local.interfaces.LocalDatabase

@Module
@InstallIn(SingletonComponent::class)
object DaoModule {
    @Provides
    fun provideNewsListDao(database: LocalDatabase): NewsListDao {
        return database.newsListDao()
    }

    @Provides
    fun provideStoryDao(database: LocalDatabase): StoryDao {
        return database.storyDao()
    }
}
