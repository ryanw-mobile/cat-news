/*
 * Copyright (c) 2023. Ryan Wong (hello@ryanwong.co.uk)
 */

package uk.ryanwong.skycatnews.uk.ryanwong.skycatnews.storydetail.ui

data class StoryDetailUIEvent(
    val onRefresh: () -> Unit,
    val onErrorShown: (errorId: Long) -> Unit,
)
