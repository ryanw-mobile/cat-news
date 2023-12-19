/*
 * Copyright (c) 2022. Ryan Wong (hello@ryanwong.co.uk)
 */

package uk.ryanwong.catnews.newslist.data.remote.model

import kotlinx.serialization.Serializable

@Serializable
data class LinksDto(
    val url: UrlDto? = null,
)
