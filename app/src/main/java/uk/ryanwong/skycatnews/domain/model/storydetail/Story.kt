/*
 * Copyright (c) 2023. Ryan Wong (hello@ryanwong.co.uk)
 */

package uk.ryanwong.skycatnews.domain.model.storydetail

data class Story(
    val id: Int,
    val contents: List<Content>,
    val date: String,
    val headline: String,
    val heroImageAccessibilityText: String?,
    val heroImageUrl: String?,
)
