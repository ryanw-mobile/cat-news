/*
 * Copyright (c) 2024. Ryan Wong (hello@ryanwong.co.uk)
 */

package uk.ryanwong.catnews.newslist.data.repository

import uk.ryanwong.catnews.data.datasource.local.entity.NewsItemEntity
import uk.ryanwong.catnews.data.dto.LinksDto
import uk.ryanwong.catnews.data.dto.NewsItemDto
import uk.ryanwong.catnews.data.dto.NewsListDto
import uk.ryanwong.catnews.data.dto.TeaserImageDto
import uk.ryanwong.catnews.data.dto.UrlDto
import uk.ryanwong.catnews.domain.model.newslist.NewsItem
import uk.ryanwong.catnews.domain.model.newslist.NewsList

internal object NewsListRepositoryImplTestData {
    private val newsItemDto = NewsItemDto(
        creationDate = "2020-11-18T00:00:00Z",
        headline = "some-headline",
        id = 1,
        modifiedDate = "2020-11-19T00:00:00Z",
        teaserImage = TeaserImageDto(
            links = LinksDto(
                url = UrlDto(
                    href = "https://some.url/href",
                    templated = true,
                    type = "image/jpeg",
                ),
            ),
            accessibilityText = "some-accessibility-text",
        ),
        teaserText = "some-teaser-text",
        type = "story",
        advertUrl = null,
        weblinkUrl = null,
    )

    val newsListDto = NewsListDto(
        title = "some-title",
        news = listOf(newsItemDto),
    )

    fun generateNewsItemEntity(listId: Int) = NewsItemEntity(
        listId = listId,
        newsId = 1,
        type = "story",
        headline = "some-headline",
        creationDate = "2020-11-18T00:00:00Z",
        modifiedDate = "2020-11-19T00:00:00Z",
        advertUrl = null,
        weblinkUrl = null,
        teaserText = "some-teaser-text",
        teaserImageHref = "https://some.url/href",
        teaserImageTemplated = true,
        teaserImageType = "image/jpeg",
        teaserImageAccessibilityText = "some-accessibility-text",
    )

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
}
