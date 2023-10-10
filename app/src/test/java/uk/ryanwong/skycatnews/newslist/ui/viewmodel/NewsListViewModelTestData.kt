/*
 * Copyright (c) 2022. Ryan Wong (hello@ryanwong.co.uk)
 */

package uk.ryanwong.skycatnews.newslist.ui.viewmodel

import uk.ryanwong.skycatnews.domain.model.newslist.NewsItem
import uk.ryanwong.skycatnews.domain.model.newslist.NewsList

internal object NewsListViewModelTestData {

    val mockNewsList by lazy {
        NewsList(
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
    }

    val mockNewsItem by lazy {
        NewsItem.Story(
            newsId = 1,
            headline = "some-headline",
            teaserText = "some-teaser-text",
            modifiedDate = "2020-11-19T00:00:00Z",
            niceDate = "2 days ago",
            teaserImageUrl = "https://some.url/href",
            teaserImageAccessibilityText = "some-accessibility-text",
        )
    }
}
