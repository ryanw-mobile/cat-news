/*
 * Copyright (c) 2022. Ryan Wong (hello@ryanwong.co.uk)
 */

package uk.ryanwong.catnews.newslist.ui

import uk.ryanwong.catnews.app.util.ErrorMessage
import uk.ryanwong.catnews.domain.model.newslist.NewsItem

data class NewsListUIState(
    val newsList: List<NewsItem> = emptyList(),
    val isLoading: Boolean = false,
    val errorMessages: List<ErrorMessage> = emptyList(),
)
