/*
 * Copyright (c) 2024. Ryan Wong (hello@ryanwong.co.uk)
 */

package uk.ryanwong.catnews.ui.screens.weblink

data class WebLinkUIEvent(
    val onErrorShown: (errorId: Long) -> Unit,
)
