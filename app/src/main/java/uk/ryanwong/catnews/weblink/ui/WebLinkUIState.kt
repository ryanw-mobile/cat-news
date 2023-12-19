/*
 * Copyright (c) 2022. Ryan Wong (hello@ryanwong.co.uk)
 */

package uk.ryanwong.catnews.weblink.ui

import uk.ryanwong.catnews.app.util.ErrorMessage

data class WebLinkUIState(
    val url: String? = null,
    val isLoading: Boolean = false,
    val errorMessages: List<ErrorMessage> = emptyList(),
)
