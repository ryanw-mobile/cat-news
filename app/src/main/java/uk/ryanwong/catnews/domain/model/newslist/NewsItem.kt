/*
 * Copyright (c) 2024. Ryan Wong (hello@ryanwong.co.uk)
 */

package uk.ryanwong.catnews.domain.model.newslist

sealed class NewsItem {
    data class Story(
        val newsId: Int,
        val headline: String,
        val teaserText: String,
        val modifiedDate: String,
        val niceDate: String,
        val teaserImageUrl: String,
        val teaserImageAccessibilityText: String?,
    ) : NewsItem()

    data class WebLink(
        val newsId: Int,
        val headline: String,
        // TODO: Wireframe suggests it should have teaserText but missing from json
        // val teaserText: String,
        val url: String,
        val modifiedDate: String,
        val niceDate: String,
        val teaserImageUrl: String,
        val teaserImageAccessibilityText: String?,
    ) : NewsItem()

    // TODO: Advert is not implemented as we do not need it in this prototype.
}
