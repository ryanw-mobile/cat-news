/*
 * Copyright (c) 2022. Ryan Wong (hello@ryanwong.co.uk)
 */

package uk.ryanwong.catnews.newslist.data.repository

import uk.ryanwong.catnews.domain.model.newslist.NewsItem
import uk.ryanwong.catnews.domain.model.newslist.NewsList

internal class MockNewsListRepository : NewsListRepository {

    var mockGetNewsListResponse: Result<NewsList>? = null
    override suspend fun getNewsList(): Result<NewsList> {
        return mockGetNewsListResponse ?: throw Exception("mock response not defined")
    }

    var mockGetNewsItemResponse: Result<NewsItem?>? = null
    var mockGetNewsItemNewsId: Int? = null
    override suspend fun getNewsItem(newsId: Int): Result<NewsItem?> {
        mockGetNewsItemNewsId = newsId
        return mockGetNewsItemResponse ?: throw Exception("mock response not defined")
    }
}
