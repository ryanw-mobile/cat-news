/*
 * Copyright (c) 2022. Ryan Wong (hello@ryanwong.co.uk)
 */

package uk.ryanwong.catnews.storydetail.data.remote.model

import kotlinx.serialization.Serializable

@Serializable
data class ContentDto(
    val accessibilityText: String? = null,
    val text: String? = null,
    val type: String? = null,
    val url: String? = null,
)
