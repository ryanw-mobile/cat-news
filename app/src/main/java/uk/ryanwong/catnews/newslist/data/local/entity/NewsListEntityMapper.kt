/*
 * Copyright (c) 2023. Ryan Wong (hello@ryanwong.co.uk)
 */

package uk.ryanwong.catnews.newslist.data.local.entity

import uk.ryanwong.catnews.app.util.nicedateformatter.NiceDateFormatter
import uk.ryanwong.catnews.domain.model.newslist.NewsList

fun NewsListEntity.toDomainModel(
    newsItemEntities: List<NewsItemEntity>,
    niceDateFormatter: NiceDateFormatter,
): NewsList {
    return NewsList(
        title = title,
        newsItems = newsItemEntities.toDomainModel(
            niceDateFormatter = niceDateFormatter,
        ),
    )
}
