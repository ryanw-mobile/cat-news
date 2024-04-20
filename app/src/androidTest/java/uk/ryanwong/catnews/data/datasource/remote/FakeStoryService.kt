/*
 * Copyright (c) 2024. Ryan Wong (hello@ryanwong.co.uk)
 */

package uk.ryanwong.catnews.data.datasource.remote

import uk.ryanwong.catnews.data.datasource.remote.interfaces.StoryService
import uk.ryanwong.catnews.data.dto.ContentDto
import uk.ryanwong.catnews.data.dto.HeroImageDto
import uk.ryanwong.catnews.data.dto.StoryDto

class FakeStoryService : StoryService {
    private val imageUrls = listOf(
        "https://ryanwong.co.uk/sample-resources/catnews/cat1_hero.jpg",
        "https://ryanwong.co.uk/sample-resources/catnews/cat2_hero.jpg",
        "https://ryanwong.co.uk/sample-resources/catnews/cat3_hero.jpg",
        "https://ryanwong.co.uk/sample-resources/catnews/cat4_hero.jpg",
        "https://ryanwong.co.uk/sample-resources/catnews/cat5_hero.jpg",
        "https://ryanwong.co.uk/sample-resources/catnews/cat6_hero.jpg",
    )

    override suspend fun getStory(storyId: Int): Result<StoryDto> {
        return Result.success(
            StoryDto(
                contents = listOf(
                    ContentDto(
                        accessibilityText = null,
                        text = "some-paragraph-1",
                        type = "paragraph",
                        url = null,
                    ),
                    ContentDto(
                        accessibilityText = "some-accessibility-text-2",
                        text = null,
                        type = "image",
                        url = imageUrls[0],
                    ),
                    ContentDto(
                        accessibilityText = null,
                        text = "some-paragraph-3",
                        type = "paragraph",
                        url = null,
                    ),
                ),
                creationDate = "2020-11-18T00:00:00Z",
                headline = "some-headline",
                heroImage = HeroImageDto(
                    accessibilityText = "some-hero-image-accessibility-text",
                    imageUrl = imageUrls[2],
                ),
                id = storyId,
                modifiedDate = "2020-11-19T00:00:00Z",
            ),
        )
    }
}
