/*
 * Copyright (c) 2022. Ryan Wong (hello@ryanwong.co.uk)
 */

package uk.ryanwong.catnews.newslist.data.remote

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
import kotlinx.coroutines.test.runTest
import kotlinx.serialization.json.Json

internal class NewsListServiceImplTest : FreeSpec() {

    private lateinit var httpClient: HttpClient
    private lateinit var newsListService: NewsListService

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
        newsListService = NewsListServiceImpl(httpClient = httpClient)
    }

    init {

        "getAllItems" - {
            "Should return NewsListDto if API request is successful" {
                runTest {
                    // Given
                    setupDataSource(
                        status = HttpStatusCode.OK,
                        contentType = "application/json",
                        payload = NewsListServiceTestData.MOCK_JSON_RESPONSE,
                    )

                    // When
                    val newsListDto = newsListService.getAllItems()

                    // Then
                    newsListDto shouldBe Result.success(NewsListServiceTestData.mockNewsListDto)
                }
            }

            "Should return success with null DTO if API request is successful with empty body" {
                runTest {
                    // Given
                    setupDataSource(
                        status = HttpStatusCode.OK,
                        contentType = "application/json",
                        payload = "",
                    )

                    // When
                    val newsListDto = newsListService.getAllItems()

                    // Then
                    newsListDto shouldBe Result.success(null)
                }
            }

            "Should return failure if API request returns HTTP Error" {
                runTest {
                    // Given
                    setupDataSource(
                        status = HttpStatusCode.BadGateway,
                        contentType = "text/plain",
                        payload = "Bad Gateway",
                    )

                    // When
                    val newsListDto = newsListService.getAllItems()

                    // Then
                    newsListDto.isFailure shouldBe true
                    newsListDto.exceptionOrNull() shouldBe Exception("Bad Gateway")
                }
            }
        }
    }
}
