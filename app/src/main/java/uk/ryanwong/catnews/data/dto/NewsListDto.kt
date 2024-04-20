/*
 * Copyright (c) 2024. Ryan Wong (hello@ryanwong.co.uk)
 */

package uk.ryanwong.catnews.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class NewsListDto(
    @SerialName(value = "data")
    val news: List<NewsItemDto>? = null,
    val title: String,
)
