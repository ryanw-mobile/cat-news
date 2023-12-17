/*
 * Copyright (c) 2022. Ryan Wong (hello@ryanwong.co.uk)
 */

package uk.ryanwong.skycatnews.newslist.ui.viewmodel

import io.kotest.core.spec.style.FreeSpec
import io.kotest.matchers.collections.shouldHaveSize
import io.kotest.matchers.shouldBe
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.TestDispatcher
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import uk.ryanwong.skycatnews.R
import uk.ryanwong.skycatnews.newslist.data.repository.MockNewsListRepository
import uk.ryanwong.skycatnews.newslist.ui.NewsListUIState
import java.io.IOException

@OptIn(ExperimentalCoroutinesApi::class)
internal class NewsListViewModelTest : FreeSpec() {

    private lateinit var scope: TestScope
    private lateinit var dispatcher: TestDispatcher
    private lateinit var newsListViewModel: NewsListViewModel
    private lateinit var mockNewsListRepository: MockNewsListRepository

    private fun setupTest() {
        dispatcher = UnconfinedTestDispatcher()
        scope = TestScope(dispatcher)
        mockNewsListRepository = MockNewsListRepository()
    }

    private fun setupViewModel() {
        newsListViewModel = NewsListViewModel(
            newsListRepository = mockNewsListRepository,
            dispatcher = dispatcher,
        )
    }

    init {
        // This view model calls refreshNewsList() at init(), so we do not have to explicitly run this in our tests
        "refreshNewsList" - {
            "Should be able to pass the correct URL to the uiState if able to get NewsList from newsListRepository" {
                setupTest()
                scope.runTest {
                    // Given
                    mockNewsListRepository.mockGetNewsListResponse =
                        Result.success(NewsListViewModelTestData.mockNewsList)

                    // When
                    setupViewModel()

                    // Then
                    val uiState = newsListViewModel.uiState.first()
                    uiState shouldBe NewsListUIState(
                        newsList = listOf(NewsListViewModelTestData.mockNewsItem),
                        isLoading = false,
                        errorMessages = emptyList(),
                    )
                }
            }

            "Should append an error message to uiState if newsListRepository is not returning the NewsList requested" {
                setupTest()
                scope.runTest {
                    // Given
                    mockNewsListRepository.mockGetNewsListResponse = Result.failure(IOException())

                    // When
                    setupViewModel()

                    // Then
                    val uiState = newsListViewModel.uiState.first()
                    uiState.newsList shouldBe emptyList()
                    uiState.isLoading shouldBe false
                    uiState.errorMessages shouldHaveSize 1 // error message id is a random number we cannot assert
                    uiState.errorMessages[0].messageId shouldBe R.string.error_loading_news_list
                }
            }
        }

        "errorShown" - {
            "Should remove the error message from the list after calling" {
                setupTest()
                scope.runTest {
                    // Given
                    mockNewsListRepository.mockGetNewsListResponse = Result.failure(Exception())
                    setupViewModel()

                    val originalUiState = newsListViewModel.uiState.first()
                    originalUiState.errorMessages shouldHaveSize 1

                    // When
                    newsListViewModel.errorShown(errorId = originalUiState.errorMessages[0].id)

                    // Then
                    val uiState = newsListViewModel.uiState.first()
                    uiState.errorMessages shouldHaveSize 0
                }
            }
        }
    }
}
