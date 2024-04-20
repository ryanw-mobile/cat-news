/*
 * Copyright (c) 2024. Ryan Wong (hello@ryanwong.co.uk)
 */

package uk.ryanwong.catnews.data.datasource.local

import androidx.room.Database
import androidx.room.RoomDatabase
import uk.ryanwong.catnews.data.datasource.local.daos.NewsListDao
import uk.ryanwong.catnews.data.datasource.local.daos.StoryDao
import uk.ryanwong.catnews.data.datasource.local.entity.ContentEntity
import uk.ryanwong.catnews.data.datasource.local.entity.NewsItemEntity
import uk.ryanwong.catnews.data.datasource.local.entity.NewsListEntity
import uk.ryanwong.catnews.data.datasource.local.entity.StoryEntity
import uk.ryanwong.catnews.data.datasource.local.interfaces.LocalDatabase

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
abstract class RoomLocalDatabase : LocalDatabase, RoomDatabase() {
    abstract override fun newsListDao(): NewsListDao
    abstract override fun storyDao(): StoryDao
}
