/*
 * Copyright (c) 2022. Ryan Wong (hello@ryanwong.co.uk)
 */

package uk.ryanwong.skycatnews.weblink.ui.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import java.util.UUID
import javax.inject.Inject
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import uk.ryanwong.skycatnews.R
import uk.ryanwong.skycatnews.app.di.DispatcherModule
import uk.ryanwong.skycatnews.app.util.ErrorMessage
import uk.ryanwong.skycatnews.domain.model.newslist.NewsItem
import uk.ryanwong.skycatnews.newslist.data.repository.NewsListRepository
import uk.ryanwong.skycatnews.weblink.ui.WebLinkUIState

@HiltViewModel
class WebLinkViewModel @Inject constructor(
    stateHandle: SavedStateHandle,
    private val newsListRepository: NewsListRepository,
    @DispatcherModule.MainDispatcher private val dispatcher: CoroutineDispatcher,
) : ViewModel() {

    /***
     * The Webview works so independently that not much we can do after feeding it an URL.
     */
    private var newsId = 0

    private val _uiState = MutableStateFlow(WebLinkUIState())
    var uiState = _uiState.asStateFlow()

    init {
        newsId = stateHandle.get<Int>("list_id") ?: 0
        getNewsItem()
    }

    private fun getNewsItem() {
        _uiState.update { currentUiState ->
            currentUiState.copy(isLoading = true)
        }

        viewModelScope.launch(dispatcher) {
            newsListRepository.getNewsItem(newsId = newsId).onSuccess { newsItem ->
                if (newsItem is NewsItem.WebLink) {
                    _uiState.update { currentUiState ->
                        currentUiState.copy(
                            url = newsItem.url,
                            isLoading = false
                        )
                    }
                } else {
                    setErrorUIState()
                }
            }.onFailure { ex ->
                ex.printStackTrace()
                setErrorUIState()
            }
        }
    }

    private fun setErrorUIState() {
        _uiState.update { currentUiState ->
            val errorMessages = currentUiState.errorMessages + ErrorMessage(
                id = UUID.randomUUID().mostSignificantBits,
                messageId = R.string.error_loading_web_link
            )
            currentUiState.copy(
                isLoading = false,
                errorMessages = errorMessages
            )
        }
    }

    fun errorShown(errorId: Long) {
        _uiState.update { currentUiState ->
            val errorMessages = currentUiState.errorMessages.filterNot { it.id == errorId }
            currentUiState.copy(errorMessages = errorMessages)
        }
    }
}
