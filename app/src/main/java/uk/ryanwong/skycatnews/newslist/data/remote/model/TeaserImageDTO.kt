/*
 * Copyright (c) 2022. Ryan Wong (hello@ryanwong.co.uk)
 */

package uk.ryanwong.skycatnews.newslist.data.remote.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TeaserImageDTO(
    @SerialName(value = "_links")
    val links: LinksDTO? = null,
    val accessibilityText: String? = null,
)
