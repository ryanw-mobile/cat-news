/*
 * Copyright (c) 2023. Ryan Wong (hello@ryanwong.co.uk)
 */

package uk.ryanwong.catnews.storydetail.data.local.entity

import uk.ryanwong.catnews.domain.model.storydetail.Story

fun StoryEntity.toDomainModel(contentEntities: List<ContentEntity>): Story {
    return Story(
        id = storyId,
        contents = contentEntities.toDomainModel(),
        date = modifiedDate,
        headline = headline ?: "",
        heroImageAccessibilityText = heroImageAccessibilityText,
        heroImageUrl = heroImageUrl,
    )
}
