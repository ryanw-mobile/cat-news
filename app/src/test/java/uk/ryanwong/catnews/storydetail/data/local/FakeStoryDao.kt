/*
 * Copyright (c) 2022. Ryan Wong (hello@ryanwong.co.uk)
 */

package uk.ryanwong.catnews.storydetail.data.local

import uk.ryanwong.catnews.data.datasource.local.daos.StoryDao
import uk.ryanwong.catnews.data.datasource.local.entity.ContentEntity
import uk.ryanwong.catnews.data.datasource.local.entity.StoryEntity

internal class FakeStoryDao : StoryDao {

    var getStoryResponse: StoryEntity? = null
    override suspend fun getStory(storyId: Int): StoryEntity? {
        return getStoryResponse
    }

    var getContentsResponse: List<ContentEntity>? = null
    override suspend fun getContents(storyId: Int): List<ContentEntity> {
        return getContentsResponse ?: throw Exception("fake response not defined")
    }

    var insertStoryReceivedValue: StoryEntity? = null
    override suspend fun insertStory(story: StoryEntity) {
        insertStoryReceivedValue = story
    }

    var insertContentsReceivedValue: List<ContentEntity>? = null
    override suspend fun insertContents(contents: List<ContentEntity>) {
        insertContentsReceivedValue = contents
    }

    var deleteStoryReceivedValue: Int? = null
    override suspend fun deleteStory(storyId: Int) {
        deleteStoryReceivedValue = storyId
    }

    var deleteContentsReceivedValue: Int? = null
    override suspend fun deleteContents(storyId: Int) {
        deleteContentsReceivedValue = storyId
    }
}
