/*
 * Copyright (c) 2022. Ryan Wong (hello@ryanwong.co.uk)
 */

package uk.ryanwong.skycatnews.uk.ryanwong.skycatnews.newslist.data.remote

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.http.HttpStatusCode
import kotlinx.coroutines.CancellationException
import uk.ryanwong.skycatnews.except
import uk.ryanwong.skycatnews.newslist.data.remote.model.NewsListDTO

class NewsListServiceImpl(
    private val httpClient: HttpClient,
) : NewsListService {

    override suspend fun getAllItems(): Result<NewsListDTO> {
        return Result.runCatching {

            val response = httpClient.get(NewsListService.Endpoints.GetAllItems.url)

            when (response.status) {
                HttpStatusCode.OK -> {
                    val body: NewsListDTO? = response.body()
                    body ?: NewsListDTO()
                }
                else -> {
                    throw Exception(response.status.description)
                }
            }
        }.except<CancellationException, _>()
    }
}
