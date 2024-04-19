/*
 * Copyright (c) 2022. Ryan Wong (hello@ryanwong.co.uk)
 */

package uk.ryanwong.catnews.app.util.nicedateformatter

class FakeNiceDateFormatter : NiceDateFormatter {
    var getNiceDateResponse: String? = null
    override fun getNiceDate(dateString: String, currentTimeMills: Long): String {
        return getNiceDateResponse ?: throw Exception("fake response not defined")
    }
}
