/*
 * Copyright (c) 2022. Ryan Wong (hello@ryanwong.co.uk)
 */

package uk.ryanwong.catnews.app.util.nicedateformatter

import android.text.format.DateUtils
import java.time.Instant
import java.time.format.DateTimeFormatter

class NiceDateFormatterImpl : NiceDateFormatter {
    override fun getNiceDate(dateString: String, currentTimeMills: Long): String {
        val instant = Instant.from(DateTimeFormatter.ISO_INSTANT.parse(dateString))

        // android.text.format.DateUtils.getRelativeTimeSpanString not unit-testable (error: Stub!)
        return DateUtils.getRelativeTimeSpanString(
            instant.toEpochMilli(),
            currentTimeMills,
            DateUtils.SECOND_IN_MILLIS,
        ).toString()
    }
}
