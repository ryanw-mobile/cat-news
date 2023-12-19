/*
 * Copyright (c) 2022. Ryan Wong (hello@ryanwong.co.uk)
 */

package uk.ryanwong.catnews.storydetail.data.repository

import uk.ryanwong.catnews.domain.model.storydetail.Story

interface StoryDetailRepository {
    suspend fun getStory(storyId: Int): Result<Story>
}
