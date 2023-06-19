/*
 * Copyright (c) 2022. Ryan Wong (hello@ryanwong.co.uk)
 */

package uk.ryanwong.skycatnews.storydetail.data.repository

import io.kotest.core.spec.style.FreeSpec
import io.kotest.matchers.shouldBe
import io.ktor.client.plugins.HttpRequestTimeoutException
import java.net.ConnectException
import java.net.UnknownHostException
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.runTest
import uk.ryanwong.skycatnews.app.exception.RemoteSourceFailedWithNoCacheException
import uk.ryanwong.skycatnews.app.exception.StoryNotFoundException
import uk.ryanwong.skycatnews.storydetail.data.local.MockStoryDao
import uk.ryanwong.skycatnews.storydetail.data.local.entity.StoryEntity
import uk.ryanwong.skycatnews.storydetail.data.remote.MockStoryService
import uk.ryanwong.skycatnews.uk.ryanwong.skycatnews.domain.model.storydetail.Story

@OptIn(ExperimentalCoroutinesApi::class)
internal class StoryDetailRepositoryImplTest : FreeSpec() {

    private lateinit var scope: TestScope
    private lateinit var storyDetailRepository: StoryDetailRepository
    private lateinit var mockStoryService: MockStoryService
    private lateinit var mockStoryDao: MockStoryDao

    private fun setupRepository() {
        val dispatcher = StandardTestDispatcher()
        scope = TestScope(dispatcher)

        mockStoryService = MockStoryService()
        mockStoryDao = MockStoryDao()
        storyDetailRepository = StoryDetailRepositoryImpl(
            storyService = mockStoryService,
            storyDao = mockStoryDao,
            dispatcher = dispatcher
        )
    }

