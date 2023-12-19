/*
 * Copyright (c) 2022. Ryan Wong (hello@ryanwong.co.uk)
 */

package uk.ryanwong.catnews.storydetail.ui

import uk.ryanwong.catnews.app.util.ErrorMessage
import uk.ryanwong.catnews.domain.model.storydetail.Story

data class StoryDetailUIState(
    val story: Story? = null,
    val isLoading: Boolean = false,
    val errorMessages: List<ErrorMessage> = emptyList(),
)
