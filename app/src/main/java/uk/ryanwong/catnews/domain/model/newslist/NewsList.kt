/*
 * Copyright (c) 2023. Ryan Wong (hello@ryanwong.co.uk)
 */

package uk.ryanwong.catnews.domain.model.newslist

data class NewsList(
    val title: String,
    val newsItems: List<NewsItem>,
) {
    fun isEmpty(): Boolean {
        return newsItems.isEmpty()
    }
}
