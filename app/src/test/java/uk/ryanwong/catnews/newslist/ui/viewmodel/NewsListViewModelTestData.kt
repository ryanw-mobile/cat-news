/*
 * Copyright (c) 2024. Ryan Wong (hello@ryanwong.co.uk)
 */

package uk.ryanwong.catnews.newslist.ui.viewmodel

import uk.ryanwong.catnews.domain.model.newslist.NewsItem
import uk.ryanwong.catnews.domain.model.newslist.NewsList

internal object NewsListViewModelTestData {

    val newsList = NewsList(
        title = "some-title",
        newsItems = listOf(
            NewsItem.Story(
                newsId = 1,
                headline = "some-headline",
                teaserText = "some-teaser-text",
                modifiedDate = "2020-11-19T00:00:00Z",
                niceDate = "2 days ago",
                teaserImageUrl = "https://some.url/href",
                teaserImageAccessibilityText = "some-accessibility-text",
            ),
        ),
    )

    val newsItem = NewsItem.Story(
        newsId = 1,
        headline = "some-headline",
        teaserText = "some-teaser-text",
        modifiedDate = "2020-11-19T00:00:00Z",
        niceDate = "2 days ago",
        teaserImageUrl = "https://some.url/href",
        teaserImageAccessibilityText = "some-accessibility-text",
    )
}
