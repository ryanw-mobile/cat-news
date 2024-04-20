/*
 * Copyright (c) 2024. Ryan Wong (hello@ryanwong.co.uk)
 */

package uk.ryanwong.catnews.data.datasource.local.mappers

import uk.ryanwong.catnews.data.datasource.local.entity.NewsItemEntity
import uk.ryanwong.catnews.data.datasource.local.entity.NewsListEntity
import uk.ryanwong.catnews.domain.model.newslist.NewsList
import uk.ryanwong.catnews.domain.util.nicedateformatter.NiceDateFormatter

fun NewsListEntity.asDomainModel(
    newsItemEntities: List<NewsItemEntity>,
    niceDateFormatter: NiceDateFormatter,
): NewsList {
    return NewsList(
        title = title,
        newsItems = newsItemEntities.asDomainModel(
            niceDateFormatter = niceDateFormatter,
        ),
    )
}
