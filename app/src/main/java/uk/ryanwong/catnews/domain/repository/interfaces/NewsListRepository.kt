/*
 * Copyright (c) 2022. Ryan Wong (hello@ryanwong.co.uk)
 */

package uk.ryanwong.catnews.domain.repository.interfaces

import uk.ryanwong.catnews.domain.model.newslist.NewsItem
import uk.ryanwong.catnews.domain.model.newslist.NewsList

interface NewsListRepository {
    suspend fun getNewsList(): Result<NewsList>
    suspend fun getNewsItem(newsId: Int): Result<NewsItem?>
}
