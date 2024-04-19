/*
 * Copyright (c) 2023. Ryan Wong (hello@ryanwong.co.uk)
 */

package uk.ryanwong.catnews.storydetail.data.local.entity

internal object StoryEntityMapperTestData {

    val storyEntity = StoryEntity(
        storyId = 1,
        headline = "some-headline",
        heroImageUrl = "https://some.hero.image/url",
        heroImageAccessibilityText = "some-accessibility-text",
        creationDate = "2022-05-21T00:00:00Z",
        modifiedDate = "2022-05-21T00:00:00Z",
    )

    val contentEntity = ContentEntity(
        sequenceId = 0, // RoomDB auto-increment but default is 0
        storyId = 1,
        type = "paragraph",
        url = "https://some.url/",
        accessibilityText = "some-accessibility-text",
        text = "some-text-1",
    )
}
