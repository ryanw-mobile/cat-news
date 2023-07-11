package uk.ryanwong.skycatnews.newslist.data.local.entity

import io.kotest.core.spec.style.FreeSpec
import io.kotest.matchers.shouldBe
import uk.ryanwong.skycatnews.app.util.nicedateformatter.MockNiceDateFormatter
import uk.ryanwong.skycatnews.domain.model.newslist.NewsList

class NewsListEntityMapperTest : FreeSpec() {

    private lateinit var mockNiceDateFormatter: MockNiceDateFormatter

    private fun setupNiceDateFormatter() {
        mockNiceDateFormatter = MockNiceDateFormatter()
    }

    init {
        "toDomainModel" - {
            "Should return NewsList correctly if newsItemEntities contains one item" {
                // Given
                setupNiceDateFormatter()
                mockNiceDateFormatter.mockGetNiceDateResponse = "2 days ago"
                val title = "some-title"
                val listId = 1
                val newsItemEntities = listOf(NewsListEntityMapperTestData.mockNewsItemEntityStory)

                // When
                val newsList = NewsListEntity(
                    listId = listId,
                    title = title,
                ).toDomainModel(
                    newsItemEntities = newsItemEntities,
                    niceDateFormatter = mockNiceDateFormatter,
                )

                // Then
                newsList shouldBe NewsList(
                    title = "some-title",
                    newsItems = listOf(NewsListEntityMapperTestData.mockNewsItemStory),
                )
            }

            "Should convert and keep only known types from multiple newsItemEntities" {
                // Given
                setupNiceDateFormatter()
                mockNiceDateFormatter.mockGetNiceDateResponse = "2 days ago"
                val title = "some-title"
                val listId = 1
                val newsItemEntities = listOf(
                    NewsListEntityMapperTestData.mockNewsItemEntityStory,
                    NewsListEntityMapperTestData.mockNewsItemEntityWebLink,
                    NewsListEntityMapperTestData.mockNewsItemEntityUnknown,
                )

                // When
                val newsList = NewsListEntity(
                    listId = listId,
                    title = title,
                ).toDomainModel(
                    newsItemEntities = newsItemEntities,
                    niceDateFormatter = mockNiceDateFormatter,
                )

                // Then
                newsList shouldBe NewsList(
                    title = "some-title",
                    newsItems = listOf(
                        NewsListEntityMapperTestData.mockNewsItemStory,
                        NewsListEntityMapperTestData.mockNewsItemWebLink,
                    ),
                )
            }
        }
    }
}
