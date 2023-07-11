/*
 * Copyright (c) 2022. Ryan Wong (hello@ryanwong.co.uk)
 */

package uk.ryanwong.skycatnews.app.ui

import androidx.compose.ui.test.junit4.AndroidComposeTestRule
import androidx.test.ext.junit.rules.ActivityScenarioRule
import uk.ryanwong.skycatnews.SkyCatNewsApp
import uk.ryanwong.skycatnews.app.MainActivity
import uk.ryanwong.skycatnews.app.ui.theme.SkyCatNewsTheme

typealias SkyCatNewsTestRule = AndroidComposeTestRule<ActivityScenarioRule<MainActivity>, MainActivity>

fun SkyCatNewsTestRule.loadMainScreen(): SkyCatNewsTestRule {
    with(this) {
        setContent {
            SkyCatNewsTheme {
                SkyCatNewsApp()
            }
        }
        waitForIdle()
    }
    return this
}
