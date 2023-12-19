/*
 * Copyright (c) 2022. Ryan Wong (hello@ryanwong.co.uk)
 */

package uk.ryanwong.catnews.newslist.data.remote.model

import kotlinx.serialization.Serializable

@Serializable
data class UrlDto(
    val href: String? = null,
    val templated: Boolean? = null,
    val type: String? = null,
)
