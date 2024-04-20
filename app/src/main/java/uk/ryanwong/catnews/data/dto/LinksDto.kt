/*
 * Copyright (c) 2024. Ryan Wong (hello@ryanwong.co.uk)
 */

package uk.ryanwong.catnews.data.dto

import kotlinx.serialization.Serializable

@Serializable
data class LinksDto(
    val url: UrlDto? = null,
)
