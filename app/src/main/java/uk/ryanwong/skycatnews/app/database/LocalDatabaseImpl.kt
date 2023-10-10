/*
 * Copyright (c) 2022. Ryan Wong (hello@ryanwong.co.uk)
 */

package uk.ryanwong.skycatnews.app.database

import androidx.room.Database
import androidx.room.RoomDatabase
import uk.ryanwong.skycatnews.newslist.data.local.NewsListDao
import uk.ryanwong.skycatnews.newslist.data.local.entity.NewsItemEntity
import uk.ryanwong.skycatnews.newslist.data.local.entity.NewsListEntity
import uk.ryanwong.skycatnews.storydetail.data.local.StoryDao
import uk.ryanwong.skycatnews.storydetail.data.local.entity.ContentEntity
import uk.ryanwong.skycatnews.storydetail.data.local.entity.StoryEntity

@Database(
    entities = [
        NewsItemEntity::class,
        NewsListEntity::class,
        ContentEntity::class,
        StoryEntity::class,
    ],
    version = 1,
    exportSchema = true,
)
abstract class LocalDatabaseImpl : LocalDatabase, RoomDatabase() {
    abstract override fun newsListDao(): NewsListDao
    abstract override fun storyDao(): StoryDao
}
