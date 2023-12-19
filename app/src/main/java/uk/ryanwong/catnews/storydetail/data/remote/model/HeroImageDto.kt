/*
 * Copyright (c) 2022. Ryan Wong (hello@ryanwong.co.uk)
 */

package uk.ryanwong.catnews.storydetail.data.remote.model

import kotlinx.serialization.Serializable

@Serializable
data class HeroImageDto(
    val accessibilityText: String? = null,
    val imageUrl: String? = null,
)
