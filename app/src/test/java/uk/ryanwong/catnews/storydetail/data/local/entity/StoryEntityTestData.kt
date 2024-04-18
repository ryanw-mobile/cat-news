/*
 * Copyright (c) 2022. Ryan Wong (hello@ryanwong.co.uk)
 */

package uk.ryanwong.catnews.storydetail.data.local.entity

import uk.ryanwong.catnews.storydetail.data.remote.model.ContentDto
import uk.ryanwong.catnews.storydetail.data.remote.model.HeroImageDto
import uk.ryanwong.catnews.storydetail.data.remote.model.StoryDto

internal object StoryEntityTestData {

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

    val storyEntity = StoryEntity(
        storyId = 1,
        headline = "some-head-line",
        heroImageUrl = "https://some.hero.image/url",
        heroImageAccessibilityText = "some-accessibility-text",
        creationDate = "2020-11-18T00:00:00Z",
        modifiedDate = "2020-11-19T00:00:00Z",
    )
}
