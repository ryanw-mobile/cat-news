/*
 * Copyright (c) 2024. Ryan Wong (hello@ryanwong.co.uk)
 */

package uk.ryanwong.catnews.domain.util.nicedateformatter

interface NiceDateFormatter {
    fun getNiceDate(
        dateString: String,
        currentTimeMills: Long,
    ): String
}
