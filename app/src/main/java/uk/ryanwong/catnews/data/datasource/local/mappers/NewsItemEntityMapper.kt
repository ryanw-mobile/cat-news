/*
 * Copyright (c) 2024. Ryan Wong (hello@ryanwong.co.uk)
 */

package uk.ryanwong.catnews.data.datasource.local.mappers

import io.ktor.util.date.getTimeMillis
import timber.log.Timber
import uk.ryanwong.catnews.data.datasource.local.entity.NewsItemEntity
import uk.ryanwong.catnews.domain.model.newslist.NewsItem
import uk.ryanwong.catnews.domain.model.newslist.NewsType
import uk.ryanwong.catnews.domain.util.nicedateformatter.NiceDateFormatter

fun List<NewsItemEntity>.toDomainModel(
    niceDateFormatter: NiceDateFormatter,
    currentTimeMills: Long = getTimeMillis(),
): List<NewsItem> {
    return mapNotNull { newsItemEntity ->
        newsItemEntity.toDomainModel(
            niceDateFormatter = niceDateFormatter,
            currentTimeMills = currentTimeMills,
        )
    }
}

fun NewsItemEntity.toDomainModel(
    niceDateFormatter: NiceDateFormatter,
    currentTimeMills: Long = getTimeMillis(),
): NewsItem? {
    val newsItemType = NewsType.parse(type ?: "")

    return when (newsItemType) {
        NewsType.STORY -> {
            NewsItem.Story(
                newsId = newsId,
                headline = headline ?: "",
                teaserText = teaserText ?: "",
                modifiedDate = modifiedDate,
                niceDate = niceDateFormatter.getNiceDate(
                    dateString = modifiedDate,
                    currentTimeMills = currentTimeMills,
                ),
                teaserImageUrl = teaserImageHref ?: "",
                teaserImageAccessibilityText = teaserImageAccessibilityText,
            )
        }

        NewsType.WEBLINK -> {
            // TODO: weblinkUrl should be essential for weblink entries. Change this if not.
            weblinkUrl?.let { weblink ->
                NewsItem.WebLink(
                    newsId = newsId,
                    headline = headline ?: "",
                    modifiedDate = modifiedDate,
                    niceDate = niceDateFormatter.getNiceDate(
                        dateString = modifiedDate,
                        currentTimeMills = currentTimeMills,
                    ),
                    teaserImageUrl = teaserImageHref ?: "",
                    teaserImageAccessibilityText = teaserImageAccessibilityText,
                    url = weblink,
                )
            }
        }
        // For unknown type and ADVERT, we drop them for now as we don't know how to show them.
        else -> {
            Timber.d(
                "NewsItem.fromEntity(): unknown NewsItem with type $newsItemType dropped",
            )
            null
        }
    }
}
