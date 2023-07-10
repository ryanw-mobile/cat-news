/*
 * Copyright (c) 2023. Ryan Wong (hello@ryanwong.co.uk)
 */

package uk.ryanwong.skycatnews.domain.model.storydetail

import uk.ryanwong.skycatnews.storydetail.data.local.entity.ContentEntity
import uk.ryanwong.skycatnews.storydetail.data.local.entity.StoryEntity

data class Story(
    val id: Int,
    val contents: List<Content>,
    val date: String,
    val headline: String,
    val heroImageAccessibilityText: String?,
    val heroImageUrl: String?,
) {
    companion object {
        fun fromEntity(
            storyEntity: StoryEntity?,
            contentEntities: List<ContentEntity>,
        ): Story? {
            return storyEntity?.let { story ->
                Story(
                    id = story.storyId,
                    contents = Content.fromEntity(contentEntities = contentEntities),
                    date = story.modifiedDate,
                    headline = story.headline ?: "",
                    heroImageAccessibilityText = story.heroImageAccessibilityText,
                    heroImageUrl = story.heroImageUrl
                )
            }
        }
    }
}
