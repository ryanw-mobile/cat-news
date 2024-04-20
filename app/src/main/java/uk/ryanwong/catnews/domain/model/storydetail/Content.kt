/*
 * Copyright (c) 2024. Ryan Wong (hello@ryanwong.co.uk)
 */

package uk.ryanwong.catnews.domain.model.storydetail

sealed class Content {

    data class Paragraph(
        val text: String,
    ) : Content()

    data class Image(
        val url: String,
        val accessibilityText: String?,
    ) : Content()
}
