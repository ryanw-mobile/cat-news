/*
 * Copyright (c) 2023. Ryan Wong (hello@ryanwong.co.uk)
 */

package uk.ryanwong.skycatnews.weblink.ui

data class WebLinkUIEvent(
    val onErrorShown: (errorId: Long) -> Unit,
)
