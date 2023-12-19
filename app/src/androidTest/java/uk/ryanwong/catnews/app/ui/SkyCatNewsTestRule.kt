/*
 * Copyright (c) 2022. Ryan Wong (hello@ryanwong.co.uk)
 */

package uk.ryanwong.catnews.app.ui

import androidx.compose.ui.test.junit4.AndroidComposeTestRule
import androidx.test.ext.junit.rules.ActivityScenarioRule
import uk.ryanwong.catnews.app.MainActivity
import uk.ryanwong.catnews.app.ui.theme.CatNewsTheme

typealias catnewsTestRule = AndroidComposeTestRule<ActivityScenarioRule<MainActivity>, MainActivity>

fun catnewsTestRule.loadMainScreen(): catnewsTestRule {
    with(this) {
        setContent {
            CatNewsTheme {
                CatNewsApp()
            }
        }
        waitForIdle()
    }
    return this
}
