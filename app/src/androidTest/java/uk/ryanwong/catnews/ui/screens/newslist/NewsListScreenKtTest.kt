/*
 * Copyright (c) 2024. Ryan Wong (hello@ryanwong.co.uk)
 */

package uk.ryanwong.catnews.ui.screens.newslist

import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import uk.ryanwong.catnews.MainActivity

@kotlinx.coroutines.ExperimentalCoroutinesApi
@HiltAndroidTest
@RunWith(AndroidJUnit4::class)
internal class NewsListScreenKtTest {

    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    private lateinit var newsListScreenRobot: NewsListScreenRobot

    @Before
    fun setUp() {
        runTest {
            hiltRule.inject()
            newsListScreenRobot = NewsListScreenRobot(composeTestRule)
        }
    }

    @Test
    fun news_list_screen_general_layout_is_correct() {
        with(newsListScreenRobot) {
            waitUntilNewsListIsVisible()
            storyHeadline1ShowsCorrectLayout()
            storyHeadline2ShowsCorrectLayout()
            weblinkHeadline3ShowsCorrectLayout()
            storyHeadline4ShowsCorrectLayout()
            weblinkHeadline5ShowsCorrectLayout()
            storyHeadline6ShowsCorrectLayout()
        }
    }

    @Test
    fun story_screen_general_layout_is_correct() {
        with(newsListScreenRobot) {
            waitUntilNewsListIsVisible()
            userClicksOnStoryHeadline1()
            storyScreenShowsCorrectLayout()
        }
    }

    @Test
    fun weblink_screen_general_layout_is_correct() {
        with(newsListScreenRobot) {
            waitUntilNewsListIsVisible()
            userClicksOnWeblinkHeadline3()
            weblinkScreenShowsCorrectLayout()
        }
    }
}
