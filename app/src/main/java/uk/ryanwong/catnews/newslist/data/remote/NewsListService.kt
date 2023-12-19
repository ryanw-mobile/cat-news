/*
 * Copyright (c) 2022. Ryan Wong (hello@ryanwong.co.uk)
 */

package uk.ryanwong.catnews.newslist.data.remote

import uk.ryanwong.catnews.BuildConfig
import uk.ryanwong.catnews.newslist.data.remote.model.NewsListDto

interface NewsListService {

    suspend fun getAllItems(): Result<NewsListDto?>

    companion object {
        const val BASE_URL = BuildConfig.DEFAULT_BASE_URL
    }

    sealed class Endpoints(val url: String) {
        object GetAllItems : Endpoints("$BASE_URL/news-list")
    }
}
