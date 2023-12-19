/*
 * Copyright (c) 2022. Ryan Wong (hello@ryanwong.co.uk)
 */

package uk.ryanwong.catnews.storydetail.data.local.entity

import io.kotest.core.spec.style.FreeSpec
import io.kotest.matchers.collections.shouldContainExactly
import io.kotest.matchers.shouldBe
import uk.ryanwong.catnews.storydetail.data.remote.model.ContentDto

internal class ContentEntityTest : FreeSpec() {

    init {
        "fromDto" - {
            "Should return empty list if contentDtoList is null" {
                // Given
                val contentDtoList = null

                // When
                val contentEntity = ContentEntity.fromDto(
                    storyId = 1,
                    contentDtoList = contentDtoList,
                )

                // Then
                contentEntity shouldBe emptyList()
            }

            "Should return empty list if contentDtoList is an empty list" {
                // Given
                val contentDtoList = emptyList<ContentDto>()

                // When
                val contentEntity = ContentEntity.fromDto(
                    storyId = 1,
                    contentDtoList = contentDtoList,
                )

                // Then
                contentEntity shouldBe emptyList()
            }

            "Should return contentEntity list correctly if contentDtoList contains one item" {
                // Given
                val contentDtoList = listOf(ContentEntityTestData.mockContentDao1)

                // When
                val contentEntity = ContentEntity.fromDto(
                    storyId = 1,
                    contentDtoList = contentDtoList,
                )

                // Then
                contentEntity shouldBe listOf(ContentEntityTestData.mockContentEntity1)
            }

            "Should return contentEntity list correctly if contentDtoList contains multiple items" {
                // Given
                val contentDtoList = listOf(
                    ContentEntityTestData.mockContentDao1,
                    ContentEntityTestData.mockContentDao2,
                    ContentEntityTestData.mockContentDao3,
                )

                // When
                val contentEntity = ContentEntity.fromDto(
                    storyId = 1,
                    contentDtoList = contentDtoList,
                )

                // Then
                contentEntity shouldContainExactly listOf(
                    ContentEntityTestData.mockContentEntity1,
                    ContentEntityTestData.mockContentEntity2,
                    ContentEntityTestData.mockContentEntity3,
                )
            }
        }
    }
}
