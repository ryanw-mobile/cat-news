/*
 * Copyright (c) 2024. Ryan Wong (hello@ryanwong.co.uk)
 */

package uk.ryanwong.catnews.data.datasource.remote.interfaces

import uk.ryanwong.catnews.BuildConfig
import uk.ryanwong.catnews.data.dto.NewsListDto

interface NewsListService {

    suspend fun getAllItems(): Result<NewsListDto?>

    companion object {
        const val BASE_URL = BuildConfig.DEFAULT_BASE_URL
    }

    sealed class Endpoints(val url: String) {
        object GetAllItems : Endpoints("$BASE_URL/news-list")
    }
}
