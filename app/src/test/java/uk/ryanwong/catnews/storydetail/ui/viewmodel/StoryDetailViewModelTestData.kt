/*
 * Copyright (c) 2024. Ryan Wong (hello@ryanwong.co.uk)
 */

package uk.ryanwong.catnews.storydetail.ui.viewmodel

import uk.ryanwong.catnews.domain.model.storydetail.Content
import uk.ryanwong.catnews.domain.model.storydetail.Story

internal object StoryDetailViewModelTestData {

    val newsItemStory = Story(
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
