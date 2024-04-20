/*
 * Copyright (c) 2024. Ryan Wong (hello@ryanwong.co.uk)
 */

package uk.ryanwong.catnews.data.datasource.local.mappers

import timber.log.Timber
import uk.ryanwong.catnews.data.datasource.local.entity.ContentEntity
import uk.ryanwong.catnews.domain.model.storydetail.Content
import uk.ryanwong.catnews.domain.model.storydetail.StoryContentType

fun ContentEntity.toDomainModel(): Content? {
    val storyContentType = StoryContentType.parse(type)

    return when (storyContentType) {
        StoryContentType.PARAGRAPH -> {
            Content.Paragraph(text = text ?: "")
        }

        StoryContentType.IMAGE -> {
            url?.let { url ->
                Content.Image(
                    url = url,
                    accessibilityText = accessibilityText,
                )
            }
        }

        // Drop unknown types as we don't know how to show them.
        else -> {
            Timber.d(
                "ContentEntity.toDomainModel(): unknown ContentEntity with type $storyContentType dropped",
            )
            null
        }
    }
}

fun List<ContentEntity>.toDomainModel(): List<Content> {
    return mapNotNull { contentEntity ->
        contentEntity.toDomainModel()
    }
}
