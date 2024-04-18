/*
 * Copyright (c) 2023. Ryan Wong (hello@ryanwong.co.uk)
 */

package uk.ryanwong.catnews.domain.model.newslist

import io.kotest.matchers.shouldBe
import org.junit.Test
import uk.ryanwong.catnews.newslist.data.local.entity.NewsListEntityMapperTestData

internal class NewsListTest {
    @Test
    fun `isEmpty should return true if it contains title but no newsItems`() {
        val newsList = NewsList(title = "some-title", newsItems = emptyList())
        val isEmpty = newsList.isEmpty()
        isEmpty shouldBe true
    }

    @Test
    fun `isEmpty should return false if newsItems is not empty`() {
        val newsList = NewsList(
            title = "some-title",
            newsItems = listOf(NewsListEntityMapperTestData.newsItemStory),
        )
        val isEmpty = newsList.isEmpty()
        isEmpty shouldBe false
    }
}
