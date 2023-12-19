/*
 * Copyright (c) 2022. Ryan Wong (hello@ryanwong.co.uk)
 */

package uk.ryanwong.catnews.newslist.data.local.entity

import uk.ryanwong.catnews.newslist.data.remote.model.LinksDto
import uk.ryanwong.catnews.newslist.data.remote.model.NewsItemDto
import uk.ryanwong.catnews.newslist.data.remote.model.TeaserImageDto
import uk.ryanwong.catnews.newslist.data.remote.model.UrlDto

internal object NewsItemEntityTestData {

    val mockNewsItemDto by lazy {
        NewsItemDto(
            id = 1,
            creationDate = "2020-11-18T00:00:00Z",
            headline = "some-headline",
            modifiedDate = "2020-11-19T00:00:00Z",
            teaserImage = TeaserImageDto(
                links = LinksDto(
                    url = UrlDto(
                        href = "https://some.links.url/href",
                        templated = true,
                        type = "image/jpeg",
                    ),
                ),
                accessibilityText = "some-accessibility-text",
            ),
            teaserText = "some-teaser-text",
            type = "story",
            advertUrl = "https://some.url/",
            weblinkUrl = "https://some.weblink.url/",
        )
    }

    val mockNewsItemEntity by lazy {
        NewsItemEntity(
            listId = 1,
            newsId = 1,
            type = "story",
            headline = "some-headline",
            creationDate = "2020-11-18T00:00:00Z",
            modifiedDate = "2020-11-19T00:00:00Z",
            advertUrl = "https://some.url/",
            weblinkUrl = "https://some.weblink.url/",
            teaserText = "some-teaser-text",
            teaserImageHref = "https://some.links.url/href",
            teaserImageTemplated = true,
            teaserImageType = "image/jpeg",
            teaserImageAccessibilityText = "some-accessibility-text",
        )
    }

    val mockNewsItemDto2 by lazy {
        NewsItemDto(
            id = 2,
            creationDate = "2020-11-18T00:00:00Z",
            headline = "some-headline",
            modifiedDate = "2020-11-19T00:00:00Z",
            teaserImage = TeaserImageDto(
                links = LinksDto(
                    url = UrlDto(
                        href = "https://some.links.url/href",
                        templated = true,
                        type = "image/jpeg",
                    ),
                ),
                accessibilityText = "some-accessibility-text",
            ),
            teaserText = "some-teaser-text-2",
            type = "story",
            advertUrl = "https://some.url/",
            weblinkUrl = "https://some.weblink.url/",
        )
    }

    val mockNewsItemEntity2 by lazy {
        NewsItemEntity(
            listId = 1,
            newsId = 2,
            type = "story",
            headline = "some-headline",
            creationDate = "2020-11-18T00:00:00Z",
            modifiedDate = "2020-11-19T00:00:00Z",
            advertUrl = "https://some.url/",
            weblinkUrl = "https://some.weblink.url/",
            teaserText = "some-teaser-text-2",
            teaserImageHref = "https://some.links.url/href",
            teaserImageTemplated = true,
            teaserImageType = "image/jpeg",
            teaserImageAccessibilityText = "some-accessibility-text",
        )
    }

    val mockNewsItemDto3 by lazy {
        NewsItemDto(
            id = 3,
            creationDate = "2020-11-18T00:00:00Z",
            headline = "some-headline",
            modifiedDate = "2020-11-19T00:00:00Z",
            teaserImage = TeaserImageDto(
                links = LinksDto(
                    url = UrlDto(
                        href = "https://some.links.url/href",
                        templated = true,
                        type = "image/jpeg",
                    ),
                ),
                accessibilityText = "some-accessibility-text",
            ),
            teaserText = "some-teaser-text-3",
            type = "story",
            advertUrl = "https://some.url/",
            weblinkUrl = "https://some.weblink.url/",
        )
    }

    val mockNewsItemEntity3 by lazy {
        NewsItemEntity(
            listId = 1,
            newsId = 3,
            type = "story",
            headline = "some-headline",
            creationDate = "2020-11-18T00:00:00Z",
            modifiedDate = "2020-11-19T00:00:00Z",
            advertUrl = "https://some.url/",
            weblinkUrl = "https://some.weblink.url/",
            teaserText = "some-teaser-text-3",
            teaserImageHref = "https://some.links.url/href",
            teaserImageTemplated = true,
            teaserImageType = "image/jpeg",
            teaserImageAccessibilityText = "some-accessibility-text",
        )
    }
}
