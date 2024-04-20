/*
 * Copyright (c) 2024. Ryan Wong (hello@ryanwong.co.uk)
 */

package uk.ryanwong.catnews.data.datasource.local.interfaces

import uk.ryanwong.catnews.data.datasource.local.daos.NewsListDao
import uk.ryanwong.catnews.data.datasource.local.daos.StoryDao

interface LocalDatabase {
    fun newsListDao(): NewsListDao
    fun storyDao(): StoryDao
}
