package uk.ryanwong.skycatnews.storydetail.data.local.entity

import io.kotest.core.spec.style.FreeSpec
import io.kotest.matchers.collections.shouldContainInOrder
import io.kotest.matchers.shouldBe
import uk.ryanwong.skycatnews.domain.model.storydetail.Content
import uk.ryanwong.skycatnews.uk.ryanwong.skycatnews.storydetail.data.local.entity.toDomainModel

internal class ContentEntityMapperTest : FreeSpec() {

    init {

        "toDomainModel" - {
            "Should return an empty list if contentEntities is empty" {
                // Given
                val contentEntities = emptyList<ContentEntity>()

                // When
                val content = contentEntities.toDomainModel()

                // Then
                content shouldBe listOf()
            }

            "Should drop unknown entries when having multiple contentEntities" {
                // Given
                val contentEntities = listOf(
                    ContentEntityMapperTestData.mockContentEntity1,
                    ContentEntityMapperTestData.mockContentEntity2,
                    ContentEntityMapperTestData.mockContentEntity3,
                )

                // When
                val content = contentEntities.toDomainModel()

                // Then
                content shouldContainInOrder listOf(
                    ContentEntityMapperTestData.mockContentList1,
                    ContentEntityMapperTestData.mockContentList2,
                )
            }

            "Should fill text with empty string if it comes as null" {
                // Given
                val contentEntities = listOf(
                    ContentEntityMapperTestData.mockContentEntity1.copy(
                        text = null,
                    ),
                )

                // When
                val content = contentEntities.toDomainModel()

                // Then
                content shouldBe listOf(Content.Paragraph(text = ""))
            }

            "Should keep url as null if it comes as null" {
                // Given
                val contentEntities = listOf(
                    ContentEntityMapperTestData.mockContentEntity1.copy(
                        url = null,
                    ),
                )

                // When
                val content = contentEntities.toDomainModel()

                // Then
                content shouldBe listOf(Content.Paragraph(text = "some-text-1"))
            }

            "Should keep accessibilityText as null if it comes as null" {
                // Given
                val contentEntities = listOf(
                    ContentEntityMapperTestData.mockContentEntity1.copy(
                        accessibilityText = null,
                    ),
                )

                // When
                val content = contentEntities.toDomainModel()

                // Then
                content shouldBe listOf(Content.Paragraph(text = "some-text-1"))
            }
        }
    }
}
