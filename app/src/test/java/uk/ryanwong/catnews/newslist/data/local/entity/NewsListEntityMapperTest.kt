/*
 * Copyright (c) 2024. Ryan Wong (hello@ryanwong.co.uk)
 */

package uk.ryanwong.catnews.newslist.data.local.entity

import io.kotest.matchers.shouldBe
import org.junit.Before
import org.junit.Test
import uk.ryanwong.catnews.app.util.nicedateformatter.FakeNiceDateFormatter
import uk.ryanwong.catnews.data.datasource.local.entity.NewsListEntity
import uk.ryanwong.catnews.data.datasource.local.mappers.asDomainModel
import uk.ryanwong.catnews.domain.model.newslist.NewsList

class NewsListEntityMapperTest {

    private lateinit var fakeNiceDateFormatter: FakeNiceDateFormatter

    @Before
    fun setupNiceDateFormatter() {
        fakeNiceDateFormatter = FakeNiceDateFormatter()
    }

    @Test
    fun `asDomainModel should return NewsList correctly if newsItemEntities contains one item`() {
        setupNiceDateFormatter()
        fakeNiceDateFormatter.getNiceDateResponse = "2 days ago"
        val title = "some-title"
        val listId = 1
        val newsItemEntities = listOf(NewsListEntityMapperTestData.newsItemEntityStory)

        val newsList = NewsListEntity(
            listId = listId,
            title = title,
        ).asDomainModel(
            newsItemEntities = newsItemEntities,
            niceDateFormatter = fakeNiceDateFormatter,
        )

        newsList shouldBe NewsList(
            title = "some-title",
            newsItems = listOf(NewsListEntityMapperTestData.newsItemStory),
        )
    }

    @Test
    fun `asDomainModel should convert and keep only known types from multiple newsItemEntities`() {
        setupNiceDateFormatter()
        fakeNiceDateFormatter.getNiceDateResponse = "2 days ago"
        val title = "some-title"
        val listId = 1
        val newsItemEntities = listOf(
            NewsListEntityMapperTestData.newsItemEntityStory,
            NewsListEntityMapperTestData.newsItemEntityWebLink,
            NewsListEntityMapperTestData.newsItemEntityUnknown,
        )

        val newsList = NewsListEntity(
            listId = listId,
            title = title,
        ).asDomainModel(
            newsItemEntities = newsItemEntities,
            niceDateFormatter = fakeNiceDateFormatter,
        )

        newsList shouldBe NewsList(
            title = "some-title",
            newsItems = listOf(
                NewsListEntityMapperTestData.newsItemStory,
                NewsListEntityMapperTestData.newsItemWebLink,
            ),
        )
    }
}
