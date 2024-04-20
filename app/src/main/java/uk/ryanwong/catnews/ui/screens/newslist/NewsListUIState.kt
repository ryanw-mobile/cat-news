/*
 * Copyright (c) 2024. Ryan Wong (hello@ryanwong.co.uk)
 */

package uk.ryanwong.catnews.ui.screens.newslist

import uk.ryanwong.catnews.domain.model.newslist.NewsItem
import uk.ryanwong.catnews.ui.util.ErrorMessage

data class NewsListUIState(
    val newsList: List<NewsItem> = emptyList(),
    val isLoading: Boolean = false,
    val errorMessages: List<ErrorMessage> = emptyList(),
)
