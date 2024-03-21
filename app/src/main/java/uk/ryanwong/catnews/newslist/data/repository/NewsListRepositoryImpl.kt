/*
 * Copyright (c) 2022. Ryan Wong (hello@ryanwong.co.uk)
 */

package uk.ryanwong.catnews.newslist.data.repository

import io.ktor.client.plugins.HttpRequestTimeoutException
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import uk.ryanwong.catnews.app.exception.RemoteSourceFailedWithNoCacheException
import uk.ryanwong.catnews.app.util.except
import uk.ryanwong.catnews.app.util.nicedateformatter.NiceDateFormatter
import uk.ryanwong.catnews.di.DispatcherModule
import uk.ryanwong.catnews.domain.model.newslist.NewsItem
import uk.ryanwong.catnews.domain.model.newslist.NewsList
import uk.ryanwong.catnews.newslist.data.local.NewsListDao
import uk.ryanwong.catnews.newslist.data.local.entity.NewsItemEntity
import uk.ryanwong.catnews.newslist.data.local.entity.NewsListEntity
import uk.ryanwong.catnews.newslist.data.local.entity.toDomainModel
import uk.ryanwong.catnews.newslist.data.remote.NewsListService
import uk.ryanwong.catnews.newslist.data.remote.model.NewsListDto
import java.net.ConnectException
import java.net.UnknownHostException

class NewsListRepositoryImpl(
    private val newsListService: NewsListService,
    private val newsListDao: NewsListDao,
    private val niceDateFormatter: NiceDateFormatter,
    @DispatcherModule.IoDispatcher private val dispatcher: CoroutineDispatcher,
) : NewsListRepository {

    // For now Sky Cat News is good enough to always assign listId = 1
    // Hardcoded until API returns more than one lists that comes with their own Ids
    private val listId = 1

    override suspend fun getNewsList(): Result<NewsList> {
        return withContext(dispatcher) {
            Result.runCatching {
                val networkResult = newsListService.getAllItems()

                if (networkResult.isFailure) {
                    val throwable = networkResult.exceptionOrNull()
                    when (throwable) {
                        null, // should not happen
                        is UnknownHostException,
                        is ConnectException,
                        is HttpRequestTimeoutException,
                        -> {
                            val newsList = getNewsListFromLocalDatabase()
                            if (newsList.isEmpty()) {
                                throw RemoteSourceFailedWithNoCacheException()
                            } else {
                                return@runCatching newsList
                            }
                        }

                        else -> {
                            throw throwable
                        }
                    }
                } else {
                    networkResult.getOrNull()?.let { newsListDto ->
                        updateLocalDatabase(newsListDto = newsListDto)
                    }
                    return@runCatching getNewsListFromLocalDatabase()
                }
            }.except<CancellationException, _>()
        }
    }

    override suspend fun getNewsItem(newsId: Int): Result<NewsItem?> {
        return withContext(dispatcher) {
            Result.runCatching {
                val newsItemEntity = newsListDao.getNewsItem(listId = listId, newsId = newsId)

                newsItemEntity?.let {
                    val newsItemList =
                        listOf(newsItemEntity).toDomainModel(
                            niceDateFormatter = niceDateFormatter,
                        )
                    newsItemList.getOrNull(0)
                }
            }
        }
    }

    private suspend fun getNewsListFromLocalDatabase(): NewsList {
        return NewsListEntity(
            listId = listId,
            title = newsListDao.getNewsListTitle(listId = listId) ?: "",
        ).toDomainModel(
            newsItemEntities = newsListDao.getNewsList(listId = listId),
            niceDateFormatter = niceDateFormatter,
        )
    }

    private suspend fun updateLocalDatabase(newsListDto: NewsListDto) {
        newsListDao.insertNewsListTitle(
            newsListEntity = NewsListEntity(
                listId = listId,
                title = newsListDto.title,
            ),
        )

        // Cleaning up DB first, as we are not using paging and the API behaviour is not clearly defined
        newsListDao.deleteNewsItems(listId = listId)

        val newsItems = NewsItemEntity.fromDto(listId = listId, newsItemDtoList = newsListDto.news)
        newsListDao.insertNewsItems(newsItems = newsItems)
    }
}
