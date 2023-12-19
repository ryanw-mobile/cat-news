/*
 * Copyright (c) 2022. Ryan Wong (hello@ryanwong.co.uk)
 */

package uk.ryanwong.catnews.storydetail.data.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import uk.ryanwong.catnews.app.database.LocalDatabase
import uk.ryanwong.catnews.storydetail.data.local.StoryDao

@Module
@InstallIn(SingletonComponent::class)
object DaoModule {
    @Provides
    fun provideStoryDao(database: LocalDatabase): StoryDao {
        return database.storyDao()
    }
}
