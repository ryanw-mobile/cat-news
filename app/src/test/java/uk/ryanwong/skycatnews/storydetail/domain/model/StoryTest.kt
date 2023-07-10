/*
 * Copyright (c) 2022. Ryan Wong (hello@ryanwong.co.uk)
 */

package uk.ryanwong.skycatnews.storydetail.domain.model

import io.kotest.core.spec.style.FreeSpec
import io.kotest.matchers.shouldBe
import uk.ryanwong.skycatnews.domain.model.storydetail.Content
import uk.ryanwong.skycatnews.domain.model.storydetail.Story
import uk.ryanwong.skycatnews.storydetail.data.local.entity.ContentEntity

internal class StoryTest : FreeSpec() {

    init {
        "fromEntity" - {
            "Should return null if storyEntity is null even contentEntities is not null" {
                // Given
                val storyEntity = null
                val contentEntities = listOf(StoryTestData.mockContentEntity)

                // When
                val story = Story.fromEntity(
                    storyEntity = storyEntity,
                    contentEntities = contentEntities
                )

                // Then
                story shouldBe null
            }

            "Should return Story if storyEntity is not null and contentEntities is empty" {
                // Given
                val storyEntity = StoryTestData.mockStoryEntity
                val contentEntities = listOf<ContentEntity>()

                // When
                val story = Story.fromEntity(
                    storyEntity = storyEntity,
                    contentEntities = contentEntities
                )

                // Then
                story shouldBe Story(
                    id = 1,
                    contents = emptyList(),
                    date = "2022-05-21T00:00:00Z",
                    headline = "some-headline",
                    heroImageAccessibilityText = "some-accessibility-text",
                    heroImageUrl = "https://some.hero.image/url"
                )
            }

            "Should fill headline with empty string if it comes as null" {
                // Given
                val storyEntity = StoryTestData.mockStoryEntity.copy(
                    headline = null
                )
                val contentEntities = listOf<ContentEntity>()

                // When
                val story = Story.fromEntity(
                    storyEntity = storyEntity,
                    contentEntities = contentEntities
                )

                // Then
                story shouldBe Story(
                    id = 1,
                    contents = emptyList(),
                    date = "2022-05-21T00:00:00Z",
                    headline = "",
                    heroImageAccessibilityText = "some-accessibility-text",
                    heroImageUrl = "https://some.hero.image/url"
                )
            }

            "Should return Story if storyEntity and contentEntities are not null" {
                // Given
                val storyEntity = StoryTestData.mockStoryEntity
                val contentEntities = listOf(StoryTestData.mockContentEntity)

                // When
                val story = Story.fromEntity(
                    storyEntity = storyEntity,
                    contentEntities = contentEntities
                )

                // Then
                story shouldBe Story(
                    id = 1,
                    contents = listOf(
                        Content.Paragraph(
                            text = "some-text-1",
                        )
                    ),
                    date = "2022-05-21T00:00:00Z",
                    headline = "some-headline",
                    heroImageAccessibilityText = "some-accessibility-text",
                    heroImageUrl = "https://some.hero.image/url"
                )
            }
        }
    }
}
