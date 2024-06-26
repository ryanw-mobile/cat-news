/*
 * Copyright (c) 2024. Ryan Wong (hello@ryanwong.co.uk)
 */

package uk.ryanwong.catnews.storydetail.data.local.entity

import io.kotest.matchers.collections.shouldContainInOrder
import io.kotest.matchers.shouldBe
import org.junit.Test
import uk.ryanwong.catnews.data.datasource.local.entity.ContentEntity
import uk.ryanwong.catnews.data.datasource.local.mappers.asDomainModel
import uk.ryanwong.catnews.domain.model.storydetail.Content

internal class ContentEntityMapperTest {

    @Test
    fun `asDomainModel should return an empty list if contentEntities is empty`() {
        val contentEntities = emptyList<ContentEntity>()
        val content = contentEntities.asDomainModel()
        content shouldBe listOf()
    }

    @Test
    fun `asDomainModel should drop unknown entries when having multiple contentEntities`() {
        val contentEntities = listOf(
            ContentEntityMapperTestData.contentEntity1,
            ContentEntityMapperTestData.contentEntity2,
            ContentEntityMapperTestData.contentEntity3,
        )

        val content = contentEntities.asDomainModel()

        content shouldContainInOrder listOf(
            ContentEntityMapperTestData.contentList1,
            ContentEntityMapperTestData.contentList2,
        )
    }

    @Test
    fun `asDomainModel should fill text with empty string if it comes as null`() {
        val contentEntities = listOf(
            ContentEntityMapperTestData.contentEntity1.copy(
                text = null,
            ),
        )

        val content = contentEntities.asDomainModel()

        content shouldBe listOf(Content.Paragraph(text = ""))
    }

    @Test
    fun `asDomainModel should keep url as null if it comes as null`() {
        val contentEntities = listOf(
            ContentEntityMapperTestData.contentEntity1.copy(
                url = null,
            ),
        )

        val content = contentEntities.asDomainModel()

        content shouldBe listOf(Content.Paragraph(text = "some-text-1"))
    }

    @Test
    fun `asDomainModel should keep accessibilityText as null if it comes as null`() {
        val contentEntities = listOf(
            ContentEntityMapperTestData.contentEntity1.copy(
                accessibilityText = null,
            ),
        )

        val content = contentEntities.asDomainModel()

        content shouldBe listOf(Content.Paragraph(text = "some-text-1"))
    }
}
