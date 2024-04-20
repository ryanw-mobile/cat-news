/*
 * Copyright (c) 2024. Ryan Wong (hello@ryanwong.co.uk)
 */

package uk.ryanwong.catnews.ui.screens.newslist

data class NewsListUIEvent(
    val onStoryItemClicked: (id: Int) -> Unit,
    val onWebLinkItemClicked: (id: Int) -> Unit,
    val onRefresh: () -> Unit,
    val onErrorShown: (errorId: Long) -> Unit,
)
