/*
 * Copyright (c) 2023. Ryan Wong (hello@ryanwong.co.uk)
 */

package uk.ryanwong.skycatnews.newslist.ui

data class NewsListUIEvent(
    val onStoryItemClicked: (id: Int) -> Unit,
    val onWebLinkItemClicked: (id: Int) -> Unit,
    val onRefresh: () -> Unit,
    val onErrorShown: (errorId: Long) -> Unit,
)
