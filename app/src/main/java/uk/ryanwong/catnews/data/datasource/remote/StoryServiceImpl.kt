/*
 * Copyright (c) 2024. Ryan Wong (hello@ryanwong.co.uk)
 */

package uk.ryanwong.catnews.data.datasource.remote

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.http.HttpStatusCode
import io.ktor.http.appendPathSegments
import kotlinx.coroutines.CancellationException
import uk.ryanwong.catnews.data.datasource.remote.interfaces.StoryService
import uk.ryanwong.catnews.data.dto.StoryDto
import uk.ryanwong.catnews.domain.exception.except

class StoryServiceImpl(
    private val httpClient: HttpClient,
) : StoryService {

    override suspend fun getStory(storyId: Int): Result<StoryDto?> {
        return Result.runCatching {
            val response = httpClient.get(StoryService.Endpoints.GetAllItems.url) {
                url {
                    appendPathSegments(storyId.toString())
                }
            }

            when (response.status) {
                HttpStatusCode.OK -> {
                    return@runCatching response.body<StoryDto?>()
                }
                else -> {
                    throw Exception(response.status.description)
                }
            }
        }.except<CancellationException, _>()
    }
}
