/*
 * Copyright (c) 2024. Ryan Wong (hello@ryanwong.co.uk)
 */

package uk.ryanwong.catnews.storydetail.data.repository

import io.kotest.matchers.shouldBe
import io.ktor.client.plugins.HttpRequestTimeoutException
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import uk.ryanwong.catnews.data.datasource.local.entity.StoryEntity
import uk.ryanwong.catnews.domain.exception.RemoteSourceFailedWithNoCacheException
import uk.ryanwong.catnews.domain.exception.StoryNotFoundException
import uk.ryanwong.catnews.domain.model.storydetail.Story
import uk.ryanwong.catnews.domain.repository.interfaces.StoryDetailRepository
import uk.ryanwong.catnews.repository.StoryDetailRepositoryImpl
import uk.ryanwong.catnews.storydetail.data.local.FakeStoryDao
import uk.ryanwong.catnews.storydetail.data.remote.FakeStoryService
import java.net.ConnectException
import java.net.UnknownHostException

internal class StoryDetailRepositoryImplTest {

    private lateinit var scope: TestScope
    private lateinit var storyDetailRepository: StoryDetailRepository
    private lateinit var storyService: FakeStoryService
    private lateinit var storyDao: FakeStoryDao

    @Before
    fun setupRepository() {
        val dispatcher = StandardTestDispatcher()
        scope = TestScope(dispatcher)

        storyService = FakeStoryService()
        storyDao = FakeStoryDao()
        storyDetailRepository = StoryDetailRepositoryImpl(
            storyService = storyService,
            storyDao = storyDao,
            dispatcher = dispatcher,
        )
    }

    @Test
    fun `getStory should update local database when remote data source returned success`() {
        scope.runTest {
            val storyDto = StoryDetailRepositoryImplTestData.storyDto
            storyService.getStoryResponse = Result.success(storyDto)
            val storyId = StoryDetailRepositoryImplTestData.storyDto.id

            storyDetailRepository.getStory(storyId = storyId)

            storyDao.insertStoryReceivedValue shouldBe StoryEntity(
                storyId = storyId,
                headline = "some-head-line",
                heroImageUrl = "https://some.hero.image/url",
                heroImageAccessibilityText = "some-accessibility-text",
                creationDate = "2020-11-18T00:00:00Z",
                modifiedDate = "2020-11-19T00:00:00Z",
            )
            storyDao.deleteContentsReceivedValue shouldBe storyId
            storyDao.insertContentsReceivedValue shouldBe listOf(
                StoryDetailRepositoryImplTestData.generateContentEntity(storyId = storyId),
            )
        }
    }

    @Test
    fun `getStory should return Result_Success when remote data source returned success`() {
        scope.runTest {
            // Only to trigger a success response,
            // actual data to be tested is from storyDao.getStoryResponse
            val storyDto = StoryDetailRepositoryImplTestData.storyDto
            storyService.getStoryResponse = Result.success(storyDto)
            val storyId = StoryDetailRepositoryImplTestData.storyDto.id
            storyDao.getStoryResponse = StoryDetailRepositoryImplTestData.generateStoryEntity(storyId = storyId)
            storyDao.getContentsResponse = listOf(StoryDetailRepositoryImplTestData.generateContentEntity(storyId = storyId))

            val story = storyDetailRepository.getStory(storyId = storyId)

            story shouldBe Result.success(StoryDetailRepositoryImplTestData.storyId1)
        }
    }

    @Test
    fun `getStory should return Failure_StoryNotFoundException when remote data source returned success but with empty story`() {
        scope.runTest {
            // Only to trigger a success response,
            // actual data to be tested is from storyDao.getStoryResponse
            val storyDto = StoryDetailRepositoryImplTestData.storyDto
            storyService.getStoryResponse = Result.success(storyDto)
            val storyId = StoryDetailRepositoryImplTestData.storyDto.id
            storyDao.getStoryResponse = null
            storyDao.getContentsResponse = listOf()

            val story = storyDetailRepository.getStory(storyId = storyId)

            story.isFailure shouldBe true
            story.exceptionOrNull() shouldBe StoryNotFoundException()
        }
    }

    @Test
    fun `getStory should return Result_Success when remote data source returned success but with empty content list`() {
        scope.runTest {
            // Only to trigger a success response,
            // actual data to be tested is from storyDao.getStoryResponse
            val storyDto = StoryDetailRepositoryImplTestData.storyDto
            storyService.getStoryResponse = Result.success(storyDto)
            val storyId = StoryDetailRepositoryImplTestData.storyDto.id
            storyDao.getStoryResponse = StoryDetailRepositoryImplTestData.generateStoryEntity(storyId = storyId)
            storyDao.getContentsResponse = listOf()

            val story = storyDetailRepository.getStory(storyId = storyId)

            story shouldBe Result.success(
                Story(
                    id = 1,
                    contents = emptyList(),
                    date = "2020-11-19T00:00:00Z",
                    headline = "some-headline",
                    heroImageAccessibilityText = "some-hero-image-accessibility-text",
                    heroImageUrl = "https://some.hero.image/url",
                ),
            )
        }
    }

