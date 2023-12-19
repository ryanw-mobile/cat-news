package uk.ryanwong.catnews.newslist.data.local.entity

import io.kotest.core.spec.style.FreeSpec
import io.kotest.matchers.collections.shouldContainInOrder
import io.kotest.matchers.shouldBe
import uk.ryanwong.catnews.app.util.nicedateformatter.MockNiceDateFormatter
import uk.ryanwong.catnews.domain.model.newslist.NewsItem

class NewsItemEntityMapperTest : FreeSpec() {

    private lateinit var mockNiceDateFormatter: MockNiceDateFormatter

    private fun setupNiceDateFormatter() {
        mockNiceDateFormatter = MockNiceDateFormatter()
    }

    init {
        "toDomainModel" - {
            "Should return an empty list if newsItemEntities is empty" {
                // Given
                setupNiceDateFormatter()
                mockNiceDateFormatter.mockGetNiceDateResponse = "2 days ago"
                val newsItemEntities = listOf<NewsItemEntity>()

                // When
                val newsItem = newsItemEntities.toDomainModel(
                    niceDateFormatter = mockNiceDateFormatter,
                )

                // then
                newsItem shouldBe emptyList()
            }

            "Should convert and keep only known types from multiple newsItemEntities" {
                // Given
                setupNiceDateFormatter()
                mockNiceDateFormatter.mockGetNiceDateResponse = "2 days ago"
                val newsItemEntities = listOf(
                    NewsItemEntityMapperTestData.mockNewsItemEntity1,
                    NewsItemEntityMapperTestData.mockNewsItemEntity2,
                    NewsItemEntityMapperTestData.mockNewsItemEntity3,
                )

                // When
                val newsItem = newsItemEntities.toDomainModel(
                    niceDateFormatter = mockNiceDateFormatter,
                )

                // then
                newsItem shouldContainInOrder listOf(
                    NewsItemEntityMapperTestData.mockNewsItemStory,
                    NewsItemEntityMapperTestData.mockNewsItemWebLink,
                )
            }

            "Should fill headline with empty string if it comes as null" {
                // Given
                setupNiceDateFormatter()
                mockNiceDateFormatter.mockGetNiceDateResponse = "2 days ago"
                val newsItemEntities =
                    listOf(NewsItemEntityMapperTestData.mockNewsItemEntity1.copy(headline = null))

                // When
                val newsItem = newsItemEntities.toDomainModel(
                    niceDateFormatter = mockNiceDateFormatter,
                )

                // then
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

            "Should fill teaserText with empty string if it comes as null" {
                // Given
                setupNiceDateFormatter()
                mockNiceDateFormatter.mockGetNiceDateResponse = "2 days ago"
                val newsItemEntities =
                    listOf(NewsItemEntityMapperTestData.mockNewsItemEntity1.copy(teaserText = null))

                // When
                val newsItem = newsItemEntities.toDomainModel(
                    niceDateFormatter = mockNiceDateFormatter,
                )

                // then
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

            "Should fill teaserImageUrl with empty string if teaserImageHref comes as null" {
                // Given
                setupNiceDateFormatter()
                mockNiceDateFormatter.mockGetNiceDateResponse = "2 days ago"
                val newsItemEntities =
                    listOf(
                        NewsItemEntityMapperTestData.mockNewsItemEntity1.copy(
                            teaserImageHref = null,
                        ),
                    )

                // When
                val newsItem = newsItemEntities.toDomainModel(
                    niceDateFormatter = mockNiceDateFormatter,
                )

                // then
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

            "Should keep url as null if it comes as null" {
                // Given
                setupNiceDateFormatter()
                mockNiceDateFormatter.mockGetNiceDateResponse = "2 days ago"
                val newsItemEntities =
                    listOf(NewsItemEntityMapperTestData.mockNewsItemEntity1.copy(advertUrl = null))

                // When
                val newsItem = newsItemEntities.toDomainModel(
                    niceDateFormatter = mockNiceDateFormatter,
                )

                // then
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
    }
}
