/*
 * Copyright (c) 2022. Ryan Wong (hello@ryanwong.co.uk)
 */

package uk.ryanwong.skycatnews.newslist.domain.model

import io.ktor.util.date.getTimeMillis
import timber.log.Timber
import uk.ryanwong.skycatnews.app.util.nicedateformatter.NiceDateFormatter
import uk.ryanwong.skycatnews.newslist.data.local.entity.NewsItemEntity

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

    companion object {

        fun fromEntity(
            newsItemEntities: List<NewsItemEntity>,
            niceDateFormatter: NiceDateFormatter,
            currentTimeMills: Long = getTimeMillis(),
        ): List<NewsItem> {
            return newsItemEntities.mapNotNull { newsItemEntity ->

                val newsItemType = NewsType.parse(newsItemEntity.type ?: "")
                when (newsItemType) {
                    NewsType.STORY -> {
                        Story(
                            newsId = newsItemEntity.newsId,
                            headline = newsItemEntity.headline ?: "",
                            teaserText = newsItemEntity.teaserText ?: "",
                            modifiedDate = newsItemEntity.modifiedDate,
                            niceDate = niceDateFormatter.getNiceDate(
                                dateString = newsItemEntity.modifiedDate,
                                currentTimeMills = currentTimeMills
                            ),
                            teaserImageUrl = newsItemEntity.teaserImageHref ?: "",
                            teaserImageAccessibilityText = newsItemEntity.teaserImageAccessibilityText,
                        )
                    }

                    NewsType.WEBLINK -> {
                        // TODO: weblinkUrl should be essential for weblink entries. Change this if not.
                        newsItemEntity.weblinkUrl?.let { weblink ->
                            WebLink(
                                newsId = newsItemEntity.newsId,
                                headline = newsItemEntity.headline ?: "",
                                modifiedDate = newsItemEntity.modifiedDate,
                                niceDate = niceDateFormatter.getNiceDate(
                                    dateString = newsItemEntity.modifiedDate,
                                    currentTimeMills = currentTimeMills
                                ),
                                teaserImageUrl = newsItemEntity.teaserImageHref ?: "",
                                teaserImageAccessibilityText = newsItemEntity.teaserImageAccessibilityText,
                                url = weblink
                            )
                        }
                    }
                    // For unknown type and ADVERT, we drop them for now as we don't know how to show them.
                    else -> {
                        Timber.d(
                            "NewsItem.fromEntity(): unknown NewsItem with type $newsItemType dropped"
                        )
                        null
                    }
                }
            }
        }
    }
}
