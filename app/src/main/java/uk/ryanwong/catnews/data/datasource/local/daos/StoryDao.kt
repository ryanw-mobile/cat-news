/*
 * Copyright (c) 2024. Ryan Wong (hello@ryanwong.co.uk)
 */

package uk.ryanwong.catnews.data.datasource.local.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import uk.ryanwong.catnews.data.datasource.local.entity.ContentEntity
import uk.ryanwong.catnews.data.datasource.local.entity.StoryEntity

@Dao
interface StoryDao {
    @Query("SELECT * FROM stories WHERE story_id = :storyId LIMIT 1")
    suspend fun getStory(storyId: Int): StoryEntity?

    @Query("SELECT * FROM contents WHERE story_id = :storyId order by sequence_id ASC")
    suspend fun getContents(storyId: Int): List<ContentEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertStory(story: StoryEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertContents(contents: List<ContentEntity>)

    @Query("DELETE FROM stories WHERE story_id = :storyId ")
    suspend fun deleteStory(storyId: Int)

    @Query("DELETE FROM contents WHERE story_id = :storyId ")
    suspend fun deleteContents(storyId: Int)
}
