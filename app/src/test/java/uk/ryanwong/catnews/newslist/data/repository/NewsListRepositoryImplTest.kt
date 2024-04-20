/*
 * Copyright (c) 2022. Ryan Wong (hello@ryanwong.co.uk)
 */

package uk.ryanwong.catnews.newslist.data.repository

import io.kotest.matchers.shouldBe
import io.ktor.client.plugins.HttpRequestTimeoutException
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import uk.ryanwong.catnews.app.util.nicedateformatter.FakeNiceDateFormatter
import uk.ryanwong.catnews.data.datasource.local.entity.NewsListEntity
import uk.ryanwong.catnews.domain.exception.RemoteSourceFailedWithNoCacheException
import uk.ryanwong.catnews.domain.model.newslist.NewsItem
import uk.ryanwong.catnews.domain.model.newslist.NewsList
import uk.ryanwong.catnews.domain.repository.interfaces.NewsListRepository
import uk.ryanwong.catnews.newslist.data.local.FakeNewsListDao
import uk.ryanwong.catnews.newslist.data.remote.FakeNewsListService
import uk.ryanwong.catnews.repository.NewsListRepositoryImpl
import java.net.ConnectException
import java.net.UnknownHostException

internal class NewsListRepositoryImplTest {

    private lateinit var scope: TestScope
    private lateinit var newsListRepository: NewsListRepository
    private lateinit var fakeNewsListService: FakeNewsListService
    private lateinit var fakeNewsListDao: FakeNewsListDao
    private lateinit var fakeNiceDateFormatter: FakeNiceDateFormatter

    @Before
    fun setupRepository() {
        val dispatcher = StandardTestDispatcher()
        scope = TestScope(dispatcher)

        fakeNewsListService = FakeNewsListService()
        fakeNewsListDao = FakeNewsListDao()
        fakeNiceDateFormatter = FakeNiceDateFormatter()

        newsListRepository = NewsListRepositoryImpl(
            newsListService = fakeNewsListService,
            newsListDao = fakeNewsListDao,
            niceDateFormatter = fakeNiceDateFormatter,
            dispatcher = dispatcher,
        )
    }

    @Test
    fun `getNewsList should update local database`() {
        scope.runTest {
            val listId = 1
            val newsListDto = NewsListRepositoryImplTestData.newsListDto
            fakeNewsListService.getAllItemsResponse = Result.success(newsListDto)

            newsListRepository.getNewsList()

            fakeNewsListDao.insertNewsListTitleReceivedValue shouldBe NewsListEntity(
                listId = listId,
                title = "some-title",
            )
            fakeNewsListDao.deleteNewsItemsReceivedValue shouldBe listId
            fakeNewsListDao.insertNewsItemsReceivedValue shouldBe listOf(
                NewsListRepositoryImplTestData.generateNewsItemEntity(listId = listId),
            )
        }
    }

    @Test
    fun `getNewsList should return Result_Success`() {
        scope.runTest {
            val listId = 1
            fakeNiceDateFormatter.getNiceDateResponse = "2 days ago"
            // Only to trigger a success response,
            // actual data to be tested is from fakeNewsListDao.getNewsListResponse
            val newsListDto = NewsListRepositoryImplTestData.newsListDto
            fakeNewsListService.getAllItemsResponse = Result.success(newsListDto)
            fakeNewsListDao.getNewsListTitleResponse = "some-title"
            fakeNewsListDao.getNewsListResponse = listOf(NewsListRepositoryImplTestData.generateNewsItemEntity(listId = listId))

            val newsList = newsListRepository.getNewsList()

            newsList shouldBe Result.success(NewsListRepositoryImplTestData.newsList)
        }
    }

    @Test
    fun `getNewsList should return Result_Success when remote data source returned OK but with empty list`() {
        scope.runTest {
            // Only to trigger a success response,
            // actual data to be tested is from fakeNewsListDao.getNewsListResponse
            val newsListDto = NewsListRepositoryImplTestData.newsListDto
            fakeNewsListService.getAllItemsResponse = Result.success(newsListDto)
            fakeNewsListDao.getNewsListTitleResponse = null
            fakeNewsListDao.getNewsListResponse = listOf()

            val newsList = newsListRepository.getNewsList()

            newsList shouldBe Result.success(
                NewsList(
                    title = "",
                    newsItems = emptyList(),
                ),
            )
        }
    }

    @Test
    fun `getNewsList should rethrow all other unexpected exceptions when remote data source returned failure`() {
        scope.runTest {
            fakeNewsListService.getAllItemsResponse = Result.failure(exception = ClassNotFoundException())

            val newsList = newsListRepository.getNewsList()

            newsList.isFailure shouldBe true
            newsList.exceptionOrNull() shouldBe ClassNotFoundException()
        }
    }

    @Test
    fun `getNewsList Should return Result_Success with cached NewsList for UnknownHostException when repository contains cached data`() {
        scope.runTest {
            val listId = 1
            fakeNewsListDao.getNewsListTitleResponse = "some-title"
            fakeNewsListDao.getNewsListResponse =
                listOf(
                    NewsListRepositoryImplTestData.generateNewsItemEntity(
                        listId = listId,
                    ),
                )
            fakeNewsListService.getAllItemsResponse = Result.failure(exception = UnknownHostException())
            fakeNiceDateFormatter.getNiceDateResponse = "2 days ago"

            val newsList = newsListRepository.getNewsList()

            newsList shouldBe Result.success(NewsListRepositoryImplTestData.newsList)
        }
    }

