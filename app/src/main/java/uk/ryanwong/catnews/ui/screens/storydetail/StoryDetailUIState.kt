/*
 * Copyright (c) 2024. Ryan Wong (hello@ryanwong.co.uk)
 */

package uk.ryanwong.catnews.ui.screens.storydetail

import uk.ryanwong.catnews.domain.model.storydetail.Story
import uk.ryanwong.catnews.ui.util.ErrorMessage

data class StoryDetailUIState(
    val story: Story? = null,
    val isLoading: Boolean = false,
    val errorMessages: List<ErrorMessage> = emptyList(),
)
