/*
 * Copyright (c) 2024. Ryan Wong (hello@ryanwong.co.uk)
 */

package uk.ryanwong.catnews.repository

import io.ktor.client.plugins.HttpRequestTimeoutException
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import uk.ryanwong.catnews.data.datasource.local.daos.StoryDao
import uk.ryanwong.catnews.data.datasource.local.entity.ContentEntity
import uk.ryanwong.catnews.data.datasource.local.entity.StoryEntity
import uk.ryanwong.catnews.data.datasource.local.mappers.asDomainModel
import uk.ryanwong.catnews.data.datasource.remote.interfaces.StoryService
import uk.ryanwong.catnews.data.dto.StoryDto
import uk.ryanwong.catnews.di.DispatcherModule
import uk.ryanwong.catnews.domain.exception.RemoteSourceFailedWithNoCacheException
import uk.ryanwong.catnews.domain.exception.StoryNotFoundException
import uk.ryanwong.catnews.domain.exception.except
import uk.ryanwong.catnews.domain.model.storydetail.Story
import uk.ryanwong.catnews.domain.repository.interfaces.StoryDetailRepository
import java.net.ConnectException
import java.net.UnknownHostException

class StoryDetailRepositoryImpl(
    private val storyService: StoryService,
    private val storyDao: StoryDao,
    @DispatcherModule.IoDispatcher private val dispatcher: CoroutineDispatcher,
) : StoryDetailRepository {

    override suspend fun getStory(storyId: Int): Result<Story> {
        return withContext(dispatcher) {
            Result.runCatching {
                val networkResult = storyService.getStory(storyId = storyId)

                if (networkResult.isFailure) {
                    val throwable = networkResult.exceptionOrNull()
                    when (throwable) {
                        null, // should not happen
                        is UnknownHostException,
                        is ConnectException,
                        is HttpRequestTimeoutException,
                        -> {
                            val story = getStoryFromLocalDatabase(storyId = storyId)
                            return@runCatching story
                                ?: throw RemoteSourceFailedWithNoCacheException()
                        }

                        else -> {
                            throw throwable
                        }
                    }
                } else {
                    networkResult.getOrNull()?.let { storyDto ->
                        updateLocalDatabase(storyDto = storyDto)
                    }
                    return@runCatching getStoryFromLocalDatabase(storyId = storyId)
                        ?: throw StoryNotFoundException()
                }
            }.except<CancellationException, _>()
        }
    }

    private suspend fun getStoryFromLocalDatabase(storyId: Int): Story? {
        val storyEntity = storyDao.getStory(storyId = storyId)
        val contentEntities = storyDao.getContents(storyId = storyId)

        return storyEntity?.asDomainModel(contentEntities = contentEntities)
    }

    private suspend fun updateLocalDatabase(storyDto: StoryDto) {
        storyDao.insertStory(story = StoryEntity.fromDto(storyDto = storyDto))

        // Cleaning up DB first, as we are not using paging and the API behaviour is not clearly defined
        storyDao.deleteContents(storyDto.id)

        val contents =
            ContentEntity.fromDto(storyId = storyDto.id, contentDtoList = storyDto.contents)
        storyDao.insertContents(contents = contents)
    }
}
