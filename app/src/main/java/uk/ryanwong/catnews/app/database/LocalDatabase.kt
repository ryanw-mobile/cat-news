/*
 * Copyright (c) 2022. Ryan Wong (hello@ryanwong.co.uk)
 */

package uk.ryanwong.catnews.app.database

import uk.ryanwong.catnews.newslist.data.local.NewsListDao
import uk.ryanwong.catnews.storydetail.data.local.StoryDao

interface LocalDatabase {
    fun newsListDao(): NewsListDao
    fun storyDao(): StoryDao
}