    @Test
    fun `getStory should rethrow all other unexpected exceptions when remote data source returned failure`() {
        scope.runTest {
            storyService.getStoryResponse = Result.failure(exception = ClassNotFoundException())
            val storyId = 1

            val story = storyDetailRepository.getStory(storyId = storyId)

            story.isFailure shouldBe true
            story.exceptionOrNull() shouldBe ClassNotFoundException()
        }
    }

    @Test
    fun `getStory should return Result_Success with cached NewsList for UnknownHostException when repository contains cached data`() {
        scope.runTest {
            val storyId = 1
            storyService.getStoryResponse = Result.failure(exception = UnknownHostException())
            storyDao.getStoryResponse = StoryDetailRepositoryImplTestData.generateStoryEntity(storyId = storyId)
            storyDao.getContentsResponse = listOf(StoryDetailRepositoryImplTestData.generateContentEntity(storyId = storyId))

            val story = storyDetailRepository.getStory(storyId = storyId)

            story shouldBe Result.success(StoryDetailRepositoryImplTestData.storyId1)
        }
    }

    @Test
    fun `getStory should return Result_Success with cached NewsList for ConnectException when repository contains cached data`() {
        scope.runTest {
            val storyId = 1
            storyService.getStoryResponse = Result.failure(exception = ConnectException())
            storyDao.getStoryResponse = StoryDetailRepositoryImplTestData.generateStoryEntity(storyId = storyId)
            storyDao.getContentsResponse = listOf(StoryDetailRepositoryImplTestData.generateContentEntity(storyId = storyId))

            val story = storyDetailRepository.getStory(storyId = storyId)

            story shouldBe Result.success(StoryDetailRepositoryImplTestData.storyId1)
        }
    }

    @Test
    fun `getStory should return Result_Success with cached NewsList for HttpRequestTimeoutException when repository contains cached data`() {
        scope.runTest {
            val storyId = 1
            storyService.getStoryResponse = Result.failure(exception = HttpRequestTimeoutException("some-url", 1200L))
            storyDao.getStoryResponse = StoryDetailRepositoryImplTestData.generateStoryEntity(storyId = storyId)
            storyDao.getContentsResponse = listOf(StoryDetailRepositoryImplTestData.generateContentEntity(storyId = storyId))

            val story = storyDetailRepository.getStory(storyId = storyId)

            story shouldBe Result.success(StoryDetailRepositoryImplTestData.storyId1)
        }
    }

    @Test
    fun `getStory should return Result_failure with RemoteSourceFailedWithNoCacheException for UnknownHostException when rpository contains no cached data`() {
        scope.runTest {
            val storyId = 1
            storyService.getStoryResponse = Result.failure(exception = UnknownHostException())
            storyDao.getStoryResponse = null
            storyDao.getContentsResponse = emptyList()

            val story = storyDetailRepository.getStory(storyId = storyId)

            story.isFailure shouldBe true
            story.exceptionOrNull() shouldBe RemoteSourceFailedWithNoCacheException()
        }
    }

    @Test
    fun `getStory should return Result_failure with RemoteSourceFailedWithNoCacheException for ConnectException when rpository contains no cached data`() {
        scope.runTest {
            val storyId = 1
            storyService.getStoryResponse = Result.failure(exception = ConnectException())
            storyDao.getStoryResponse = null
            storyDao.getContentsResponse = emptyList()

            val story = storyDetailRepository.getStory(storyId = storyId)

            story.isFailure shouldBe true
            story.exceptionOrNull() shouldBe RemoteSourceFailedWithNoCacheException()
        }
    }

    @Test
    fun `getStory should return Result_failure with RemoteSourceFailedWithNoCacheException for HttpRequestTimeoutException when rpository contains no cached data`() {
        scope.runTest {
            val storyId = 1
            storyService.getStoryResponse = Result.failure(exception = HttpRequestTimeoutException("some-url", 1200L))
            storyDao.getStoryResponse = null
            storyDao.getContentsResponse = emptyList()

            val story = storyDetailRepository.getStory(storyId = storyId)

            story.isFailure shouldBe true
            story.exceptionOrNull() shouldBe RemoteSourceFailedWithNoCacheException()
        }
    }
}
