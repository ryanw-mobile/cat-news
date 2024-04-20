/*
 * Copyright (c) 2023. Ryan Wong (hello@ryanwong.co.uk)
 */

package uk.ryanwong.catnews.storydetail.data.local.entity

import uk.ryanwong.catnews.data.datasource.local.entity.ContentEntity
import uk.ryanwong.catnews.domain.model.storydetail.Content

internal object ContentEntityMapperTestData {

    val contentEntity1 = ContentEntity(
        sequenceId = 1,
        storyId = 1,
        type = "paragraph",
        url = "https://some.url/1",
        accessibilityText = "some-accessibility-text-1",
        text = "some-text-1",
    )

    val contentEntity2 = ContentEntity(
        sequenceId = 2,
        storyId = 1,
        type = "image",
        url = "https://some.url/2",
        accessibilityText = "some-accessibility-text-2",
        text = "some-text-2",
    )

    val contentEntity3 = ContentEntity(
        sequenceId = 3,
        storyId = 1,
        type = "some-unknown-type",
        url = "https://some.url/3",
        accessibilityText = "some-accessibility-text-3",
        text = "some-text-3",
    )

    val contentList1 = Content.Paragraph(
        text = "some-text-1",
    )

    val contentList2 = Content.Image(
        accessibilityText = "some-accessibility-text-2",
        url = "https://some.url/2",
    )
}
