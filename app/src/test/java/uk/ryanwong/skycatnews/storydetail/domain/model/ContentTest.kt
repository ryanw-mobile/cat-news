/*
 * Copyright (c) 2022. Ryan Wong (hello@ryanwong.co.uk)
 */

package uk.ryanwong.skycatnews.storydetail.domain.model

import io.kotest.core.spec.style.FreeSpec
import io.kotest.matchers.collections.shouldContainInOrder
import io.kotest.matchers.shouldBe
import uk.ryanwong.skycatnews.storydetail.data.local.entity.ContentEntity
import uk.ryanwong.skycatnews.uk.ryanwong.skycatnews.domain.model.storydetail.Content

internal class ContentTest : FreeSpec() {

    init {

        "fromEntity" - {
            "Should return an empty list if contentEntities is empty" {
                // Given
                val contentEntities = emptyList<ContentEntity>()

                // When
                val content = Content.fromEntity(contentEntities = contentEntities)

                // Then
                content shouldBe listOf()
            }

            "Should drop unknown entries when having multiple contentEntities" {
                // Given
                val contentEntities = listOf(
                    ContentTestData.mockContentEntity1,
                    ContentTestData.mockContentEntity2,
                    ContentTestData.mockContentEntity3
                )

                // When
                val content = Content.fromEntity(contentEntities = contentEntities)

                // Then
                content shouldContainInOrder listOf(
                    ContentTestData.mockContentList1,
                    ContentTestData.mockContentList2
                )
            }

            "Should fill text with empty string if it comes as null" {
                // Given
                val contentEntities = listOf(
                    ContentTestData.mockContentEntity1.copy(
                        text = null
                    )
                )

                // When
                val content = Content.fromEntity(contentEntities = contentEntities)

                // Then
                content shouldBe listOf(Content.Paragraph(text = ""))
            }

            "Should keep url as null if it comes as null" {
                // Given
                val contentEntities = listOf(
                    ContentTestData.mockContentEntity1.copy(
                        url = null
                    )
                )

                // When
                val content = Content.fromEntity(contentEntities = contentEntities)

                // Then
                content shouldBe listOf(Content.Paragraph(text = "some-text-1"))
            }

            "Should keep accessibilityText as null if it comes as null" {
                // Given
                val contentEntities = listOf(
                    ContentTestData.mockContentEntity1.copy(
                        accessibilityText = null
                    )
                )

                // When
                val content = Content.fromEntity(contentEntities = contentEntities)

                // Then
                content shouldBe listOf(Content.Paragraph(text = "some-text-1"))
            }
        }
    }
}
