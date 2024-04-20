/*
 * Copyright (c) 2024. Ryan Wong (hello@ryanwong.co.uk)
 */

package uk.ryanwong.catnews.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import uk.ryanwong.catnews.domain.util.nicedateformatter.NiceDateFormatter
import uk.ryanwong.catnews.domain.util.nicedateformatter.NiceDateFormatterImpl
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
