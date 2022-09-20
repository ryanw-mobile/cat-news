/*
 * Copyright (c) 2022. Ryan Wong (hello@ryanwong.co.uk)
 */

package uk.ryanwong.skycatnews.app.util.nicedateformatter

interface NiceDateFormatter {
    fun getNiceDate(
        dateString: String,
        currentTimeMills: Long,
    ): String
}
