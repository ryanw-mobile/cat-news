/*
 * Copyright (c) 2024. Ryan Wong (hello@ryanwong.co.uk)
 */

package uk.ryanwong.catnews.domain.repository.interfaces

import uk.ryanwong.catnews.domain.model.storydetail.Story

interface StoryDetailRepository {
    suspend fun getStory(storyId: Int): Result<Story>
}
