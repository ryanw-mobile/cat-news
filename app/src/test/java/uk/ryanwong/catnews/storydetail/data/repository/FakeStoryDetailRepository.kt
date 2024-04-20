/*
 * Copyright (c) 2022. Ryan Wong (hello@ryanwong.co.uk)
 */

package uk.ryanwong.catnews.storydetail.data.repository

import uk.ryanwong.catnews.domain.model.storydetail.Story
import uk.ryanwong.catnews.domain.repository.interfaces.StoryDetailRepository

internal class FakeStoryDetailRepository : StoryDetailRepository {

    var getStoryResponse: Result<Story>? = null
    var getStoryStoryId: Int? = null
    override suspend fun getStory(storyId: Int): Result<Story> {
        getStoryStoryId = storyId
        return getStoryResponse ?: throw Exception("fake response not defined")
    }
}
