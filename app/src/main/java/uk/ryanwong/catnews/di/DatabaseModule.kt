/*
 * Copyright (c) 2022. Ryan Wong (hello@ryanwong.co.uk)
 */

package uk.ryanwong.catnews.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import uk.ryanwong.catnews.R
import uk.ryanwong.catnews.data.datasource.local.RoomLocalDatabase
import uk.ryanwong.catnews.data.datasource.local.interfaces.LocalDatabase
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {

    @Provides
    @Singleton
    fun provideLocalDatabase(@ApplicationContext context: Context): LocalDatabase {
        return Room.databaseBuilder(
            context,
            RoomLocalDatabase::class.java,
            context.getString(R.string.app_name),
        )
            .fallbackToDestructiveMigration() // As a local cache it is not necessary to migrate data
            .build()
    }
}
