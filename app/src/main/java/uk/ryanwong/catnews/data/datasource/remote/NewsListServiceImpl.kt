/*
 * Copyright (c) 2024. Ryan Wong (hello@ryanwong.co.uk)
 */

package uk.ryanwong.catnews.data.datasource.remote

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.http.HttpStatusCode
import kotlinx.coroutines.CancellationException
import uk.ryanwong.catnews.data.datasource.remote.interfaces.NewsListService
import uk.ryanwong.catnews.data.dto.NewsListDto
import uk.ryanwong.catnews.domain.exception.except

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
