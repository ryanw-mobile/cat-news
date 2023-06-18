/*
 * Copyright (c) 2022. Ryan Wong (hello@ryanwong.co.uk)
 */

package uk.ryanwong.skycatnews.uk.ryanwong.skycatnews.newslist.ui

import uk.ryanwong.skycatnews.app.util.ErrorMessage
import uk.ryanwong.skycatnews.uk.ryanwong.skycatnews.domain.model.newslist.NewsItem

data class NewsListUIState(
    val newsList: List<NewsItem> = emptyList(),
    val isLoading: Boolean = false,
    val errorMessages: List<ErrorMessage> = emptyList(),
)
