/*
 * Copyright (c) 2022. Ryan Wong (hello@ryanwong.co.uk)
 */

package uk.ryanwong.catnews.storydetail.data.local.entity

import uk.ryanwong.catnews.data.datasource.local.entity.ContentEntity
import uk.ryanwong.catnews.data.dto.ContentDto

internal object ContentEntityTestData {

    val contentDao1 = ContentDto(
        accessibilityText = "some-accessibility-text",
        text = "some-text-1",
        type = "paragraph",
        url = "https://some.url/",
    )

    val contentDao2 = ContentDto(
        accessibilityText = "some-accessibility-text",
        text = "some-text-2",
        type = "paragraph",
        url = "https://some.url/",
    )

    val contentDao3 = ContentDto(
        accessibilityText = "some-accessibility-text",
        text = "some-text-3",
        type = "paragraph",
        url = "https://some.url/",
    )

    val contentEntity1 = ContentEntity(
        sequenceId = 0,
        storyId = 1,
        type = "paragraph",
        url = "https://some.url/",
        accessibilityText = "some-accessibility-text",
        text = "some-text-1",
    )

    val contentEntity2 = ContentEntity(
        sequenceId = 0,
        storyId = 1,
        type = "paragraph",
        url = "https://some.url/",
        accessibilityText = "some-accessibility-text",
        text = "some-text-2",
    )

    val contentEntity3 = ContentEntity(
        sequenceId = 0,
        storyId = 1,
        type = "paragraph",
        url = "https://some.url/",
        accessibilityText = "some-accessibility-text",
        text = "some-text-3",
    )
}
