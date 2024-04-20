/*
 * Copyright (c) 2024. Ryan Wong (hello@ryanwong.co.uk)
 */

package uk.ryanwong.catnews.domain.model.storydetail

data class Story(
    val id: Int,
    val contents: List<Content>,
    val date: String,
    val headline: String,
    val heroImageAccessibilityText: String?,
    val heroImageUrl: String?,
)