    @Test
    fun `getNewsList should return Result_Success with cached NewsList for ConnectException`() {
        scope.runTest {
            val listId = 1
            fakeNewsListDao.getNewsListTitleResponse = "some-title"
            fakeNewsListDao.getNewsListResponse =
                listOf(
                    NewsListRepositoryImplTestData.generateNewsItemEntity(
                        listId = listId,
                    ),
                )
            fakeNewsListService.getAllItemsResponse =
                Result.failure(exception = ConnectException())
            fakeNiceDateFormatter.getNiceDateResponse = "2 days ago"

            val newsList = newsListRepository.getNewsList()

            newsList shouldBe Result.success(NewsListRepositoryImplTestData.newsList)
        }
    }

    @Test
    fun `getNewsList should return Result_Success with cached NewsList for HttpRequestTimeoutException`() {
        scope.runTest {
            val listId = 1
            fakeNewsListDao.getNewsListTitleResponse = "some-title"
            fakeNewsListDao.getNewsListResponse = listOf(NewsListRepositoryImplTestData.generateNewsItemEntity(listId = listId))
            fakeNewsListService.getAllItemsResponse = Result.failure(exception = HttpRequestTimeoutException("some-url", 1200L))
            fakeNiceDateFormatter.getNiceDateResponse = "2 days ago"

            val newsList = newsListRepository.getNewsList()

            newsList shouldBe Result.success(NewsListRepositoryImplTestData.newsList)
        }
    }

    @Test
    fun `getNewsList should return Result_failure with RemoteSourceFailedWithNoCacheException for UnknownHostException when repository contains no cached data`() {
        scope.runTest {
            fakeNewsListDao.getNewsListTitleResponse = null
            fakeNewsListDao.getNewsListResponse = listOf()
            fakeNewsListService.getAllItemsResponse =
                Result.failure(exception = UnknownHostException())

            val newsList = newsListRepository.getNewsList()

            newsList.isFailure shouldBe true
            newsList.exceptionOrNull() shouldBe RemoteSourceFailedWithNoCacheException()
        }
    }

    @Test
    fun `getNewsList should return Result_failure with RemoteSourceFailedWithNoCacheException for ConnectException when repository contains no cached data`() {
        scope.runTest {
            fakeNewsListDao.getNewsListTitleResponse = null
            fakeNewsListDao.getNewsListResponse = listOf()
            fakeNewsListService.getAllItemsResponse =
                Result.failure(exception = ConnectException())

            val newsList = newsListRepository.getNewsList()

            newsList.isFailure shouldBe true
            newsList.exceptionOrNull() shouldBe RemoteSourceFailedWithNoCacheException()
        }
    }

    @Test
    fun `getNewsList should return Result_failure with RemoteSourceFailedWithNoCacheException for HttpRequestTimeoutException when repository contains no cached data`() {
        scope.runTest {
            fakeNewsListDao.getNewsListTitleResponse = null
            fakeNewsListDao.getNewsListResponse = listOf()
            fakeNewsListService.getAllItemsResponse =
                Result.failure(
                    exception = HttpRequestTimeoutException(
                        "some-url",
                        1200L,
                    ),
                )

            val newsList = newsListRepository.getNewsList()

            newsList.isFailure shouldBe true
            newsList.exceptionOrNull() shouldBe RemoteSourceFailedWithNoCacheException()
        }
    }

    @Test
    fun `getNewsItem should return Result_success and the correct NewsItem if exists`() {
        scope.runTest {
            val listId = 1
            val newsItemEntity = NewsListRepositoryImplTestData.generateNewsItemEntity(listId = listId).copy(newsId = 1234)
            // Only to trigger a success response,
            // actual data to be tested is from fakeNewsListDao.getNewsListResponse
            val newsListDto = NewsListRepositoryImplTestData.newsListDto
            fakeNewsListService.getAllItemsResponse = Result.success(newsListDto)
            fakeNewsListDao.getNewsListTitleResponse = "some-title"
            fakeNewsListDao.getNewsItemResponse = newsItemEntity
            fakeNiceDateFormatter.getNiceDateResponse = "2 days ago"

            val newsList = newsListRepository.getNewsItem(newsId = 1234)

            newsList shouldBe Result.success(
                NewsItem.Story(
                    newsId = 1234,
                    headline = "some-headline",
                    teaserText = "some-teaser-text",
                    modifiedDate = "2020-11-19T00:00:00Z",
                    niceDate = "2 days ago",
                    teaserImageUrl = "https://some.url/href",
                    teaserImageAccessibilityText = "some-accessibility-text",
                ),
            )
        }
    }

    @Test
    fun `getNewsItem should return Result_success(null) if NewsListDao returns null`() {
        scope.runTest {
            // Only to trigger a success response,
            // actual data to be tested is from fakeNewsListDao.getNewsListResponse
            val newsListDto = NewsListRepositoryImplTestData.newsListDto
            fakeNewsListService.getAllItemsResponse = Result.success(newsListDto)
            fakeNewsListDao.getNewsListTitleResponse = "some-title"
            fakeNewsListDao.getNewsItemResponse = null

            val newsList = newsListRepository.getNewsItem(newsId = 2345)

            newsList shouldBe Result.success(null)
        }
    }
}
