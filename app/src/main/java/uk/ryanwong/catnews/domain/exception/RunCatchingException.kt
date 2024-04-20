/*
 * Copyright (c) 2022. Ryan Wong (hello@ryanwong.co.uk)
 */

package uk.ryanwong.catnews.domain.exception

/***
 * Workaround to rethrow CancellationException
 * Also only wraps Exception but not Throwable
 * Reference: https://github.com/Kotlin/kotlinx.coroutines/issues/1814
 */

inline fun <reified T : Throwable, R> Result<R>.except(): Result<R> =
    onFailure { if (it is T) throw it }
