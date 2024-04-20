/*
 * Copyright (c) 2024. Ryan Wong (hello@ryanwong.co.uk)
 */

package uk.ryanwong.catnews.newslist.ui.viewmodel

import androidx.lifecycle.SavedStateHandle
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
import org.junit.Before
import org.junit.Test
import uk.ryanwong.catnews.R
import uk.ryanwong.catnews.newslist.data.repository.FakeNewsListRepository
import uk.ryanwong.catnews.ui.screens.weblink.WebLinkUIState
import uk.ryanwong.catnews.ui.viewmodel.WebLinkViewModel

@OptIn(ExperimentalCoroutinesApi::class)
internal class WebLinkViewModelTest {

    private lateinit var scope: TestScope
    private lateinit var dispatcher: TestDispatcher
    private lateinit var webLinkViewModel: WebLinkViewModel
    private lateinit var fakeNewsListRepository: FakeNewsListRepository
    private lateinit var mockStateHandle: SavedStateHandle

    /***
     * Test plan: this viewModel works by initialising itself at creation time.
     * We can only test it by mocking the stateHandle object and observe its UIState.
     *
     * This test behaves differently from other tests, in the way that we can only
     * initialise it under the "WHEN" stage, because we need to have everything properly
     * mocked before triggering its init()
     */

    @Before
    fun setupTest() {
        dispatcher = UnconfinedTestDispatcher()
        scope = TestScope(dispatcher)
        mockStateHandle = mockk()
        fakeNewsListRepository = FakeNewsListRepository()
    }

    private fun setupViewModel() {
        webLinkViewModel = WebLinkViewModel(
            stateHandle = mockStateHandle,
            newsListRepository = fakeNewsListRepository,
            dispatcher = dispatcher,
        )
    }

    @Test
    fun `init should be able to pass the stateHandle list_id to newsListRepository and make requests`() {
        scope.runTest {
            every { mockStateHandle.get<Int>("list_id") } returns 521
            fakeNewsListRepository.getNewsItemResponse = Result.success(WebLinkViewModelTestData.newsItemWebLink)

            setupViewModel()

            fakeNewsListRepository.getNewsItemNewsId shouldBe 521
        }
    }

    @Test
    fun `init should be able to pass the correct URL to the uiState if able to get NewsItem from newsListRepository`() {
        scope.runTest {
            every { mockStateHandle.get<Int>("list_id") } returns 2
            fakeNewsListRepository.getNewsItemResponse = Result.success(WebLinkViewModelTestData.newsItemWebLink)

            setupViewModel()

            val uiState = webLinkViewModel.uiState.first()
            uiState shouldBe WebLinkUIState(
                url = "https://some.weblink.url/2",
                isLoading = false,
                errorMessages = emptyList(),
            )
        }
    }

    @Test
    fun `init should append an error message to uiState if newsListRepository is not returning the NewsItem requested`() {
        scope.runTest {
            every { mockStateHandle.get<Int>("list_id") } returns 1
            fakeNewsListRepository.getNewsItemResponse = Result.failure(Exception("some-exception"))

            setupViewModel()

            val uiState = webLinkViewModel.uiState.first()
            with(uiState) {
                url shouldBe null
                isLoading shouldBe false
                errorMessages shouldHaveSize 1 // error message id is a random number we cannot assert
                errorMessages[0].messageId shouldBe R.string.error_loading_web_link
            }
        }
    }

    @Test
    fun `Should remove the error message from the list after calling`() {
        scope.runTest {
            every { mockStateHandle.get<Int>("list_id") } returns 1
            fakeNewsListRepository.getNewsItemResponse = Result.success(WebLinkViewModelTestData.newsItemStory)
            setupViewModel()

            val originalUiState = webLinkViewModel.uiState.first()
            originalUiState.errorMessages shouldHaveSize 1

            webLinkViewModel.errorShown(errorId = originalUiState.errorMessages[0].id)

            val uiState = webLinkViewModel.uiState.first()
            uiState.errorMessages shouldHaveSize 0
        }
    }
}
