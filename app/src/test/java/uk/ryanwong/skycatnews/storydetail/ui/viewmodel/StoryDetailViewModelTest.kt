/*
 * Copyright (c) 2022. Ryan Wong (hello@ryanwong.co.uk)
 */

package uk.ryanwong.skycatnews.storydetail.ui.viewmodel

import androidx.lifecycle.SavedStateHandle
import io.kotest.core.spec.style.FreeSpec
import io.kotest.matchers.collections.shouldHaveSize
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestDispatcher
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.runTest
import uk.ryanwong.skycatnews.R
import uk.ryanwong.skycatnews.app.exception.StoryNotFoundException
import uk.ryanwong.skycatnews.storydetail.data.repository.MockStoryDetailRepository
import uk.ryanwong.skycatnews.uk.ryanwong.skycatnews.storydetail.ui.StoryDetailUIState

internal class StoryDetailViewModelTest : FreeSpec() {

    private lateinit var scope: TestScope
    private lateinit var dispatcher: TestDispatcher
    private lateinit var storyDetailViewModel: StoryDetailViewModel
    private lateinit var mockStoryDetailRepository: MockStoryDetailRepository
    private lateinit var mockStateHandle: SavedStateHandle

    /***
     * Test plan: this viewModel contains something within init() we need to test.
     * certain tests will need do setupViewModel() under the 'when' stage
     */

    private fun setupTest() {
        dispatcher = StandardTestDispatcher()
        scope = TestScope(dispatcher)
        mockStateHandle = mockk()
        mockStoryDetailRepository = MockStoryDetailRepository()
    }

    private fun setupViewModel() {
        storyDetailViewModel = StoryDetailViewModel(
            stateHandle = mockStateHandle,
            storyDetailRepository = mockStoryDetailRepository,
            dispatcher = dispatcher
        )
    }

    init {
        "init" - {
            "Should be able to pass the stateHandle storyId to storyDetailRepository.getStory and make requests" {
                setupTest()
                scope.runTest {
                    // Given
                    every { mockStateHandle.get<Int>("list_id") } returns 521
                    mockStoryDetailRepository.mockGetStoryResponse =
                        Result.success(StoryDetailViewModelTestData.mockNewsItemStory)

                    // When
                    setupViewModel()
                    dispatcher.scheduler.advanceUntilIdle()

                    // Then
                    mockStoryDetailRepository.mockGetStoryStoryId shouldBe 521
                }
            }
        }

        "refreshStory" - {
            "Should be able to pass the correct Story to the uiState if able to get it from StoryDetailRepository" {
                setupTest()
                scope.runTest {
                    // Given
                    every { mockStateHandle.get<Int>("list_id") } returns 2
                    mockStoryDetailRepository.mockGetStoryResponse =
                        Result.success(StoryDetailViewModelTestData.mockNewsItemStory)

                    // When
                    setupViewModel()
                    dispatcher.scheduler.advanceUntilIdle()

                    // Then
                    val uiState = storyDetailViewModel.uiState.first()
                    uiState shouldBe StoryDetailUIState(
                        story = StoryDetailViewModelTestData.mockNewsItemStory,
                        isLoading = false,
                        errorMessages = emptyList()
                    )
                }
            }

            "Should append an error message to uiState if StoryDetailRepository is not returning the Story requested" {
                setupTest()
                scope.runTest {
                    // Given
                    every { mockStateHandle.get<Int>("list_id") } returns 1
                    mockStoryDetailRepository.mockGetStoryResponse = Result.failure(
                        exception = StoryNotFoundException()
                    )

                    // When
                    setupViewModel()
                    dispatcher.scheduler.advanceUntilIdle()

                    // Then
                    val uiState = storyDetailViewModel.uiState.first()
                    uiState.story shouldBe null
                    uiState.isLoading shouldBe false
                    uiState.errorMessages shouldHaveSize 1 // error message id is a random number we cannot assert
                    uiState.errorMessages[0].messageId shouldBe R.string.error_loading_story_detail
                }
            }
        }

        "errorShown" - {
            "Should remove the error message from the list after calling" {
                setupTest()
                scope.runTest {
                    // Given
                    every { mockStateHandle.get<Int>("list_id") } returns 1
                    mockStoryDetailRepository.mockGetStoryResponse = Result.failure(
                        exception = StoryNotFoundException()
                    )
                    setupViewModel()
                    dispatcher.scheduler.advanceUntilIdle()

                    val originalUiState = storyDetailViewModel.uiState.first()
                    originalUiState.errorMessages shouldHaveSize 1

                    // When
                    storyDetailViewModel.errorShown(errorId = originalUiState.errorMessages[0].id)

                    // Then
                    val uiState = storyDetailViewModel.uiState.first()
                    uiState.errorMessages shouldHaveSize 0
                }
            }
        }
    }
}
