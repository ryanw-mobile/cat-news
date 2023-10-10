/*
 * Copyright (c) 2022. Ryan Wong (hello@ryanwong.co.uk)
 */

package uk.ryanwong.skycatnews.newslist.data.local.entity

import io.kotest.core.spec.style.FreeSpec
import io.kotest.matchers.collections.shouldContainExactlyInAnyOrder
import io.kotest.matchers.shouldBe
import uk.ryanwong.skycatnews.newslist.data.remote.model.NewsItemDto

internal class NewsItemEntityTest : FreeSpec() {

    init {
        "fromDto" - {
            "Should skip processing NewsItemDto in the list if id is null" {
                // Given
                val newsItemDtoList = listOf(
                    NewsItemEntityTestData.mockNewsItemDto.copy(
                        id = null,
                    ),
                )

                // When
                val newsItemEntity =
                    NewsItemEntity.fromDto(listId = 1, newsItemDtoList = newsItemDtoList)

                // Then
                newsItemEntity shouldBe emptyList()
            }

            "Should skip processing NewsItemDto in the list if creationDate is null" {
                // Given
                val newsItemDtoList = listOf(
                    NewsItemEntityTestData.mockNewsItemDto.copy(
                        creationDate = null,
                    ),
                )

                // When
                val newsItemEntity =
                    NewsItemEntity.fromDto(listId = 1, newsItemDtoList = newsItemDtoList)

                // Then
                newsItemEntity shouldBe emptyList()
            }

            "Should skip processing NewsItemDto in the list if modifiedDate is null" {
                // Given
                val newsItemDtoList = listOf(
                    NewsItemEntityTestData.mockNewsItemDto.copy(
                        modifiedDate = null,
                    ),
                )

                // When
                val newsItemEntity =
                    NewsItemEntity.fromDto(listId = 1, newsItemDtoList = newsItemDtoList)

                // Then
                newsItemEntity shouldBe emptyList()
            }

            "Should only ignore skipped item while retaining valid items" {
                // Given
                val newsItemDtoList = listOf(
                    NewsItemEntityTestData.mockNewsItemDto.copy(
                        modifiedDate = null,
                    ),
                    NewsItemEntityTestData.mockNewsItemDto2,
                )

                // When
                val newsItemEntity =
                    NewsItemEntity.fromDto(listId = 1, newsItemDtoList = newsItemDtoList)

                // Then
                newsItemEntity shouldBe listOf(NewsItemEntityTestData.mockNewsItemEntity2)
            }

            "Should return empty list if newsItemDtoList is null" {
                // Given
                val newsItemDtoList = null

                // When
                val newsItemEntity =
                    NewsItemEntity.fromDto(listId = 1, newsItemDtoList = newsItemDtoList)

                // Then
                newsItemEntity shouldBe emptyList()
            }

            "Should return empty list if newsItemDtoList is empty" {
                // Given
                val newsItemDtoList = emptyList<NewsItemDto>()

                // When
                val newsItemEntity =
                    NewsItemEntity.fromDto(listId = 1, newsItemDtoList = newsItemDtoList)

                // Then
                newsItemEntity shouldBe emptyList()
            }

            "Should correctly convert a newsItemDtoList with one item" {
                // Given
                val newsItemDtoList = listOf(NewsItemEntityTestData.mockNewsItemDto)

                // When
                val newsItemEntity =
                    NewsItemEntity.fromDto(listId = 1, newsItemDtoList = newsItemDtoList)

                // Then
                newsItemEntity shouldBe listOf(NewsItemEntityTestData.mockNewsItemEntity)
            }

            "Should correctly convert a newsItemDtoList with multiple items" {
                // Given
                val newsItemDtoList = listOf(
                    NewsItemEntityTestData.mockNewsItemDto,
                    NewsItemEntityTestData.mockNewsItemDto2,
                    NewsItemEntityTestData.mockNewsItemDto3,
                )

                // When
                val newsItemEntity =
                    NewsItemEntity.fromDto(listId = 1, newsItemDtoList = newsItemDtoList)

                // Then
                newsItemEntity shouldContainExactlyInAnyOrder listOf(
                    NewsItemEntityTestData.mockNewsItemEntity,
                    NewsItemEntityTestData.mockNewsItemEntity2,
                    NewsItemEntityTestData.mockNewsItemEntity3,
                )
            }
        }
    }
}
