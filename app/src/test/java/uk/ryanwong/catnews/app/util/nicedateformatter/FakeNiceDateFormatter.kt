/*
 * Copyright (c) 2024. Ryan Wong (hello@ryanwong.co.uk)
 */

package uk.ryanwong.catnews.app.util.nicedateformatter

import uk.ryanwong.catnews.domain.util.nicedateformatter.NiceDateFormatter

class FakeNiceDateFormatter : NiceDateFormatter {
    var getNiceDateResponse: String? = null
    override fun getNiceDate(dateString: String, currentTimeMills: Long): String {
        return getNiceDateResponse ?: throw Exception("fake response not defined")
    }
}
