/*
 * Copyright (c) 2022. Ryan Wong (hello@ryanwong.co.uk)
 */

package uk.ryanwong.skycatnews.uk.ryanwong.skycatnews.newslist.domain.model

import java.util.Date

data class NewsItem(
    val id: Int,
    val headline: String,
    val teaserText: String,
    val modifiedDate: Date,
    val teaserImageUrl: String,
    val type: NewsType,
    val url: String?,
) {
    companion object {

        // TODO: fromDataModel
    }
}