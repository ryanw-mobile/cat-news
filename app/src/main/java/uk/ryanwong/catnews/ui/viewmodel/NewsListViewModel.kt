/*
 * Copyright (c) 2024. Ryan Wong (hello@ryanwong.co.uk)
 */

package uk.ryanwong.catnews.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import uk.ryanwong.catnews.R
import uk.ryanwong.catnews.di.DispatcherModule
import uk.ryanwong.catnews.domain.repository.interfaces.NewsListRepository
import uk.ryanwong.catnews.ui.screens.newslist.NewsListUIState
import uk.ryanwong.catnews.ui.util.ErrorMessage
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class NewsListViewModel @Inject constructor(
    private val newsListRepository: NewsListRepository,
    @DispatcherModule.MainDispatcher private val dispatcher: CoroutineDispatcher,
) : ViewModel() {

    private val _uiState = MutableStateFlow(NewsListUIState())
    val uiState = _uiState.asStateFlow()

    init {
        refreshNewsList()
    }

    fun refreshNewsList() {
        _uiState.update { currentUiState ->
            currentUiState.copy(isLoading = true)
        }

        viewModelScope.launch(dispatcher) {
            newsListRepository.getNewsList().onSuccess { newsListResult ->
                _uiState.update { currentUiState ->
                    currentUiState.copy(
                        newsList = newsListResult.newsItems,
                        isLoading = false,
                    )
                }
            }.onFailure { ex ->
                ex.printStackTrace()
                _uiState.update { currentUiState ->
                    val errorMessages = currentUiState.errorMessages + ErrorMessage(
                        id = UUID.randomUUID().mostSignificantBits,
                        messageId = R.string.error_loading_news_list,
                    )
                    currentUiState.copy(
                        isLoading = false,
                        errorMessages = errorMessages,
                    )
                }
            }
        }
    }

    fun errorShown(errorId: Long) {
        _uiState.update { currentUiState ->
            val errorMessages = currentUiState.errorMessages.filterNot { it.id == errorId }
            currentUiState.copy(errorMessages = errorMessages)
        }
    }
}
