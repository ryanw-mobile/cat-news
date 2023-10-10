/*
 * Copyright (c) 2022. Ryan Wong (hello@ryanwong.co.uk)
 */

package uk.ryanwong.skycatnews.newslist.ui.screen

import android.content.res.Configuration.UI_MODE_NIGHT_NO
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.SnackbarHost
import androidx.compose.material.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import uk.ryanwong.skycatnews.R
import uk.ryanwong.skycatnews.app.ui.theme.SkyCatNewsTheme
import uk.ryanwong.skycatnews.domain.model.newslist.NewsItem
import uk.ryanwong.skycatnews.newslist.ui.NewsListUIEvent
import uk.ryanwong.skycatnews.newslist.ui.NewsListUIState
import uk.ryanwong.skycatnews.newslist.ui.screen.component.NewsListScreenLayout
import uk.ryanwong.skycatnews.newslist.ui.screen.previewparameter.NewsListProvider

@Composable
fun NewsListScreen(
    modifier: Modifier = Modifier,
    uiState: NewsListUIState,
    uiEvent: NewsListUIEvent,
) {
    val snackbarHostState = remember { SnackbarHostState() }

    Box(modifier = modifier.fillMaxSize()) {
        NewsListScreenLayout(
            newsList = uiState.newsList,
            isLoading = uiState.isLoading,
            onRefresh = uiEvent.onRefresh,
            onStoryItemClicked = uiEvent.onStoryItemClicked,
            onWebLinkItemClicked = uiEvent.onWebLinkItemClicked,
        )

        SnackbarHost(
            hostState = snackbarHostState,
            modifier = Modifier.align(Alignment.BottomCenter),
        )
    }

    if (uiState.errorMessages.isNotEmpty()) {
        val errorMessage = remember(uiState) { uiState.errorMessages[0] }
        val errorMessageText = stringResource(errorMessage.messageId)
        val actionLabel = stringResource(R.string.ok)

        LaunchedEffect(errorMessage.id) {
            snackbarHostState.showSnackbar(
                message = errorMessageText,
                actionLabel = actionLabel,
            )
            uiEvent.onErrorShown(errorMessage.id)
        }
    }
}

@Preview(
    name = "News List Screen",
    group = "Light",
    showBackground = true,
    uiMode = UI_MODE_NIGHT_NO,
)
@Preview(
    name = "News List Screen",
    group = "Dark",
    showBackground = true,
    uiMode = UI_MODE_NIGHT_YES,
)
@Composable
private fun NewsListScreenPreview(
    @PreviewParameter(NewsListProvider::class)
    newsList: List<NewsItem>,
) {
    SkyCatNewsTheme {
        NewsListScreen(
            uiState = NewsListUIState(
                newsList = newsList,
            ),
            uiEvent = NewsListUIEvent(
                onStoryItemClicked = {},
                onWebLinkItemClicked = {},
                onRefresh = {},
                onErrorShown = {},
            ),
        )
    }
}

@Preview(
    name = "News List Screen No Data",
    group = "Light",
    showBackground = true,
    uiMode = UI_MODE_NIGHT_NO,
)
@Preview(
    name = "News List Screen No Data",
    group = "Dark",
    showBackground = true,
    uiMode = UI_MODE_NIGHT_YES,
)
@Composable
private fun NewsListScreenNoDataPreview() {
    SkyCatNewsTheme {
        NewsListScreen(
            uiState = NewsListUIState(
                newsList = emptyList(),
            ),
            uiEvent = NewsListUIEvent(
                onStoryItemClicked = {},
                onWebLinkItemClicked = {},
                onRefresh = {},
                onErrorShown = {},
            ),
        )
    }
}
