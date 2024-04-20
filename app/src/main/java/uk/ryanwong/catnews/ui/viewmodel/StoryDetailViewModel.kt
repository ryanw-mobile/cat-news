/*
 * Copyright (c) 2024. Ryan Wong (hello@ryanwong.co.uk)
 */

package uk.ryanwong.catnews.ui.viewmodel

import androidx.lifecycle.SavedStateHandle
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
import uk.ryanwong.catnews.domain.repository.interfaces.StoryDetailRepository
import uk.ryanwong.catnews.ui.screens.storydetail.StoryDetailUIState
import uk.ryanwong.catnews.ui.util.ErrorMessage
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class StoryDetailViewModel @Inject constructor(
    stateHandle: SavedStateHandle,
    private val storyDetailRepository: StoryDetailRepository,
    @DispatcherModule.MainDispatcher private val dispatcher: CoroutineDispatcher,
) : ViewModel() {

    private var storyId = 0

    private val _uiState = MutableStateFlow(StoryDetailUIState())
    val uiState = _uiState.asStateFlow()

    init {
        storyId = stateHandle.get<Int>("list_id") ?: 0
        refreshStory()
    }

    fun refreshStory() {
        _uiState.update { currentUiState ->
            currentUiState.copy(isLoading = true)
        }

        viewModelScope.launch(dispatcher) {
            storyDetailRepository.getStory(storyId = storyId).onSuccess { story ->
                _uiState.update { currentUiState ->
                    currentUiState.copy(
                        story = story,
                        isLoading = false,
                    )
                }
            }.onFailure { ex ->
                ex.printStackTrace()
                _uiState.update { currentUiState ->
                    val errorMessages = currentUiState.errorMessages + ErrorMessage(
                        id = UUID.randomUUID().mostSignificantBits,
                        messageId = R.string.error_loading_story_detail,
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
