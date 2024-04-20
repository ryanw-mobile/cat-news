/*
 * Copyright (c) 2024. Ryan Wong (hello@ryanwong.co.uk)
 */

package uk.ryanwong.catnews.storydetail.data.remote

import uk.ryanwong.catnews.data.datasource.remote.interfaces.StoryService
import uk.ryanwong.catnews.data.dto.StoryDto

internal class FakeStoryService : StoryService {
    var getStoryResponseException: Exception? = null
    var getStoryResponse: Result<StoryDto?>? = null

    override suspend fun getStory(storyId: Int): Result<StoryDto?> {
        getStoryResponseException?.let { throw it }
        return getStoryResponse ?: throw Exception("fake response not defined")
    }
}
