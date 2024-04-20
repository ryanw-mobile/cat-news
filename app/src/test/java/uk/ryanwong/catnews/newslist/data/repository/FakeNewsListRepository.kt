/*
 * Copyright (c) 2022. Ryan Wong (hello@ryanwong.co.uk)
 */

package uk.ryanwong.catnews.newslist.data.repository

import uk.ryanwong.catnews.domain.model.newslist.NewsItem
import uk.ryanwong.catnews.domain.model.newslist.NewsList
import uk.ryanwong.catnews.domain.repository.interfaces.NewsListRepository

internal class FakeNewsListRepository : NewsListRepository {

    var getNewsListResponse: Result<NewsList>? = null
    override suspend fun getNewsList(): Result<NewsList> {
        return getNewsListResponse ?: throw Exception("fake response not defined")
    }

    var getNewsItemResponse: Result<NewsItem?>? = null
    var getNewsItemNewsId: Int? = null
    override suspend fun getNewsItem(newsId: Int): Result<NewsItem?> {
        getNewsItemNewsId = newsId
        return getNewsItemResponse ?: throw Exception("fake response not defined")
    }
}
