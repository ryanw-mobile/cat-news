/*
 * Copyright (c) 2022. Ryan Wong (hello@ryanwong.co.uk)
 */

package uk.ryanwong.skycatnews.storydetail.domain.model

import uk.ryanwong.skycatnews.storydetail.data.local.entity.ContentEntity
import uk.ryanwong.skycatnews.uk.ryanwong.skycatnews.domain.model.storydetail.Content

internal object ContentTestData {

    val mockContentEntity1 by lazy {
        ContentEntity(
            sequenceId = 1,
            storyId = 1,
            type = "paragraph",
            url = "https://some.url/1",
            accessibilityText = "some-accessibility-text-1",
            text = "some-text-1"
        )
    }

    val mockContentEntity2 by lazy {
        ContentEntity(
            sequenceId = 2,
            storyId = 1,
            type = "image",
            url = "https://some.url/2",
            accessibilityText = "some-accessibility-text-2",
            text = "some-text-2"
        )
    }

    val mockContentEntity3 by lazy {
        ContentEntity(
            sequenceId = 3,
            storyId = 1,
            type = "some-unknown-type",
            url = "https://some.url/3",
            accessibilityText = "some-accessibility-text-3",
            text = "some-text-3"
        )
    }

    val mockContentList1 by lazy {
        Content.Paragraph(
            text = "some-text-1",
        )
    }

    val mockContentList2 by lazy {
        Content.Image(
            accessibilityText = "some-accessibility-text-2",
            url = "https://some.url/2"
        )
    }
}
