/*
 * Copyright (c) 2023. Ryan Wong (hello@ryanwong.co.uk)
 */

package uk.ryanwong.catnews.domain.model.newslist

import timber.log.Timber

enum class NewsType {
    UNKNOWN,
    STORY,
    ADVERT,
    WEBLINK,
    ;

    companion object {
        fun parse(newsType: String?): NewsType {
            return newsType?.let {
                try {
                    valueOf(it.uppercase())
                } catch (e: IllegalArgumentException) {
                    Timber.d("NewsType.parse(): unknown NewsType $it")
                    UNKNOWN
                }
            } ?: UNKNOWN
        }
    }
}
