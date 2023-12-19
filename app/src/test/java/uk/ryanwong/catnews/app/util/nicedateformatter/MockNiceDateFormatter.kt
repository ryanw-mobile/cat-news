/*
 * Copyright (c) 2022. Ryan Wong (hello@ryanwong.co.uk)
 */

package uk.ryanwong.catnews.app.util.nicedateformatter

class MockNiceDateFormatter : NiceDateFormatter {
    var mockGetNiceDateResponse: String? = null
    override fun getNiceDate(dateString: String, currentTimeMills: Long): String {
        return mockGetNiceDateResponse ?: throw Exception("mock response not defined")
    }
}
