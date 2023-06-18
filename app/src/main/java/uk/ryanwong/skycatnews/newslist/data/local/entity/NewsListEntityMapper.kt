/*
 * Copyright (c) 2023. Ryan Wong (hello@ryanwong.co.uk)
 */

package uk.ryanwong.skycatnews.uk.ryanwong.skycatnews.newslist.data.local.entity

import uk.ryanwong.skycatnews.app.util.nicedateformatter.NiceDateFormatter
import uk.ryanwong.skycatnews.newslist.data.local.entity.NewsItemEntity
import uk.ryanwong.skycatnews.newslist.data.local.entity.NewsListEntity
import uk.ryanwong.skycatnews.uk.ryanwong.skycatnews.domain.model.newslist.NewsList

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
