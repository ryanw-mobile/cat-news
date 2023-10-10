/*
 * Copyright (c) 2022. Ryan Wong (hello@ryanwong.co.uk)
 */

package uk.ryanwong.skycatnews.storydetail.data.local.entity

import uk.ryanwong.skycatnews.storydetail.data.remote.model.ContentDto

internal object ContentEntityTestData {

    val mockContentDao1 by lazy {
        ContentDto(
            accessibilityText = "some-accessibility-text",
            text = "some-text-1",
            type = "paragraph",
            url = "https://some.url/",
        )
    }

    val mockContentDao2 by lazy {
        ContentDto(
            accessibilityText = "some-accessibility-text",
            text = "some-text-2",
            type = "paragraph",
            url = "https://some.url/",
        )
    }

    val mockContentDao3 by lazy {
        ContentDto(
            accessibilityText = "some-accessibility-text",
            text = "some-text-3",
            type = "paragraph",
            url = "https://some.url/",
        )
    }

    val mockContentEntity1 by lazy {
        ContentEntity(
            sequenceId = 0,
            storyId = 1,
            type = "paragraph",
            url = "https://some.url/",
            accessibilityText = "some-accessibility-text",
            text = "some-text-1",
        )
    }

    val mockContentEntity2 by lazy {
        ContentEntity(
            sequenceId = 0,
            storyId = 1,
            type = "paragraph",
            url = "https://some.url/",
            accessibilityText = "some-accessibility-text",
            text = "some-text-2",
        )
    }

    val mockContentEntity3 by lazy {
        ContentEntity(
            sequenceId = 0,
            storyId = 1,
            type = "paragraph",
            url = "https://some.url/",
            accessibilityText = "some-accessibility-text",
            text = "some-text-3",
        )
    }
}
