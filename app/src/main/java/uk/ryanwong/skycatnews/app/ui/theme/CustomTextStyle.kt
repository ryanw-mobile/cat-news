/*
 * Copyright (c) 2022. Ryan Wong (hello@ryanwong.co.uk)
 */

package uk.ryanwong.skycatnews.app.ui.theme

import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign

object CustomTextStyle {

    val regularHeadline
        @Composable get() = MaterialTheme.typography.subtitle1.copy(
            fontWeight = FontWeight.Bold,
        )

    val regularHeadlineTeaserText
        @Composable get() = MaterialTheme.typography.body2

    val regularHeadlineDate
        @Composable get() = MaterialTheme.typography.caption.copy(
            fontStyle = FontStyle.Italic,
            textAlign = TextAlign.Right,
        )

    val largeHeadline
        @Composable get() = MaterialTheme.typography.h6.copy(
            fontWeight = FontWeight.Bold,
        )

    val largeHeadlineTeaserText
        @Composable get() = MaterialTheme.typography.body1

    val largeHeadlineDate
        @Composable get() = MaterialTheme.typography.caption.copy(
            fontStyle = FontStyle.Italic,
            textAlign = TextAlign.Right,
        )

    val noDataErrorText
        @Composable get() = MaterialTheme.typography.body1
}
