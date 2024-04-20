/*
 * Copyright (c) 2024. Ryan Wong (hello@ryanwong.co.uk)
 */

package uk.ryanwong.catnews.ui.screens.weblink

import uk.ryanwong.catnews.ui.util.ErrorMessage

data class WebLinkUIState(
    val url: String? = null,
    val isLoading: Boolean = false,
    val errorMessages: List<ErrorMessage> = emptyList(),
)