    init {
        "getStory" - {
            "Remote data source returned success" - {
                "Should update local database" {
                    setupRepository()
                    scope.runTest {
                        // Given
                        val storyDto = StoryDetailRepositoryImplTestData.mockStoryDto
                        mockStoryService.mockGetStoryResponse = Result.success(storyDto)
                        val storyId = StoryDetailRepositoryImplTestData.mockStoryDto.id

                        // When
                        storyDetailRepository.getStory(storyId = storyId)

                        // Then
                        mockStoryDao.mockInsertStoryReceivedValue shouldBe StoryEntity(
                            storyId = storyId,
                            headline = "some-head-line",
                            heroImageUrl = "https://some.hero.image/url",
                            heroImageAccessibilityText = "some-accessibility-text",
                            creationDate = "2020-11-18T00:00:00Z",
                            modifiedDate = "2020-11-19T00:00:00Z"
                        )
                        mockStoryDao.mockDeleteContentsReceivedValue shouldBe storyId
                        mockStoryDao.mockInsertContentsReceivedValue shouldBe listOf(
                            StoryDetailRepositoryImplTestData.getMockContentEntity(
                                storyId = storyId
                            )
                        )
                    }
                }

                "Should return Result.Success<Story>" {
                    setupRepository()
                    scope.runTest {
                        // Given

                        // Only to trigger a success response,
                        // actual data to be tested is from mockStoryDao.mockGetStoryResponse
                        val storyDto = StoryDetailRepositoryImplTestData.mockStoryDto
                        mockStoryService.mockGetStoryResponse = Result.success(storyDto)

                        val storyId = StoryDetailRepositoryImplTestData.mockStoryDto.id
                        mockStoryDao.mockGetStoryResponse =
                            StoryDetailRepositoryImplTestData.getMockStoryEntity(storyId = storyId)
                        mockStoryDao.mockGetContentsResponse = listOf(
                            StoryDetailRepositoryImplTestData.getMockContentEntity(
                                storyId = storyId
                            )
                        )

                        // When
                        val story = storyDetailRepository.getStory(storyId = storyId)

                        // Then
                        story shouldBe Result.success(
                            StoryDetailRepositoryImplTestData.mockStoryId1
                        )
                    }
                }
            }

            "Remote data source returned success but with empty story" - {
                "Should return Failure.StoryNotFoundException" {
                    setupRepository()
                    scope.runTest {
                        // Given

                        // Only to trigger a success response,
                        // actual data to be tested is from mockStoryDao.mockGetStoryResponse
                        val storyDto = StoryDetailRepositoryImplTestData.mockStoryDto
                        mockStoryService.mockGetStoryResponse = Result.success(storyDto)

                        val storyId = StoryDetailRepositoryImplTestData.mockStoryDto.id
                        mockStoryDao.mockGetStoryResponse = null
                        mockStoryDao.mockGetContentsResponse = listOf()

                        // When
                        val story = storyDetailRepository.getStory(storyId = storyId)

                        // Then
                        story.isFailure shouldBe true
                        story.exceptionOrNull() shouldBe StoryNotFoundException()
                    }
                }
            }

            "Remote data source returned success but with empty content list" - {
                "Should return Result.Success<Story>" {
                    setupRepository()
                    scope.runTest {
                        // Given

                        // Only to trigger a success response,
                        // actual data to be tested is from mockStoryDao.mockGetStoryResponse
                        val storyDto = StoryDetailRepositoryImplTestData.mockStoryDto
                        mockStoryService.mockGetStoryResponse = Result.success(storyDto)

                        val storyId = StoryDetailRepositoryImplTestData.mockStoryDto.id
                        mockStoryDao.mockGetStoryResponse =
                            StoryDetailRepositoryImplTestData.getMockStoryEntity(storyId = storyId)
                        mockStoryDao.mockGetContentsResponse = listOf()

                        // When
                        val story = storyDetailRepository.getStory(storyId = storyId)

                        // Then
                        story shouldBe Result.success(
                            Story(
                                id = 1,
                                contents = emptyList(),
                                date = "2020-11-19T00:00:00Z",
                                headline = "some-headline",
                                heroImageAccessibilityText = "some-hero-image-accessibility-text",
                                heroImageUrl = "https://some.hero.image/url"
                            )
                        )
                    }
                }
            }

            "Remote data source returned failure" - {
                "Should rethrow all other unexpected exceptions" {
                    setupRepository()
                    scope.runTest {
                        // Given
                        mockStoryService.mockGetStoryResponse =
                            Result.failure(exception = ClassNotFoundException())
                        val storyId = 1

                        // When
                        val story = storyDetailRepository.getStory(storyId = storyId)

                        // Then
                        story.isFailure shouldBe true
                        story.exceptionOrNull() shouldBe ClassNotFoundException()
                    }
                }

                "Repository contains cached data" - {
                    "Should return Result.Success with cached NewsList for UnknownHostException" {
                        setupRepository()
                        scope.runTest {
                            // Given
                            val storyId = 1
                            mockStoryService.mockGetStoryResponse =
                                Result.failure(exception = UnknownHostException())
                            mockStoryDao.mockGetStoryResponse =
                                StoryDetailRepositoryImplTestData.getMockStoryEntity(
                                    storyId = storyId
                                )
                            mockStoryDao.mockGetContentsResponse = listOf(
                                StoryDetailRepositoryImplTestData.getMockContentEntity(
                                    storyId = storyId
                                )
                            )

                            // When
                            val story = storyDetailRepository.getStory(storyId = storyId)

                            // Then
                            story shouldBe Result.success(
                                StoryDetailRepositoryImplTestData.mockStoryId1
                            )
                        }
                    }

                    "Should return Result.Success with cached NewsList for ConnectException" {
                        setupRepository()
                        scope.runTest {
                            // Given
                            val storyId = 1
                            mockStoryService.mockGetStoryResponse =
                                Result.failure(exception = ConnectException())
                            mockStoryDao.mockGetStoryResponse =
                                StoryDetailRepositoryImplTestData.getMockStoryEntity(
                                    storyId = storyId
                                )
                            mockStoryDao.mockGetContentsResponse = listOf(
                                StoryDetailRepositoryImplTestData.getMockContentEntity(
                                    storyId = storyId
                                )
                            )

                            // When
                            val story = storyDetailRepository.getStory(storyId = storyId)

                            // Then
                            story shouldBe Result.success(
                                StoryDetailRepositoryImplTestData.mockStoryId1
                            )
                        }
                    }

                    "Should return Result.Success with cached NewsList for HttpRequestTimeoutException" {
                        setupRepository()
                        scope.runTest {
                            // Given
                            val storyId = 1
                            mockStoryService.mockGetStoryResponse =
                                Result.failure(
                                    exception = HttpRequestTimeoutException(
                                        "some-url",
                                        1200L
                                    )
                                )
                            mockStoryDao.mockGetStoryResponse =
                                StoryDetailRepositoryImplTestData.getMockStoryEntity(
                                    storyId = storyId
                                )
                            mockStoryDao.mockGetContentsResponse = listOf(
                                StoryDetailRepositoryImplTestData.getMockContentEntity(
                                    storyId = storyId
                                )
                            )

                            // When
                            val story = storyDetailRepository.getStory(storyId = storyId)

                            // Then
                            story shouldBe Result.success(
                                StoryDetailRepositoryImplTestData.mockStoryId1
                            )
                        }
                    }
                }

                "Repository contains no cached data" - {
                    "Should return Result.failure with RemoteSourceFailedWithNoCacheException for UnknownHostException" {
                        setupRepository()
                        scope.runTest {
                            // Given
                            val storyId = 1
                            mockStoryService.mockGetStoryResponse =
                                Result.failure(exception = UnknownHostException())
                            mockStoryDao.mockGetStoryResponse = null
                            mockStoryDao.mockGetContentsResponse = emptyList()

                            // When
                            val story = storyDetailRepository.getStory(storyId = storyId)

                            // Then
                            story.isFailure shouldBe true
                            story.exceptionOrNull() shouldBe RemoteSourceFailedWithNoCacheException()
                        }
                    }

                    "Should return Result.failure with RemoteSourceFailedWithNoCacheException for ConnectException" {
                        setupRepository()
                        scope.runTest {
                            // Given
                            val storyId = 1
                            mockStoryService.mockGetStoryResponse =
                                Result.failure(exception = ConnectException())
                            mockStoryDao.mockGetStoryResponse = null
                            mockStoryDao.mockGetContentsResponse = emptyList()

                            // When
                            val story = storyDetailRepository.getStory(storyId = storyId)

                            // Then
                            story.isFailure shouldBe true
                            story.exceptionOrNull() shouldBe RemoteSourceFailedWithNoCacheException()
                        }
                    }

                    "Should return Result.failure with RemoteSourceFailedWithNoCacheException for HttpRequestTimeoutException" {
                        setupRepository()
                        scope.runTest {
                            // Given
                            val storyId = 1
                            mockStoryService.mockGetStoryResponse =
                                Result.failure(
                                    exception = HttpRequestTimeoutException(
                                        "some-url",
                                        1200L
                                    )
                                )
                            mockStoryDao.mockGetStoryResponse = null
                            mockStoryDao.mockGetContentsResponse = emptyList()

                            // When
                            val story = storyDetailRepository.getStory(storyId = storyId)

                            // Then
                            story.isFailure shouldBe true
                            story.exceptionOrNull() shouldBe RemoteSourceFailedWithNoCacheException()
                        }
                    }
                }
            }
        }
    }
}
