/*
 * Copyright (c) 2022. Ryan Wong (hello@ryanwong.co.uk)
 */

package uk.ryanwong.catnews.newslist.ui.viewmodel

import androidx.lifecycle.SavedStateHandle
import io.kotest.core.spec.style.FreeSpec
import io.kotest.matchers.collections.shouldHaveSize
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.TestDispatcher
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import uk.ryanwong.catnews.R
import uk.ryanwong.catnews.newslist.data.repository.MockNewsListRepository
import uk.ryanwong.catnews.weblink.ui.WebLinkUIState
import uk.ryanwong.catnews.weblink.ui.viewmodel.WebLinkViewModel

@OptIn(ExperimentalCoroutinesApi::class)
internal class WebLinkViewModelTest : FreeSpec() {

    private lateinit var scope: TestScope
    private lateinit var dispatcher: TestDispatcher
    private lateinit var webLinkViewModel: WebLinkViewModel
    private lateinit var mockNewsListRepository: MockNewsListRepository
    private lateinit var mockStateHandle: SavedStateHandle

    /***
     * Test plan: this viewModel works by initialising itself at creation time.
     * We can only test it by mocking the stateHandle object and observe its UIState.
     *
     * This test behaves differently from other tests, in the way that we can only
     * initialise it under the "WHEN" stage, because we need to have everything properly
     * mocked before triggering its init()
     */

    private fun setupTest() {
        dispatcher = UnconfinedTestDispatcher()
        scope = TestScope(dispatcher)
        mockStateHandle = mockk()
        mockNewsListRepository = MockNewsListRepository()
    }

    private fun setupViewModel() {
        webLinkViewModel = WebLinkViewModel(
            stateHandle = mockStateHandle,
            newsListRepository = mockNewsListRepository,
            dispatcher = dispatcher,
        )
    }

    init {
        "init" - {
            "Should be able to pass the stateHandle list_id to newsListRepository and make requests" {
                setupTest()
                scope.runTest {
                    // Given
                    every { mockStateHandle.get<Int>("list_id") } returns 521
                    mockNewsListRepository.mockGetNewsItemResponse =
                        Result.success(WebLinkViewModelTestData.mockNewsItemWebLink)

                    // When
                    setupViewModel()

                    // Then
                    mockNewsListRepository.mockGetNewsItemNewsId shouldBe 521
                }
            }

            "Should be able to pass the correct URL to the uiState if able to get NewsItem from newsListRepository" {
                setupTest()
                scope.runTest {
                    // Given
                    every { mockStateHandle.get<Int>("list_id") } returns 2
                    mockNewsListRepository.mockGetNewsItemResponse =
                        Result.success(WebLinkViewModelTestData.mockNewsItemWebLink)

                    // When
                    setupViewModel()

                    // Then
                    val uiState = webLinkViewModel.uiState.first()
                    uiState shouldBe WebLinkUIState(
                        url = "https://some.weblink.url/2",
                        isLoading = false,
                        errorMessages = emptyList(),
                    )
                }
            }

            "Should append an error message to uiState if newsListRepository is not returning the NewsItem requested" {
                setupTest()
                scope.runTest {
                    // Given
                    every { mockStateHandle.get<Int>("list_id") } returns 1
                    mockNewsListRepository.mockGetNewsItemResponse =
                        Result.success(WebLinkViewModelTestData.mockNewsItemStory)

                    // When
                    setupViewModel()

                    // Then
                    val uiState = webLinkViewModel.uiState.first()
                    uiState.url shouldBe null
                    uiState.isLoading shouldBe false
                    uiState.errorMessages shouldHaveSize 1 // error message id is a random number we cannot assert
                    uiState.errorMessages[0].messageId shouldBe R.string.error_loading_web_link
                }
            }
        }

        "errorShown" - {
            "Should remove the error message from the list after calling" {
                setupTest()
                scope.runTest {
                    // Given
                    every { mockStateHandle.get<Int>("list_id") } returns 1
                    mockNewsListRepository.mockGetNewsItemResponse =
                        Result.success(WebLinkViewModelTestData.mockNewsItemStory)
                    setupViewModel()

                    val originalUiState = webLinkViewModel.uiState.first()
                    originalUiState.errorMessages shouldHaveSize 1

                    // When
                    webLinkViewModel.errorShown(errorId = originalUiState.errorMessages[0].id)

                    // Then
                    val uiState = webLinkViewModel.uiState.first()
                    uiState.errorMessages shouldHaveSize 0
                }
            }
        }
    }
}
