/*
 * Copyright (c) 2024. Ryan Wong (hello@ryanwong.co.uk)
 */

package uk.ryanwong.catnews.storydetail.ui.viewmodel

import androidx.lifecycle.SavedStateHandle
import io.kotest.matchers.collections.shouldHaveSize
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestDispatcher
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import uk.ryanwong.catnews.R
import uk.ryanwong.catnews.domain.exception.StoryNotFoundException
import uk.ryanwong.catnews.storydetail.data.repository.FakeStoryDetailRepository
import uk.ryanwong.catnews.ui.screens.storydetail.StoryDetailUIState
import uk.ryanwong.catnews.ui.viewmodel.StoryDetailViewModel

internal class StoryDetailViewModelTest {

    private lateinit var scope: TestScope
    private lateinit var dispatcher: TestDispatcher
    private lateinit var storyDetailViewModel: StoryDetailViewModel
    private lateinit var fakeStoryDetailRepository: FakeStoryDetailRepository
    private lateinit var mockStateHandle: SavedStateHandle

    @Before
    fun setupTest() {
        dispatcher = StandardTestDispatcher()
        scope = TestScope(dispatcher)
        mockStateHandle = mockk()
        fakeStoryDetailRepository = FakeStoryDetailRepository()
    }

    private fun setupViewModel() {
        storyDetailViewModel = StoryDetailViewModel(
            stateHandle = mockStateHandle,
            storyDetailRepository = fakeStoryDetailRepository,
            dispatcher = dispatcher,
        )
    }

    @Test
    fun `init should be able to pass the stateHandle storyId to storyDetailRepository_getStory and make requests`() {
        scope.runTest {
            every { mockStateHandle.get<Int>("list_id") } returns 521
            fakeStoryDetailRepository.getStoryResponse = Result.success(StoryDetailViewModelTestData.newsItemStory)

            setupViewModel()
            dispatcher.scheduler.advanceUntilIdle()

            fakeStoryDetailRepository.getStoryStoryId shouldBe 521
        }
    }

    @Test
    fun `refreshStory should be able to pass the correct Story to the uiState if able to get it from StoryDetailRepository`() {
        scope.runTest {
            every { mockStateHandle.get<Int>("list_id") } returns 2
            fakeStoryDetailRepository.getStoryResponse = Result.success(StoryDetailViewModelTestData.newsItemStory)

            setupViewModel()
            dispatcher.scheduler.advanceUntilIdle()

            val uiState = storyDetailViewModel.uiState.first()
            uiState shouldBe StoryDetailUIState(
                story = StoryDetailViewModelTestData.newsItemStory,
                isLoading = false,
                errorMessages = emptyList(),
            )
        }
    }

    @Test
    fun `refreshStory should append an error message to uiState if StoryDetailRepository is not returning the Story requested`() {
        scope.runTest {
            every { mockStateHandle.get<Int>("list_id") } returns 1
            fakeStoryDetailRepository.getStoryResponse = Result.failure(exception = StoryNotFoundException())

            setupViewModel()
            dispatcher.scheduler.advanceUntilIdle()

            val uiState = storyDetailViewModel.uiState.first()
            uiState.story shouldBe null
            uiState.isLoading shouldBe false
            uiState.errorMessages shouldHaveSize 1 // error message id is a random number we cannot assert
            uiState.errorMessages[0].messageId shouldBe R.string.error_loading_story_detail
        }
    }

    @Test
    fun `Should remove the error message from the list after calling`() {
        scope.runTest {
            every { mockStateHandle.get<Int>("list_id") } returns 1
            fakeStoryDetailRepository.getStoryResponse = Result.failure(exception = StoryNotFoundException())
            setupViewModel()
            dispatcher.scheduler.advanceUntilIdle()

            val originalUiState = storyDetailViewModel.uiState.first()
            originalUiState.errorMessages shouldHaveSize 1

            storyDetailViewModel.errorShown(errorId = originalUiState.errorMessages[0].id)

            val uiState = storyDetailViewModel.uiState.first()
            uiState.errorMessages shouldHaveSize 0
        }
    }
}
