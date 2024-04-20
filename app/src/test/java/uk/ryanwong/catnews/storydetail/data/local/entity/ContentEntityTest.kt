/*
 * Copyright (c) 2024. Ryan Wong (hello@ryanwong.co.uk)
 */

package uk.ryanwong.catnews.storydetail.data.local.entity

import io.kotest.matchers.collections.shouldContainExactly
import io.kotest.matchers.shouldBe
import org.junit.Test
import uk.ryanwong.catnews.data.datasource.local.entity.ContentEntity
import uk.ryanwong.catnews.data.dto.ContentDto

internal class ContentEntityTest {

    @Test
    fun `fromDto should return empty list if contentDtoList is null`() {
        val contentDtoList = null

        val contentEntity = ContentEntity.fromDto(
            storyId = 1,
            contentDtoList = contentDtoList,
        )

        contentEntity shouldBe emptyList()
    }

    @Test
    fun `fromDto should return empty list if contentDtoList is an empty list`() {
        val contentDtoList = emptyList<ContentDto>()

        val contentEntity = ContentEntity.fromDto(
            storyId = 1,
            contentDtoList = contentDtoList,
        )

        contentEntity shouldBe emptyList()
    }

    @Test
    fun `fromDto should return contentEntity list correctly if contentDtoList contains one item`() {
        val contentDtoList = listOf(ContentEntityTestData.contentDao1)

        val contentEntity = ContentEntity.fromDto(
            storyId = 1,
            contentDtoList = contentDtoList,
        )

        contentEntity shouldBe listOf(ContentEntityTestData.contentEntity1)
    }

    @Test
    fun `fromDto should return contentEntity list correctly if contentDtoList contains multiple items`() {
        val contentDtoList = listOf(
            ContentEntityTestData.contentDao1,
            ContentEntityTestData.contentDao2,
            ContentEntityTestData.contentDao3,
        )

        val contentEntity = ContentEntity.fromDto(
            storyId = 1,
            contentDtoList = contentDtoList,
        )

        contentEntity shouldContainExactly listOf(
            ContentEntityTestData.contentEntity1,
            ContentEntityTestData.contentEntity2,
            ContentEntityTestData.contentEntity3,
        )
    }
}
