/*
 * Copyright (c) 2024. Ryan Wong (hello@ryanwong.co.uk)
 */

package uk.ryanwong.catnews.data.dto

import kotlinx.serialization.Serializable

@Serializable
data class ContentDto(
    val accessibilityText: String? = null,
    val text: String? = null,
    val type: String? = null,
    val url: String? = null,
)
