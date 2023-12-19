/*
 * Copyright (c) 2022. Ryan Wong (hello@ryanwong.co.uk)
 */

package uk.ryanwong.catnews.newslist.data.local

import uk.ryanwong.catnews.newslist.data.local.entity.NewsItemEntity
import uk.ryanwong.catnews.newslist.data.local.entity.NewsListEntity

internal class MockNewsListDao : NewsListDao {

    var mockGetNewsListTitleResponse: String? = null
    override suspend fun getNewsListTitle(listId: Int): String? {
        return mockGetNewsListTitleResponse
    }

    var mockGetNewsListResponse: List<NewsItemEntity>? = null
    override suspend fun getNewsList(listId: Int): List<NewsItemEntity> {
        return mockGetNewsListResponse ?: throw Exception("mock response not defined")
    }

    var mockGetNewsItemResponse: NewsItemEntity? = null
    override suspend fun getNewsItem(listId: Int, newsId: Int): NewsItemEntity? {
        return mockGetNewsItemResponse
    }

    var mockInsertNewsItemsReceivedValue: List<NewsItemEntity>? = null
    override suspend fun insertNewsItems(newsItems: List<NewsItemEntity>) {
        mockInsertNewsItemsReceivedValue = newsItems
    }

    var mockInsertNewsListTitleReceivedValue: NewsListEntity? = null
    override suspend fun insertNewsListTitle(newsListEntity: NewsListEntity) {
        mockInsertNewsListTitleReceivedValue = newsListEntity
    }

    var mockDeleteListTitleReceivedValue: Int? = null
    override suspend fun deleteListTitle(listId: Int) {
        mockDeleteListTitleReceivedValue = listId
    }

    var mockDeleteNewsItemsReceivedValue: Int? = null
    override suspend fun deleteNewsItems(listId: Int) {
        mockDeleteNewsItemsReceivedValue = listId
    }
}
