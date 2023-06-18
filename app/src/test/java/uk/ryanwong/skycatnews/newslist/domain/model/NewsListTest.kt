/*
 * Copyright (c) 2022. Ryan Wong (hello@ryanwong.co.uk)
 */

package uk.ryanwong.skycatnews.newslist.domain.model

import io.kotest.core.spec.style.FreeSpec
import io.kotest.matchers.shouldBe
import uk.ryanwong.skycatnews.newslist.data.local.entity.NewsListEntityMapperTestData
import uk.ryanwong.skycatnews.uk.ryanwong.skycatnews.domain.model.newslist.NewsList

internal class NewsListTest : FreeSpec() {
    init {
        "isEmpty" - {
            "Should return true if it contains title but no newsItems" {
                // Given
                val newsList = NewsList(title = "some-title", newsItems = emptyList())

                // When
                val isEmpty = newsList.isEmpty()

                // Then
                isEmpty shouldBe true
            }

            "Should return false if newsItems is not empty" {
                // Given
                val newsList = NewsList(
                    title = "some-title",
                    newsItems = listOf(NewsListEntityMapperTestData.mockNewsItemStory),
                )

                // When
                val isEmpty = newsList.isEmpty()

                // Then
                isEmpty shouldBe false
            }
        }
    }
}
