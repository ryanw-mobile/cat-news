/*
 * Copyright (c) 2022. Ryan Wong (hello@ryanwong.co.uk)
 */

package uk.ryanwong.skycatnews.app.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import uk.ryanwong.skycatnews.app.util.nicedateformatter.NiceDateFormatter
import uk.ryanwong.skycatnews.app.util.nicedateformatter.NiceDateFormatterImpl
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NiceDateFormatterModule {
    @Provides
    @Singleton
    fun provideNiceDateFormatter(): NiceDateFormatter {
        return NiceDateFormatterImpl()
    }
}
