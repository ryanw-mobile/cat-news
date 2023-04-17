/*
 * Copyright (c) 2022. Ryan Wong (hello@ryanwong.co.uk)
 */

package uk.ryanwong.skycatnews.weblink.ui.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.SnackbarHost
import androidx.compose.material.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import com.google.accompanist.web.WebView
import com.google.accompanist.web.rememberWebViewState
import uk.ryanwong.skycatnews.R
import uk.ryanwong.skycatnews.uk.ryanwong.skycatnews.weblink.ui.WebLinkUIEvent
import uk.ryanwong.skycatnews.uk.ryanwong.skycatnews.weblink.ui.WebLinkUIState

@Composable
fun WebLinkScreen(
    modifier: Modifier = Modifier,
    uiState: WebLinkUIState,
    uiEvent: WebLinkUIEvent,
) {
    val snackbarHostState = remember { SnackbarHostState() }

    Box(modifier = modifier.fillMaxSize()) {
        if (uiState.isLoading) {
            CircularProgressIndicator()
        } else {
            uiState.url?.let { webLinkUrl ->
                WebLinkScreenLayout(
                    url = webLinkUrl,
                )
            }
        }

        SnackbarHost(
            hostState = snackbarHostState,
            modifier = Modifier.align(Alignment.BottomCenter)
        )
    }

    if (uiState.errorMessages.isNotEmpty()) {
        val errorMessage = remember(uiState) { uiState.errorMessages[0] }
        val errorMessageText = stringResource(errorMessage.messageId)
        val actionLabel = stringResource(R.string.ok)

        LaunchedEffect(errorMessage.id) {
            snackbarHostState.showSnackbar(
                message = errorMessageText,
                actionLabel = actionLabel
            )
            uiEvent.onErrorShown(errorMessage.id)
        }
    }
}

@Composable
fun WebLinkScreenLayout(
    modifier: Modifier = Modifier,
    url: String,
) {
    val webViewState = rememberWebViewState(url)
    val contentDescriptionWeblinkWebView =
        stringResource(R.string.content_description_weblink_webview)

    Column(modifier = modifier.fillMaxSize()) {
        WebView(
            state = webViewState,
            onCreated = { it.settings.javaScriptEnabled = true },
            modifier = Modifier
                .fillMaxSize()
                .semantics { contentDescription = contentDescriptionWeblinkWebView },
        )
    }
}
