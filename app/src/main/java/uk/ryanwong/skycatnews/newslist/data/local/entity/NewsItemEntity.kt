/*
 * Copyright (c) 2022. Ryan Wong (hello@ryanwong.co.uk)
 */

package uk.ryanwong.skycatnews.newslist.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import timber.log.Timber
import uk.ryanwong.skycatnews.newslist.data.remote.model.NewsItemDto

@Entity(
    tableName = "news_items",
    primaryKeys = ["list_id", "news_id"],
)
data class NewsItemEntity(
    @ColumnInfo(name = "list_id")
    val listId: Int,
    @ColumnInfo(name = "news_id")
    val newsId: Int,
    val type: String?,
    val headline: String?,
    @ColumnInfo(name = "creation_date")
    val creationDate: String,
    @ColumnInfo(name = "modified_date")
    val modifiedDate: String,
    @ColumnInfo(name = "advert_url")
    val advertUrl: String?,
    @ColumnInfo(name = "weblink_url")
    val weblinkUrl: String?,

    @ColumnInfo(name = "teaser_text")
    val teaserText: String?,
    @ColumnInfo(name = "teaser_image_href")
    val teaserImageHref: String?,
    @ColumnInfo(name = "teaser_image_templated")
    val teaserImageTemplated: Boolean?,
    @ColumnInfo(name = "teaser_image_type")
    val teaserImageType: String?,
    @ColumnInfo(name = "teaser_image_accessibility_text")
    val teaserImageAccessibilityText: String?,
) {
    companion object {
        fun fromDto(listId: Int, newsItemDtoList: List<NewsItemDto>?): List<NewsItemEntity> {
            return newsItemDtoList?.mapNotNull { newsItem ->
                with(newsItem) {
                    /***
                     * The type "advert" is not in the current scope, and it does not follow
                     * the format of story and weblink, we drop them for now.
                     */
                    if (id == null || creationDate == null || modifiedDate == null) {
                        Timber.d("NewsItemEntity.fromDto(): invalid NewsItem dropped")
                        return@with null
                    }

                    NewsItemEntity(
                        listId = listId,
                        newsId = id,
                        type = type,
                        headline = headline,
                        creationDate = creationDate,
                        modifiedDate = modifiedDate,
                        advertUrl = advertUrl,
                        weblinkUrl = weblinkUrl,
                        teaserText = teaserText,
                        teaserImageHref = teaserImage?.links?.url?.href,
                        teaserImageTemplated = teaserImage?.links?.url?.templated,
                        teaserImageType = teaserImage?.links?.url?.type,
                        teaserImageAccessibilityText = teaserImage?.accessibilityText,
                    )
                }
            } ?: emptyList()
        }
    }
}
