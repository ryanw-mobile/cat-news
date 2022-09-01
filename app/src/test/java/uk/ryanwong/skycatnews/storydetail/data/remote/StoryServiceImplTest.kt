/*
 * Copyright (c) 2022. Ryan Wong (hello@ryanwong.co.uk)
 */

package uk.ryanwong.skycatnews.storydetail.data.remote

import io.kotest.core.spec.style.FreeSpec
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
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.runTest
import kotlinx.serialization.json.Json
import uk.ryanwong.skycatnews.storydetail.data.remote.model.StoryDTO
import uk.ryanwong.skycatnews.uk.ryanwong.skycatnews.storydetail.data.remote.StoryService
import uk.ryanwong.skycatnews.uk.ryanwong.skycatnews.storydetail.data.remote.StoryServiceImpl

@OptIn(ExperimentalCoroutinesApi::class)
internal class StoryServiceImplTest : FreeSpec() {

    private lateinit var scope: TestScope
    private lateinit var httpClient: HttpClient
    private lateinit var storyService: StoryService

    private fun setupDispatcher() {
        val dispatcher = StandardTestDispatcher()
        scope = TestScope(dispatcher)
    }

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
                    }
                )
            }
        }
        storyService = StoryServiceImpl(httpClient = httpClient)
    }

    init {
        "getStory" - {
            "Should return StoryDTO if API request is successful" {
                setupDispatcher()
                scope.runTest {
                    // Given
                    setupDataSource(
                        status = HttpStatusCode.OK,
                        contentType = "application/json",
                        payload = StoryServiceTestData.mockJsonResponse,
                    )

                    // When
                    val storyDTO = storyService.getStory(storyId = 1)

                    // Then
                    storyDTO.isSuccess shouldBe true
                    storyDTO.getOrNull() shouldBe StoryServiceTestData.mockStoryDTO
                }
            }

            "Should return an empty StoryDTO object if API request is successful with empty body" {
                setupDispatcher()
                scope.runTest {
                    // Given
                    setupDataSource(
                        status = HttpStatusCode.OK,
                        contentType = "application/json",
                        payload = "",
                    )

                    // When
                    val storyDTO = storyService.getStory(storyId = 1)

                    // Then
                    storyDTO.isSuccess shouldBe true
                    storyDTO.getOrNull() shouldBe StoryDTO()
                }
            }

            "Should return null if API request returns HTTP Error" {
                setupDispatcher()
                scope.runTest {
                    // Given
                    setupDataSource(
                        status = HttpStatusCode.BadGateway,
                        contentType = "text/plain",
                        payload = "Bad Gateway",
                    )

                    // When
                    val storyDTO = storyService.getStory(storyId = 1)

                    // Then
                    storyDTO.isFailure shouldBe true
                    storyDTO.exceptionOrNull() shouldBe Exception("Bad Gateway")
                }
            }
        }
    }
}