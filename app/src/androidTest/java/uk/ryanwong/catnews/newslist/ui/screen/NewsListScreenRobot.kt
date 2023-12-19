/*
 * Copyright (c) 2022. Ryan Wong (hello@ryanwong.co.uk)
 */

package uk.ryanwong.catnews.newslist.ui.screen

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.onAllNodesWithContentDescription
import androidx.compose.ui.test.onChildren
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performScrollToIndex
import uk.ryanwong.catnews.app.ui.catnewsTestRule

internal class NewsListScreenRobot(
    private val composeTestRule: catnewsTestRule,
) {

    fun waitUntilNewsListIsVisible() {
        with(composeTestRule) {
            waitUntil(timeoutMillis = 3000) {
                onNodeWithContentDescription(label = "News list").onChildren()
                    .fetchSemanticsNodes().size > 1
            }
        }
    }

    fun storyHeadline1ShowsCorrectLayout() {
        with(composeTestRule) {
            onNodeWithContentDescription(label = "News list").performScrollToIndex(0)
            waitForIdle()

            onNodeWithText(text = "some-story-headline-1").assertIsDisplayed()
            onNodeWithText(text = "some-teaser-text-1").assertIsDisplayed()
            onNodeWithText(text = "Yesterday").assertIsDisplayed()
            onNodeWithContentDescription(label = "some-teaser-image-accessibility-text-1").assertIsDisplayed()
        }
    }

    fun storyHeadline2ShowsCorrectLayout() {
        with(composeTestRule) {
            onNodeWithContentDescription(label = "News list").performScrollToIndex(1)
            waitForIdle()

            onNodeWithText(text = "some-story-headline-2").assertIsDisplayed()
            onNodeWithText(text = "some-teaser-text-2").assertIsDisplayed()
            onNodeWithText(text = "2 days ago").assertIsDisplayed()
            onNodeWithContentDescription(label = "some-teaser-image-accessibility-text-2").assertIsDisplayed()
        }
    }

    fun weblinkHeadline3ShowsCorrectLayout() {
        with(composeTestRule) {
            onNodeWithContentDescription(label = "News list").performScrollToIndex(2)
            waitForIdle()

            onNodeWithText(text = "some-weblink-headline-3").assertIsDisplayed()
            onNodeWithText(text = "3 days ago").assertIsDisplayed()
            onNodeWithContentDescription(label = "some-teaser-image-accessibility-text-3").assertIsDisplayed()
        }
    }

    fun storyHeadline4ShowsCorrectLayout() {
        with(composeTestRule) {
            onNodeWithContentDescription(label = "News list").performScrollToIndex(3)
            waitForIdle()

            onNodeWithText(text = "some-story-headline-4").assertIsDisplayed()
            onNodeWithText(text = "some-teaser-text-4").assertIsDisplayed()
            onNodeWithText(text = "4 days ago").assertIsDisplayed()
            onNodeWithContentDescription(label = "some-teaser-image-accessibility-text-4").assertIsDisplayed()
        }
    }

    fun weblinkHeadline5ShowsCorrectLayout() {
        with(composeTestRule) {
            onNodeWithContentDescription(label = "News list").performScrollToIndex(4)
            waitForIdle()

            onNodeWithText(text = "some-weblink-headline-5").assertIsDisplayed()
            onNodeWithText(text = "5 days ago").assertIsDisplayed()
            onNodeWithContentDescription(label = "some-teaser-image-accessibility-text-5").assertIsDisplayed()
        }
    }

    fun storyHeadline6ShowsCorrectLayout() {
        with(composeTestRule) {
            onNodeWithContentDescription(label = "News list").performScrollToIndex(5)
            waitForIdle()

            onNodeWithText(text = "some-story-headline-6").assertIsDisplayed()
            onNodeWithText(text = "some-teaser-text-6").assertIsDisplayed()
            onNodeWithText(text = "6 days ago").assertIsDisplayed()
            onNodeWithContentDescription(label = "some-teaser-image-accessibility-text-6").assertIsDisplayed()
        }
    }

    fun userClicksOnStoryHeadline1() {
        with(composeTestRule) {
            onNodeWithContentDescription(label = "News list").performScrollToIndex(0)
            waitForIdle()
            onNodeWithText(text = "some-story-headline-1").performClick()
            waitForIdle()
        }
    }

    fun storyScreenShowsCorrectLayout() {
        with(composeTestRule) {
            waitUntil(timeoutMillis = 3000) {
                onNodeWithContentDescription(label = "Story detail").onChildren()
                    .fetchSemanticsNodes().size > 1
            }
            onNodeWithText(text = "some-headline").assertIsDisplayed()
            onNodeWithContentDescription(label = "some-hero-image-accessibility-text").assertIsDisplayed()

            onNodeWithContentDescription(label = "Story detail").performScrollToIndex(1)
            waitForIdle()
            onNodeWithText(text = "some-paragraph-1").assertIsDisplayed()

            onNodeWithContentDescription(label = "Story detail").performScrollToIndex(2)
            waitForIdle()
            onNodeWithContentDescription(label = "some-accessibility-text-2").assertIsDisplayed()

            onNodeWithContentDescription(label = "Story detail").performScrollToIndex(3)
            waitForIdle()
            onNodeWithText(text = "some-paragraph-3").assertIsDisplayed()
        }
    }

    fun userClicksOnWeblinkHeadline3() {
        with(composeTestRule) {
            onNodeWithContentDescription(label = "News list").performScrollToIndex(2)
            waitForIdle()
            onNodeWithText(text = "some-weblink-headline-3").performClick()
            waitForIdle()
        }
    }

    fun weblinkScreenShowsCorrectLayout() {
        with(composeTestRule) {
            waitUntil(timeoutMillis = 3000) {
                onAllNodesWithContentDescription(label = "Web link web view")
                    .fetchSemanticsNodes().isNotEmpty()
            }

            onNodeWithContentDescription(label = "Web link web view").assertIsDisplayed() // currently redundant due to waitUntil check
        }
    }
}
