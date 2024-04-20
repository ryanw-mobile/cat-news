/*
 * Copyright (c) 2024. Ryan Wong (hello@ryanwong.co.uk)
 */

package uk.ryanwong.catnews.data.datasource.local.mappers

import uk.ryanwong.catnews.data.datasource.local.entity.ContentEntity
import uk.ryanwong.catnews.data.datasource.local.entity.StoryEntity
import uk.ryanwong.catnews.domain.model.storydetail.Story

fun StoryEntity.asDomainModel(contentEntities: List<ContentEntity>): Story {
    return Story(
        id = storyId,
        contents = contentEntities.asDomainModel(),
        date = modifiedDate,
        headline = headline ?: "",
        heroImageAccessibilityText = heroImageAccessibilityText,
        heroImageUrl = heroImageUrl,
    )
}
