/*
 * Copyright (c) 2022. Ryan Wong (hello@ryanwong.co.uk)
 */

package uk.ryanwong.catnews.storydetail.data.local

import uk.ryanwong.catnews.storydetail.data.local.entity.ContentEntity
import uk.ryanwong.catnews.storydetail.data.local.entity.StoryEntity

internal class MockStoryDao : StoryDao {

    var mockGetStoryResponse: StoryEntity? = null
    override suspend fun getStory(storyId: Int): StoryEntity? {
        return mockGetStoryResponse
    }

    var mockGetContentsResponse: List<ContentEntity>? = null
    override suspend fun getContents(storyId: Int): List<ContentEntity> {
        return mockGetContentsResponse ?: throw Exception("mock response not defined")
    }

    var mockInsertStoryReceivedValue: StoryEntity? = null
    override suspend fun insertStory(story: StoryEntity) {
        mockInsertStoryReceivedValue = story
    }

    var mockInsertContentsReceivedValue: List<ContentEntity>? = null
    override suspend fun insertContents(contents: List<ContentEntity>) {
        mockInsertContentsReceivedValue = contents
    }

    var mockDeleteStoryReceivedValue: Int? = null
    override suspend fun deleteStory(storyId: Int) {
        mockDeleteStoryReceivedValue = storyId
    }

    var mockDeleteContentsReceivedValue: Int? = null
    override suspend fun deleteContents(storyId: Int) {
        mockDeleteContentsReceivedValue = storyId
    }
}
