/*
 * Copyright (c) 2024. Ryan Wong (hello@ryanwong.co.uk)
 */

package uk.ryanwong.catnews.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TeaserImageDto(
    @SerialName(value = "_links")
    val links: LinksDto? = null,
    val accessibilityText: String? = null,
)
