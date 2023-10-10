/*
 * Copyright (c) 2022. Ryan Wong (hello@ryanwong.co.uk)
 */

package uk.ryanwong.skycatnews.app.ui.component

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.navigation.compose.rememberNavController
import org.junit.Rule
import org.junit.Test
import uk.ryanwong.skycatnews.app.ui.theme.SkyCatNewsTheme

internal class SkyCatNewsAppBarKtTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun should_show_image_title_if_customTitle_is_null() {
        composeTestRule.setContent {
            SkyCatNewsTheme {
                SkyCatNewsAppBar(
                    navController = rememberNavController(),
                    customTitle = null,
                )
            }
        }
        composeTestRule.onNodeWithContentDescription(label = "Sky Cat News").assertIsDisplayed()
    }

    @Test
    fun should_show_text_title_if_customTitle_is_not_null() {
        composeTestRule.setContent {
            SkyCatNewsTheme {
                SkyCatNewsAppBar(
                    navController = rememberNavController(),
                    customTitle = "some-custom-text-title",
                )
            }
        }
        composeTestRule.onNodeWithContentDescription(label = "Sky Cat News").assertDoesNotExist()
        composeTestRule.onNodeWithText("some-custom-text-title").assertIsDisplayed()
    }
}
