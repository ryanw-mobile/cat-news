package uk.ryanwong.catnews.storydetail.data.local.entity

import io.kotest.matchers.collections.shouldContainInOrder
import io.kotest.matchers.shouldBe
import org.junit.Test
import uk.ryanwong.catnews.domain.model.storydetail.Content

internal class ContentEntityMapperTest {

    @Test
    fun `toDomainModel should return an empty list if contentEntities is empty`() {
        val contentEntities = emptyList<ContentEntity>()
        val content = contentEntities.toDomainModel()
        content shouldBe listOf()
    }

    @Test
    fun `toDomainModel should drop unknown entries when having multiple contentEntities`() {
        val contentEntities = listOf(
            ContentEntityMapperTestData.contentEntity1,
            ContentEntityMapperTestData.contentEntity2,
            ContentEntityMapperTestData.contentEntity3,
        )

        val content = contentEntities.toDomainModel()

        content shouldContainInOrder listOf(
            ContentEntityMapperTestData.contentList1,
            ContentEntityMapperTestData.contentList2,
        )
    }

    @Test
    fun `toDomainModel should fill text with empty string if it comes as null`() {
        val contentEntities = listOf(
            ContentEntityMapperTestData.contentEntity1.copy(
                text = null,
            ),
        )

        val content = contentEntities.toDomainModel()

        content shouldBe listOf(Content.Paragraph(text = ""))
    }

    @Test
    fun `toDomainModel should keep url as null if it comes as null`() {
        val contentEntities = listOf(
            ContentEntityMapperTestData.contentEntity1.copy(
                url = null,
            ),
        )

        val content = contentEntities.toDomainModel()

        content shouldBe listOf(Content.Paragraph(text = "some-text-1"))
    }

    @Test
    fun `toDomainModel should keep accessibilityText as null if it comes as null`() {
        val contentEntities = listOf(
            ContentEntityMapperTestData.contentEntity1.copy(
                accessibilityText = null,
            ),
        )

        val content = contentEntities.toDomainModel()

        content shouldBe listOf(Content.Paragraph(text = "some-text-1"))
    }
}
