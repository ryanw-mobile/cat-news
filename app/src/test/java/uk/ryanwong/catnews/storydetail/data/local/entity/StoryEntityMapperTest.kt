package uk.ryanwong.catnews.storydetail.data.local.entity

import io.kotest.core.spec.style.FreeSpec
import io.kotest.matchers.shouldBe
import uk.ryanwong.catnews.domain.model.storydetail.Content
import uk.ryanwong.catnews.domain.model.storydetail.Story

internal class StoryEntityMapperTest : FreeSpec() {

    init {
        "fromEntity" - {
            "Should return Story if storyEntity is not null and contentEntities is empty" {
                // Given
                val storyEntity = StoryEntityMapperTestData.mockStoryEntity
                val contentEntities = listOf<ContentEntity>()

                // When
                val story = storyEntity.toDomainModel(
                    contentEntities = contentEntities,
                )

                // Then
                story shouldBe Story(
                    id = 1,
                    contents = emptyList(),
                    date = "2022-05-21T00:00:00Z",
                    headline = "some-headline",
                    heroImageAccessibilityText = "some-accessibility-text",
                    heroImageUrl = "https://some.hero.image/url",
                )
            }

            "Should fill headline with empty string if it comes as null" {
                // Given
                val storyEntity = StoryEntityMapperTestData.mockStoryEntity.copy(
                    headline = null,
                )
                val contentEntities = listOf<ContentEntity>()

                // When
                val story = storyEntity.toDomainModel(
                    contentEntities = contentEntities,
                )

                // Then
                story shouldBe Story(
                    id = 1,
                    contents = emptyList(),
                    date = "2022-05-21T00:00:00Z",
                    headline = "",
                    heroImageAccessibilityText = "some-accessibility-text",
                    heroImageUrl = "https://some.hero.image/url",
                )
            }

            "Should return Story if storyEntity and contentEntities are not null" {
                // Given
                val storyEntity = StoryEntityMapperTestData.mockStoryEntity
                val contentEntities = listOf(StoryEntityMapperTestData.mockContentEntity)

                // When
                val story = storyEntity.toDomainModel(
                    contentEntities = contentEntities,
                )

                // Then
                story shouldBe Story(
                    id = 1,
                    contents = listOf(
                        Content.Paragraph(
                            text = "some-text-1",
                        ),
                    ),
                    date = "2022-05-21T00:00:00Z",
                    headline = "some-headline",
                    heroImageAccessibilityText = "some-accessibility-text",
                    heroImageUrl = "https://some.hero.image/url",
                )
            }
        }
    }
}
