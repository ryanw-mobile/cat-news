/*
 * Copyright (c) 2024. Ryan Wong (hello@ryanwong.co.uk)
 */

package uk.ryanwong.catnews.storydetail.data.remote

import io.kotest.matchers.shouldBe
import io.ktor.client.HttpClient
import io.ktor.client.engine.mock.MockEngine
import io.ktor.client.engine.mock.respond
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpStatusCode
import io.ktor.http.headersOf
import io.ktor.serialization.kotlinx.json.json
import io.ktor.utils.io.ByteReadChannel
import kotlinx.coroutines.test.runTest
import kotlinx.serialization.json.Json
import org.junit.Test
import uk.ryanwong.catnews.data.datasource.remote.StoryServiceImpl
import uk.ryanwong.catnews.data.datasource.remote.interfaces.StoryService

internal class StoryServiceImplTest {

    private lateinit var httpClient: HttpClient
    private lateinit var storyService: StoryService

    private fun setupDataSource(status: HttpStatusCode, payload: String, contentType: String) {
        val mockEngine = MockEngine {
            respond(
                content = ByteReadChannel(payload),
                status = status,
                headers = headersOf(HttpHeaders.ContentType, contentType),
            )
        }

        httpClient = HttpClient(mockEngine) {
            install(ContentNegotiation) {
                json(
                    Json {
                        ignoreUnknownKeys = true
                        prettyPrint = true
                        isLenient = true
                    },
                )
            }
        }
        storyService = StoryServiceImpl(httpClient = httpClient)
    }

    @Test
    fun `getStory should return StoryDto if API request is successful`() = runTest {
        setupDataSource(
            status = HttpStatusCode.OK,
            contentType = "application/json",
            payload = StoryServiceTestData.SAMPLE_JAPANESE_RESPONSE,
        )

        val storyDto = storyService.getStory(storyId = 1)

        storyDto shouldBe Result.success(StoryServiceTestData.storyDto)
    }

    @Test
    fun `getStory should return success with null DTO if API request is successful with empty body`() = runTest {
        setupDataSource(
            status = HttpStatusCode.OK,
            contentType = "application/json",
            payload = "",
        )

        val storyDto = storyService.getStory(storyId = 1)

        storyDto shouldBe Result.success(null)
    }

    @Test
    fun `getStory should return failure if API request returns HTTP Error`() = runTest {
        setupDataSource(
            status = HttpStatusCode.BadGateway,
            contentType = "text/plain",
            payload = "Bad Gateway",
        )

        val storyDto = storyService.getStory(storyId = 1)

        storyDto.isFailure shouldBe true
        storyDto.exceptionOrNull() shouldBe Exception("Bad Gateway")
    }
}
