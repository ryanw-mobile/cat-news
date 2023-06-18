/*
 * Copyright (c) 2022. Ryan Wong (hello@ryanwong.co.uk)
 */

package uk.ryanwong.skycatnews.newslist.ui.viewmodel

import uk.ryanwong.skycatnews.uk.ryanwong.skycatnews.domain.model.newslist.NewsItem

internal object WebLinkViewModelTestData {

    val mockNewsItemStory by lazy {
        NewsItem.Story(
            newsId = 521,
            headline = "some-headline",
            teaserText = "some-teaser-text",
            modifiedDate = "2022-05-21T00:00:00Z",
            niceDate = "2 days ago",
            teaserImageUrl = "https://some.teaser.image/href",
            teaserImageAccessibilityText = "some-teaser-image-accessibility-text",
        )
    }

    val mockNewsItemWebLink by lazy {
        NewsItem.WebLink(
            newsId = 2,
            headline = "some-headline-2",
            modifiedDate = "2022-05-21T00:00:01Z",
            niceDate = "2 days ago",
            teaserImageUrl = "https://some.teaser.image/href/2",
            teaserImageAccessibilityText = "some-teaser-image-accessibility-text-2",
            url = "https://some.weblink.url/2"
        )
    }
}
