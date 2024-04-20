/*
 * Copyright (c) 2022. Ryan Wong (hello@ryanwong.co.uk)
 */

package uk.ryanwong.catnews.storydetail.data.repository

import uk.ryanwong.catnews.data.datasource.local.entity.ContentEntity
import uk.ryanwong.catnews.data.datasource.local.entity.StoryEntity
import uk.ryanwong.catnews.data.dto.ContentDto
import uk.ryanwong.catnews.data.dto.HeroImageDto
import uk.ryanwong.catnews.data.dto.StoryDto
import uk.ryanwong.catnews.domain.model.storydetail.Content
import uk.ryanwong.catnews.domain.model.storydetail.Story

internal object StoryDetailRepositoryImplTestData {
    val storyDto = StoryDto(
        contents = listOf(
            ContentDto(
                accessibilityText = "some-accessibility-text",
                text = "some-text-1",
                type = "paragraph",
                url = "https://some.url/",
            ),
        ),
        creationDate = "2020-11-18T00:00:00Z",
        headline = "some-head-line",
        heroImage = HeroImageDto(
            accessibilityText = "some-accessibility-text",
            imageUrl = "https://some.hero.image/url",
        ),
        id = 1,
        modifiedDate = "2020-11-19T00:00:00Z",
    )

    fun generateContentEntity(storyId: Int) = ContentEntity(
        sequenceId = 0, // RoomDB auto-increment but default is 0
        storyId = storyId,
        type = "paragraph",
        url = "https://some.url/",
        accessibilityText = "some-accessibility-text",
        text = "some-text-1",
    )

    fun generateStoryEntity(storyId: Int) = StoryEntity(
        storyId = storyId,
        headline = "some-headline",
        heroImageUrl = "https://some.hero.image/url",
        heroImageAccessibilityText = "some-hero-image-accessibility-text",
        creationDate = "2020-11-18T00:00:00Z",
        modifiedDate = "2020-11-19T00:00:00Z",
    )

    val storyId1 = Story(
        id = 1,
        contents = listOf(
            Content.Paragraph(
                text = "some-text-1",
            ),
        ),
        date = "2020-11-19T00:00:00Z",
        headline = "some-headline",
        heroImageAccessibilityText = "some-hero-image-accessibility-text",
        heroImageUrl = "https://some.hero.image/url",
    )
}
