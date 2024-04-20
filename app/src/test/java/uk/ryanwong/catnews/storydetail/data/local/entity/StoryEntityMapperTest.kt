package uk.ryanwong.catnews.storydetail.data.local.entity

import io.kotest.matchers.shouldBe
import org.junit.Test
import uk.ryanwong.catnews.data.datasource.local.entity.ContentEntity
import uk.ryanwong.catnews.data.datasource.local.mappers.toDomainModel
import uk.ryanwong.catnews.domain.model.storydetail.Content
import uk.ryanwong.catnews.domain.model.storydetail.Story

internal class StoryEntityMapperTest {

    @Test
    fun `fromEntity should return Story if storyEntity is not null and contentEntities is empty`() {
        val storyEntity = StoryEntityMapperTestData.storyEntity
        val contentEntities = listOf<ContentEntity>()

        val story = storyEntity.toDomainModel(contentEntities = contentEntities)

        story shouldBe Story(
            id = 1,
            contents = emptyList(),
            date = "2022-05-21T00:00:00Z",
            headline = "some-headline",
            heroImageAccessibilityText = "some-accessibility-text",
            heroImageUrl = "https://some.hero.image/url",
        )
    }

    @Test
    fun `fromEntity should fill headline with empty string if it comes as null`() {
        val storyEntity = StoryEntityMapperTestData.storyEntity.copy(headline = null)
        val contentEntities = listOf<ContentEntity>()

        val story = storyEntity.toDomainModel(contentEntities = contentEntities)

        story shouldBe Story(
            id = 1,
            contents = emptyList(),
            date = "2022-05-21T00:00:00Z",
            headline = "",
            heroImageAccessibilityText = "some-accessibility-text",
            heroImageUrl = "https://some.hero.image/url",
        )
    }

    @Test
    fun `fromEntity should return Story if storyEntity and contentEntities are not null`() {
        val storyEntity = StoryEntityMapperTestData.storyEntity
        val contentEntities = listOf(StoryEntityMapperTestData.contentEntity)

        val story = storyEntity.toDomainModel(contentEntities = contentEntities)

        story shouldBe Story(
            id = 1,
            contents = listOf(
                Content.Paragraph(text = "some-text-1"),
            ),
            date = "2022-05-21T00:00:00Z",
            headline = "some-headline",
            heroImageAccessibilityText = "some-accessibility-text",
            heroImageUrl = "https://some.hero.image/url",
        )
    }
}
