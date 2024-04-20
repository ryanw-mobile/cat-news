/*
 * Copyright (c) 2024. Ryan Wong (hello@ryanwong.co.uk)
 */

package uk.ryanwong.catnews.newslist.data.local.entity

import io.kotest.matchers.collections.shouldContainInOrder
import io.kotest.matchers.shouldBe
import org.junit.Before
import org.junit.Test
import uk.ryanwong.catnews.app.util.nicedateformatter.FakeNiceDateFormatter
import uk.ryanwong.catnews.data.datasource.local.entity.NewsItemEntity
import uk.ryanwong.catnews.data.datasource.local.mappers.asDomainModel
import uk.ryanwong.catnews.domain.model.newslist.NewsItem

class NewsItemEntityMapperTest {

    private lateinit var fakeNiceDateFormatter: FakeNiceDateFormatter

    @Before
    fun setupNiceDateFormatter() {
        fakeNiceDateFormatter = FakeNiceDateFormatter()
    }

    @Test
    fun `asDomainModel should return an empty list if newsItemEntities is empty`() {
        fakeNiceDateFormatter.getNiceDateResponse = "2 days ago"
        val newsItemEntities = listOf<NewsItemEntity>()

        val newsItem = newsItemEntities.asDomainModel(niceDateFormatter = fakeNiceDateFormatter)

        newsItem shouldBe emptyList()
    }

    @Test
    fun `asDomainModel should convert and keep only known types from multiple newsItemEntities`() {
        fakeNiceDateFormatter.getNiceDateResponse = "2 days ago"
        val newsItemEntities = listOf(
            NewsItemEntityMapperTestData.newsItemEntity1,
            NewsItemEntityMapperTestData.newsItemEntity2,
            NewsItemEntityMapperTestData.newsItemEntity3,
        )

        val newsItem = newsItemEntities.asDomainModel(niceDateFormatter = fakeNiceDateFormatter)

        newsItem shouldContainInOrder listOf(
            NewsItemEntityMapperTestData.newsItemStory,
            NewsItemEntityMapperTestData.newsItemWebLink,
        )
    }

    @Test
    fun `asDomainModel should fill headline with empty string if it comes as null`() {
        fakeNiceDateFormatter.getNiceDateResponse = "2 days ago"
        val newsItemEntities = listOf(NewsItemEntityMapperTestData.newsItemEntity1.copy(headline = null))

        val newsItem = newsItemEntities.asDomainModel(niceDateFormatter = fakeNiceDateFormatter)

        newsItem shouldBe listOf(
            NewsItem.Story(
                newsId = 1,
                headline = "",
                teaserText = "some-teaser-text",
                modifiedDate = "2022-05-21T00:00:00Z",
                niceDate = "2 days ago",
                teaserImageUrl = "https://some.teaser.image/href",
                teaserImageAccessibilityText = "some-teaser-image-accessibility-text",
            ),
        )
    }

    @Test
    fun `asDomainModel should fill teaserText with empty string if it comes as null`() {
        fakeNiceDateFormatter.getNiceDateResponse = "2 days ago"
        val newsItemEntities = listOf(NewsItemEntityMapperTestData.newsItemEntity1.copy(teaserText = null))

        val newsItem = newsItemEntities.asDomainModel(niceDateFormatter = fakeNiceDateFormatter)

        newsItem shouldBe listOf(
            NewsItem.Story(
                newsId = 1,
                headline = "some-headline",
                teaserText = "",
                modifiedDate = "2022-05-21T00:00:00Z",
                niceDate = "2 days ago",
                teaserImageUrl = "https://some.teaser.image/href",
                teaserImageAccessibilityText = "some-teaser-image-accessibility-text",
            ),
        )
    }

    @Test
    fun `asDomainModel should fill teaserImageUrl with empty string if teaserImageHref comes as null`() {
        fakeNiceDateFormatter.getNiceDateResponse = "2 days ago"
        val newsItemEntities =
            listOf(
                NewsItemEntityMapperTestData.newsItemEntity1.copy(teaserImageHref = null),
            )

        val newsItem = newsItemEntities.asDomainModel(niceDateFormatter = fakeNiceDateFormatter)

        newsItem shouldBe listOf(
            NewsItem.Story(
                newsId = 1,
                headline = "some-headline",
                teaserText = "some-teaser-text",
                modifiedDate = "2022-05-21T00:00:00Z",
                niceDate = "2 days ago",
                teaserImageUrl = "",
                teaserImageAccessibilityText = "some-teaser-image-accessibility-text",
            ),
        )
    }

    @Test
    fun `asDomainModel should keep url as null if it comes as null`() {
        fakeNiceDateFormatter.getNiceDateResponse = "2 days ago"
        val newsItemEntities =
            listOf(NewsItemEntityMapperTestData.newsItemEntity1.copy(advertUrl = null))

        val newsItem = newsItemEntities.asDomainModel(niceDateFormatter = fakeNiceDateFormatter)

        newsItem shouldBe listOf(
            NewsItem.Story(
                newsId = 1,
                headline = "some-headline",
                teaserText = "some-teaser-text",
                modifiedDate = "2022-05-21T00:00:00Z",
                niceDate = "2 days ago",
                teaserImageUrl = "https://some.teaser.image/href",
                teaserImageAccessibilityText = "some-teaser-image-accessibility-text",
            ),
        )
    }
}
