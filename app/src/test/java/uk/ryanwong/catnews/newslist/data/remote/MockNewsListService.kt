/*
 * Copyright (c) 2022. Ryan Wong (hello@ryanwong.co.uk)
 */

package uk.ryanwong.catnews.newslist.data.remote

import uk.ryanwong.catnews.newslist.data.remote.model.NewsListDto

internal class MockNewsListService : NewsListService {
    var mockGetAllItemsResponseException: Exception? = null
    var mockGetAllItemsResponse: Result<NewsListDto>? = null

    override suspend fun getAllItems(): Result<NewsListDto?> {
        mockGetAllItemsResponseException?.let { throw it }
        return mockGetAllItemsResponse ?: throw Exception("mock response not defined")
    }
}
