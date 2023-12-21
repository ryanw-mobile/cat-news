/*
 * Copyright (c) 2022. Ryan Wong (hello@ryanwong.co.uk)
 */

package uk.ryanwong.catnews.app.ui.component

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.navigation.compose.rememberNavController
import org.junit.Rule
import org.junit.Test
import uk.ryanwong.catnews.app.ui.theme.CatNewsTheme

internal class CatNewsAppBarKtTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun should_show_image_title_if_customTitle_is_null() {
        composeTestRule.setContent {
            CatNewsTheme {
                CatNewsAppBar(
                    navController = rememberNavController(),
                    customTitle = null,
                )
            }
        }
        composeTestRule.onNodeWithContentDescription(label = "Cat News").assertIsDisplayed()
    }

    @Test
    fun should_show_text_title_if_customTitle_is_not_null() {
        composeTestRule.setContent {
            CatNewsTheme {
                CatNewsAppBar(
                    navController = rememberNavController(),
                    customTitle = "some-custom-text-title",
                )
            }
        }
        composeTestRule.onNodeWithContentDescription(label = "Cat News").assertDoesNotExist()
        composeTestRule.onNodeWithText("some-custom-text-title").assertIsDisplayed()
    }
}
