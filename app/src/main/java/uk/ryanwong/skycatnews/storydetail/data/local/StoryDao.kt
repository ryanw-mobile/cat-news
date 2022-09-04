/*
 * Copyright (c) 2022. Ryan Wong (hello@ryanwong.co.uk)
 */

package uk.ryanwong.skycatnews.storydetail.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import uk.ryanwong.skycatnews.storydetail.data.local.model.ContentEntity
import uk.ryanwong.skycatnews.storydetail.data.local.model.StoryEntity

@Dao
interface StoryDao {
    @Query("SELECT * FROM stories WHERE story_id = :storyId LIMIT 1")
    suspend fun getStory(storyId: Int): StoryEntity

    @Query("SELECT * FROM contents WHERE story_id = :storyId order by sequence_id ASC")
    suspend fun getContent(storyId: Int): List<ContentEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertStory(
        story: StoryEntity,
        content: List<ContentEntity>,
    )

    @Query("DELETE FROM stories WHERE story_id = :storyId ")
    suspend fun deleteStory(storyId: Int)

    @Query("DELETE FROM contents WHERE story_id = :storyId ")
    suspend fun deleteContent(storyId: Int)
}