/*
 * Copyright (c) 2022. Ryan Wong (hello@ryanwong.co.uk)
 */

package uk.ryanwong.catnews.newslist.ui.viewmodel

import io.kotest.matchers.collections.shouldHaveSize
import io.kotest.matchers.shouldBe
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.TestDispatcher
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import uk.ryanwong.catnews.R
import uk.ryanwong.catnews.newslist.data.repository.FakeNewsListRepository
import uk.ryanwong.catnews.ui.screens.newslist.NewsListUIState
import uk.ryanwong.catnews.ui.viewmodel.NewsListViewModel
import java.io.IOException

internal class NewsListViewModelTest {

    private lateinit var scope: TestScope
    private lateinit var dispatcher: TestDispatcher
    private lateinit var newsListViewModel: NewsListViewModel
    private lateinit var fakeNewsListRepository: FakeNewsListRepository

    @OptIn(ExperimentalCoroutinesApi::class)
    @Before
    fun setupTest() {
        dispatcher = UnconfinedTestDispatcher()
        scope = TestScope(dispatcher)
        fakeNewsListRepository = FakeNewsListRepository()
    }

    private fun setupViewModel() {
        newsListViewModel = NewsListViewModel(
            newsListRepository = fakeNewsListRepository,
            dispatcher = dispatcher,
        )
    }

    // This view model calls refreshNewsList() at init(), so we do not have to explicitly run this in our tests
    @Test
    fun `refreshNewsList should be able to pass the correct URL to the uiState if able to get NewsList from newsListRepository`() {
        scope.runTest {
            fakeNewsListRepository.getNewsListResponse = Result.success(NewsListViewModelTestData.newsList)

            setupViewModel()

            val uiState = newsListViewModel.uiState.first()
            uiState shouldBe NewsListUIState(
                newsList = listOf(NewsListViewModelTestData.newsItem),
                isLoading = false,
                errorMessages = emptyList(),
            )
        }
    }

    @Test
    fun `refreshNewsList should append an error message to uiState if newsListRepository is not returning the NewsList requested`() {
        scope.runTest {
            fakeNewsListRepository.getNewsListResponse = Result.failure(IOException())

            setupViewModel()

            val uiState = newsListViewModel.uiState.first()
            uiState.newsList shouldBe emptyList()
            uiState.isLoading shouldBe false
            uiState.errorMessages shouldHaveSize 1 // error message id is a random number we cannot assert
            uiState.errorMessages[0].messageId shouldBe R.string.error_loading_news_list
        }
    }

    @Test
    fun `Should remove the error message from the list after calling`() {
        scope.runTest {
            fakeNewsListRepository.getNewsListResponse = Result.failure(Exception())
            setupViewModel()

            val originalUiState = newsListViewModel.uiState.first()
            originalUiState.errorMessages shouldHaveSize 1

            newsListViewModel.errorShown(errorId = originalUiState.errorMessages[0].id)

            val uiState = newsListViewModel.uiState.first()
            uiState.errorMessages shouldHaveSize 0
        }
    }
}
