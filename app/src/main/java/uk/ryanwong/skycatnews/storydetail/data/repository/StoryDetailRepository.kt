/*
 * Copyright (c) 2022. Ryan Wong (hello@ryanwong.co.uk)
 */

package uk.ryanwong.skycatnews.storydetail.data.repository

import uk.ryanwong.skycatnews.uk.ryanwong.skycatnews.domain.model.storydetail.Story

interface StoryDetailRepository {
    suspend fun getStory(storyId: Int): Result<Story>
}
