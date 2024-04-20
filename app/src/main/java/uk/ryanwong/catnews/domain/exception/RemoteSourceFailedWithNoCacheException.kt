/*
 * Copyright (c) 2022. Ryan Wong (hello@ryanwong.co.uk)
 */

package uk.ryanwong.catnews.domain.exception

class RemoteSourceFailedWithNoCacheException : Exception() {
    override val message: String = "Error receiving data from server. No cached content available."
}
