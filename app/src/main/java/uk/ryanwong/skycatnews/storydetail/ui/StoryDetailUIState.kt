/*
 * Copyright (c) 2022. Ryan Wong (hello@ryanwong.co.uk)
 */

package uk.ryanwong.skycatnews.uk.ryanwong.skycatnews.storydetail.ui

import uk.ryanwong.skycatnews.app.util.ErrorMessage
import uk.ryanwong.skycatnews.storydetail.domain.model.Story

data class StoryDetailUIState(
    val story: Story? = null,
    val isLoading: Boolean = false,
    val errorMessages: List<ErrorMessage> = emptyList(),
)
