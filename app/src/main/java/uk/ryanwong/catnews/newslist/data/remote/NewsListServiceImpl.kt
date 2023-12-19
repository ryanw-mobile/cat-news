/*
 * Copyright (c) 2022. Ryan Wong (hello@ryanwong.co.uk)
 */

package uk.ryanwong.catnews.newslist.data.remote

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.http.HttpStatusCode
import kotlinx.coroutines.CancellationException
import uk.ryanwong.catnews.app.util.except
import uk.ryanwong.catnews.newslist.data.remote.model.NewsListDto

class NewsListServiceImpl(
    private val httpClient: HttpClient,
) : NewsListService {

    override suspend fun getAllItems(): Result<NewsListDto?> {
        return Result.runCatching {
            val response = httpClient.get(NewsListService.Endpoints.GetAllItems.url)

            when (response.status) {
                HttpStatusCode.OK -> {
                    return@runCatching response.body<NewsListDto?>()
                }
                else -> {
                    throw Exception(response.status.description)
                }
            }
        }.except<CancellationException, _>()
    }
}
