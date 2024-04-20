/*
 * Copyright (c) 2024. Ryan Wong (hello@ryanwong.co.uk)
 */

package uk.ryanwong.catnews.newslist.data.remote

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
import uk.ryanwong.catnews.data.datasource.remote.NewsListServiceImpl
import uk.ryanwong.catnews.data.datasource.remote.interfaces.NewsListService

internal class NewsListServiceImplTest {

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

    @Test
    fun `getAllItems should return NewsListDto if API request is successful`() = runTest {
        setupDataSource(
            status = HttpStatusCode.OK,
            contentType = "application/json",
            payload = NewsListServiceTestData.JSON_RESPONSE,
        )

        val newsListDto = newsListService.getAllItems()

        newsListDto shouldBe Result.success(NewsListServiceTestData.newsListDto)
    }

    @Test
    fun `getAllItems should return success with null DTO if API request is successful with empty body`() = runTest {
        setupDataSource(
            status = HttpStatusCode.OK,
            contentType = "application/json",
            payload = "",
        )

        val newsListDto = newsListService.getAllItems()

        newsListDto shouldBe Result.success(null)
    }

    @Test
    fun `getAllItems should return failure if API request returns HTTP Error`() = runTest {
        setupDataSource(
            status = HttpStatusCode.BadGateway,
            contentType = "text/plain",
            payload = "Bad Gateway",
        )

        val newsListDto = newsListService.getAllItems()

        newsListDto.isFailure shouldBe true
        newsListDto.exceptionOrNull() shouldBe Exception("Bad Gateway")
    }
}
