/*
 * Copyright (c) 2023. Ryan Wong (hello@ryanwong.co.uk)
 */

package uk.ryanwong.catnews.weblink.ui

data class WebLinkUIEvent(
    val onErrorShown: (errorId: Long) -> Unit,
)
