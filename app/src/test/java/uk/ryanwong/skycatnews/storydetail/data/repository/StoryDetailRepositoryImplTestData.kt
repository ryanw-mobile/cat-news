/*
 * Copyright (c) 2022. Ryan Wong (hello@ryanwong.co.uk)
 */

package uk.ryanwong.skycatnews.storydetail.data.repository

import uk.ryanwong.skycatnews.domain.model.storydetail.Content
import uk.ryanwong.skycatnews.domain.model.storydetail.Story
import uk.ryanwong.skycatnews.storydetail.data.local.entity.ContentEntity
import uk.ryanwong.skycatnews.storydetail.data.local.entity.StoryEntity
import uk.ryanwong.skycatnews.storydetail.data.remote.model.ContentDto
import uk.ryanwong.skycatnews.storydetail.data.remote.model.HeroImageDto
import uk.ryanwong.skycatnews.storydetail.data.remote.model.StoryDto

internal object StoryDetailRepositoryImplTestData {
    val mockStoryDto by lazy {
        StoryDto(
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
    }

    fun getMockContentEntity(storyId: Int) = ContentEntity(
        sequenceId = 0, // RoomDB auto-increment but default is 0
        storyId = storyId,
        type = "paragraph",
        url = "https://some.url/",
        accessibilityText = "some-accessibility-text",
        text = "some-text-1",
    )

    fun getMockStoryEntity(storyId: Int) = StoryEntity(
        storyId = storyId,
        headline = "some-headline",
        heroImageUrl = "https://some.hero.image/url",
        heroImageAccessibilityText = "some-hero-image-accessibility-text",
        creationDate = "2020-11-18T00:00:00Z",
        modifiedDate = "2020-11-19T00:00:00Z",
    )

    val mockStoryId1 by lazy {
        Story(
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
}
