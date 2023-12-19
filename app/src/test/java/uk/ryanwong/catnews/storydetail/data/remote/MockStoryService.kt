/*
 * Copyright (c) 2022. Ryan Wong (hello@ryanwong.co.uk)
 */

package uk.ryanwong.catnews.storydetail.data.remote

import uk.ryanwong.catnews.storydetail.data.remote.model.StoryDto

internal class MockStoryService : StoryService {
    var mockGetStoryResponseException: Exception? = null
    var mockGetStoryResponse: Result<StoryDto?>? = null

    override suspend fun getStory(storyId: Int): Result<StoryDto?> {
        mockGetStoryResponseException?.let { throw it }
        return mockGetStoryResponse ?: throw Exception("mock response not defined")
    }
}
