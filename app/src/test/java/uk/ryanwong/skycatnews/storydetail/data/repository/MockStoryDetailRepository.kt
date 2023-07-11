/*
 * Copyright (c) 2022. Ryan Wong (hello@ryanwong.co.uk)
 */

package uk.ryanwong.skycatnews.storydetail.data.repository

import uk.ryanwong.skycatnews.domain.model.storydetail.Story

internal class MockStoryDetailRepository : StoryDetailRepository {

    var mockGetStoryResponse: Result<Story>? = null
    var mockGetStoryStoryId: Int? = null
    override suspend fun getStory(storyId: Int): Result<Story> {
        mockGetStoryStoryId = storyId
        return mockGetStoryResponse ?: throw Exception("mock response not defined")
    }
}
