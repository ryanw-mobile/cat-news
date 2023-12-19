/*
 * Copyright (c) 2023. Ryan Wong (hello@ryanwong.co.uk)
 */

package uk.ryanwong.catnews.uk.ryanwong.catnews.storydetail.data.local.entity

import timber.log.Timber
import uk.ryanwong.catnews.domain.model.storydetail.Content
import uk.ryanwong.catnews.domain.model.storydetail.StoryContentType
import uk.ryanwong.catnews.storydetail.data.local.entity.ContentEntity

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
