/*
 * Copyright (c) 2022. Ryan Wong (hello@ryanwong.co.uk)
 */

package uk.ryanwong.skycatnews.app.util

import androidx.annotation.StringRes

data class ErrorMessage(val id: Long, @StringRes val messageId: Int)
