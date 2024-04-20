/*
 * Copyright (c) 2024. Ryan Wong (hello@ryanwong.co.uk)
 */

package uk.ryanwong.catnews.data.dto

import kotlinx.serialization.Serializable

@Serializable
data class StoryDto(
    val id: Int,
    val contents: List<ContentDto>? = null,
    val headline: String? = null,
    val heroImage: HeroImageDto? = null,
    val creationDate: String,
    val modifiedDate: String,
)
