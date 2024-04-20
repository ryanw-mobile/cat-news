/*
 * Copyright (c) 2022. Ryan Wong (hello@ryanwong.co.uk)
 */

package uk.ryanwong.catnews.newslist.data.local.entity

import io.kotest.matchers.collections.shouldContainExactlyInAnyOrder
import io.kotest.matchers.shouldBe
import org.junit.Test
import uk.ryanwong.catnews.data.datasource.local.entity.NewsItemEntity
import uk.ryanwong.catnews.data.dto.NewsItemDto

internal class NewsItemEntityTest {

    @Test
    fun `fromDto should skip processing NewsItemDto in the list if id is null`() {
        val newsItemDtoList = listOf(
            NewsItemEntityTestData.newsItemDto.copy(
                id = null,
            ),
        )

        val newsItemEntity = NewsItemEntity.fromDto(listId = 1, newsItemDtoList = newsItemDtoList)

        newsItemEntity shouldBe emptyList()
    }

    @Test
    fun `fromDto should skip processing NewsItemDto in the list if creationDate is null`() {
        val newsItemDtoList = listOf(
            NewsItemEntityTestData.newsItemDto.copy(
                creationDate = null,
            ),
        )

        val newsItemEntity = NewsItemEntity.fromDto(listId = 1, newsItemDtoList = newsItemDtoList)

        newsItemEntity shouldBe emptyList()
    }

    @Test
    fun `fromDto should skip processing NewsItemDto in the list if modifiedDate is null`() {
        val newsItemDtoList = listOf(
            NewsItemEntityTestData.newsItemDto.copy(
                modifiedDate = null,
            ),
        )

        val newsItemEntity =
            NewsItemEntity.fromDto(listId = 1, newsItemDtoList = newsItemDtoList)
        newsItemEntity shouldBe emptyList()
    }

    @Test
    fun `fromDto should only ignore skipped item while retaining valid items`() {
        val newsItemDtoList = listOf(
            NewsItemEntityTestData.newsItemDto.copy(
                modifiedDate = null,
            ),
            NewsItemEntityTestData.newsItemDto2,
        )

        val newsItemEntity = NewsItemEntity.fromDto(listId = 1, newsItemDtoList = newsItemDtoList)

        newsItemEntity shouldBe listOf(NewsItemEntityTestData.newsItemEntity2)
    }

    @Test
    fun `fromDto should return empty list if newsItemDtoList is null`() {
        val newsItemDtoList = null

        val newsItemEntity = NewsItemEntity.fromDto(listId = 1, newsItemDtoList = newsItemDtoList)

        newsItemEntity shouldBe emptyList()
    }

    @Test
    fun `fromDto should return empty list if newsItemDtoList is empty`() {
        val newsItemDtoList = emptyList<NewsItemDto>()

        val newsItemEntity = NewsItemEntity.fromDto(listId = 1, newsItemDtoList = newsItemDtoList)

        newsItemEntity shouldBe emptyList()
    }

    @Test
    fun `fromDto should correctly convert a newsItemDtoList with one item`() {
        val newsItemDtoList = listOf(NewsItemEntityTestData.newsItemDto)

        val newsItemEntity = NewsItemEntity.fromDto(listId = 1, newsItemDtoList = newsItemDtoList)

        newsItemEntity shouldBe listOf(NewsItemEntityTestData.newsItemEntity)
    }

    @Test
    fun `fromDto should correctly convert a newsItemDtoList with multiple items`() {
        val newsItemDtoList = listOf(
            NewsItemEntityTestData.newsItemDto,
            NewsItemEntityTestData.newsItemDto2,
            NewsItemEntityTestData.newsItemDto3,
        )

        val newsItemEntity = NewsItemEntity.fromDto(listId = 1, newsItemDtoList = newsItemDtoList)

        newsItemEntity shouldContainExactlyInAnyOrder listOf(
            NewsItemEntityTestData.newsItemEntity,
            NewsItemEntityTestData.newsItemEntity2,
            NewsItemEntityTestData.newsItemEntity3,
        )
    }
}
