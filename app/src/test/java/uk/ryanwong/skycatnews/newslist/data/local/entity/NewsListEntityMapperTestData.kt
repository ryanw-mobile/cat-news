/*
 * Copyright (c) 2023. Ryan Wong (hello@ryanwong.co.uk)
 */

package uk.ryanwong.skycatnews.newslist.data.local.entity

import uk.ryanwong.skycatnews.uk.ryanwong.skycatnews.domain.model.newslist.NewsItem

internal object NewsListEntityMapperTestData {

    val mockNewsItemEntityStory by lazy {
        NewsItemEntity(
            listId = 1,
            newsId = 1,
            type = "story",
            headline = "some-headline",
            creationDate = "2022-05-21T00:00:00Z",
            modifiedDate = "2022-05-21T00:00:00Z",
            advertUrl = "https://some.url/",
            weblinkUrl = "https://some.weblink.url/",
            teaserText = "some-teaser-text",
            teaserImageHref = "https://some.teaser.image/href",
            teaserImageTemplated = true,
            teaserImageType = "some-teaser-image-type",
            teaserImageAccessibilityText = "some-teaser-image-accessibility-text"
        )
    }

    val mockNewsItemEntityWebLink by lazy {
        NewsItemEntity(
            listId = 1,
            newsId = 2,
            type = "weblink",
            headline = "some-headline-2",
            creationDate = "2022-05-21T00:00:01Z",
            modifiedDate = "2022-05-21T00:00:01Z",
            advertUrl = "https://some.url/2",
            weblinkUrl = "https://some.weblink.url/2",
            teaserText = "some-teaser-text-2",
            teaserImageHref = "https://some.teaser.image/href/2",
            teaserImageTemplated = true,
            teaserImageType = "some-teaser-image-type-2",
            teaserImageAccessibilityText = "some-teaser-image-accessibility-text-2"
        )
    }

    val mockNewsItemEntityUnknown by lazy {
        NewsItemEntity(
            listId = 1,
            newsId = 3,
            type = "some-type-3",
            headline = "some-headline-3",
            creationDate = "2022-05-21T00:00:02Z",
            modifiedDate = "2022-05-21T00:00:02Z",
            advertUrl = "https://some.url/3",
            weblinkUrl = "https://some.weblink.url/3",
            teaserText = "some-teaser-text-3",
            teaserImageHref = "https://some.teaser.image/href/3",
            teaserImageTemplated = true,
            teaserImageType = "some-teaser-image-type-3",
            teaserImageAccessibilityText = "some-teaser-image-accessibility-text-3"
        )
    }

    val mockNewsItemStory by lazy {
        NewsItem.Story(
            newsId = 1,
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
