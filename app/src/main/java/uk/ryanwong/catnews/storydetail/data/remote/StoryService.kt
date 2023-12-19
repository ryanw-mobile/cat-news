/*
 * Copyright (c) 2022. Ryan Wong (hello@ryanwong.co.uk)
 */

package uk.ryanwong.catnews.storydetail.data.remote

import uk.ryanwong.catnews.BuildConfig
import uk.ryanwong.catnews.storydetail.data.remote.model.StoryDto

interface StoryService {

    suspend fun getStory(storyId: Int): Result<StoryDto?>

    companion object {
        const val BASE_URL = BuildConfig.DEFAULT_BASE_URL
    }

    sealed class Endpoints(val url: String) {
        object GetAllItems : Endpoints("$BASE_URL/story")
    }
}
