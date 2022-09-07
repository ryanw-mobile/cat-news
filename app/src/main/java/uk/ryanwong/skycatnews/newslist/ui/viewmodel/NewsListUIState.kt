/*
 * Copyright (c) 2022. Ryan Wong (hello@ryanwong.co.uk)
 */

package uk.ryanwong.skycatnews.newslist.ui.viewmodel

import uk.ryanwong.skycatnews.app.util.ErrorMessage
import uk.ryanwong.skycatnews.newslist.domain.model.NewsItem

data class NewsListUIState(
    val newsList: List<NewsItem> = emptyList(),
    val isLoading: Boolean = false,
    val errorMessages: List<ErrorMessage> = emptyList(),
)
