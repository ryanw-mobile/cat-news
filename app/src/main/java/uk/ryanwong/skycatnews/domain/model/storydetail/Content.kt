/*
 * Copyright (c) 2023. Ryan Wong (hello@ryanwong.co.uk)
 */

package uk.ryanwong.skycatnews.uk.ryanwong.skycatnews.domain.model.storydetail

import timber.log.Timber
import uk.ryanwong.skycatnews.storydetail.data.local.entity.ContentEntity

sealed class Content {

    data class Paragraph(
        val text: String,
    ) : Content()

    data class Image(
        val url: String,
        val accessibilityText: String?,
    ) : Content()

    companion object {
        fun fromEntity(
            contentEntities: List<ContentEntity>,
        ): List<Content> {
            return contentEntities.mapNotNull { contentEntity ->

                val storyContentType = StoryContentType.parse(contentEntity.type)
                when (storyContentType) {
                    StoryContentType.PARAGRAPH -> {
                        Paragraph(
                            text = contentEntity.text ?: "",
                        )
                    }

                    StoryContentType.IMAGE -> {
                        contentEntity.url?.let { url ->
                            Image(
                                url = url,
                                accessibilityText = contentEntity.accessibilityText,
                            )
                        }
                    }

                    // Drop unknown types as we don't know how to show them.
                    else -> {
                        Timber.d(
                            "NewsItem.fromEntity(): unknown NewsItem with type $storyContentType dropped"
                        )
                        null
                    }
                }
            }
        }
    }
}
