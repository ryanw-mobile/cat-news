/*
 * Copyright (c) 2022. Ryan Wong (hello@ryanwong.co.uk)
 */

package uk.ryanwong.catnews.newslist.data.local

import uk.ryanwong.catnews.data.datasource.local.daos.NewsListDao
import uk.ryanwong.catnews.data.datasource.local.entity.NewsItemEntity
import uk.ryanwong.catnews.data.datasource.local.entity.NewsListEntity

internal class FakeNewsListDao : NewsListDao {

    var getNewsListTitleResponse: String? = null
    override suspend fun getNewsListTitle(listId: Int): String? {
        return getNewsListTitleResponse
    }

    var getNewsListResponse: List<NewsItemEntity>? = null
    override suspend fun getNewsList(listId: Int): List<NewsItemEntity> {
        return getNewsListResponse ?: throw Exception("fake response not defined")
    }

    var getNewsItemResponse: NewsItemEntity? = null
    override suspend fun getNewsItem(listId: Int, newsId: Int): NewsItemEntity? {
        return getNewsItemResponse
    }

    var insertNewsItemsReceivedValue: List<NewsItemEntity>? = null
    override suspend fun insertNewsItems(newsItems: List<NewsItemEntity>) {
        insertNewsItemsReceivedValue = newsItems
    }

    var insertNewsListTitleReceivedValue: NewsListEntity? = null
    override suspend fun insertNewsListTitle(newsListEntity: NewsListEntity) {
        insertNewsListTitleReceivedValue = newsListEntity
    }

    var deleteListTitleReceivedValue: Int? = null
    override suspend fun deleteListTitle(listId: Int) {
        deleteListTitleReceivedValue = listId
    }

    var deleteNewsItemsReceivedValue: Int? = null
    override suspend fun deleteNewsItems(listId: Int) {
        deleteNewsItemsReceivedValue = listId
    }
}
