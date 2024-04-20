/*
 * Copyright (c) 2022. Ryan Wong (hello@ryanwong.co.uk)
 */

package uk.ryanwong.catnews.newslist.data.remote

import uk.ryanwong.catnews.data.datasource.remote.interfaces.NewsListService
import uk.ryanwong.catnews.data.dto.NewsListDto

internal class FakeNewsListService : NewsListService {
    var getAllItemsResponseException: Exception? = null
    var getAllItemsResponse: Result<NewsListDto>? = null

    override suspend fun getAllItems(): Result<NewsListDto?> {
        getAllItemsResponseException?.let { throw it }
        return getAllItemsResponse ?: throw Exception("fake response not defined")
    }
}
