/*
 * Copyright (c) 2024. Ryan Wong (hello@ryanwong.co.uk)
 */

package uk.ryanwong.catnews.domain.model.storydetail

import timber.log.Timber

enum class StoryContentType {
    UNKNOWN,
    IMAGE,
    PARAGRAPH,
    ;

    companion object {
        fun parse(storyContentType: String?): StoryContentType {
            return storyContentType?.let {
                try {
                    valueOf(it.uppercase())
                } catch (e: IllegalArgumentException) {
                    Timber.d("StoryContentType.parse(): unknown type: $it")
                    UNKNOWN
                }
            } ?: UNKNOWN
        }
    }
}
