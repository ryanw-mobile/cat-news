/*
 * Copyright (c) 2022. Ryan Wong (hello@ryanwong.co.uk)
 */

package uk.ryanwong.catnews.newslist.data.remote.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class NewsItemDto(
    val id: Int? = null,
    val headline: String? = null,
    val teaserImage: TeaserImageDto? = null,
    val teaserText: String? = null,
    val type: String? = null,
    @SerialName(value = "url")
    val advertUrl: String? = null,
    val weblinkUrl: String? = null,
    val creationDate: String? = null,
    val modifiedDate: String? = null,
)
