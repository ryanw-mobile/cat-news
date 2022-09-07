/*
 * Copyright (c) 2022. Ryan Wong (hello@ryanwong.co.uk)
 */

package uk.ryanwong.skycatnews.newslist.ui.viewmodel

import uk.ryanwong.skycatnews.newslist.domain.model.NewsItem
import uk.ryanwong.skycatnews.newslist.domain.model.NewsList

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
                    teaserImageUrl = "https://some.url/href",
                    teaserImageAccessibilityText = "some-accessibility-text",
                )
            )
        )
    }

    val mockNewsItem by lazy {
        NewsItem.Story(
            newsId = 1,
            headline = "some-headline",
            teaserText = "some-teaser-text",
            modifiedDate = "2020-11-19T00:00:00Z",
            teaserImageUrl = "https://some.url/href",
            teaserImageAccessibilityText = "some-accessibility-text",
        )
    }
}
