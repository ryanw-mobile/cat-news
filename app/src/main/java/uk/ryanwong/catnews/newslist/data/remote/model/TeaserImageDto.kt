/*
 * Copyright (c) 2022. Ryan Wong (hello@ryanwong.co.uk)
 */

package uk.ryanwong.catnews.newslist.data.remote.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TeaserImageDto(
    @SerialName(value = "_links")
    val links: LinksDto? = null,
    val accessibilityText: String? = null,
)
