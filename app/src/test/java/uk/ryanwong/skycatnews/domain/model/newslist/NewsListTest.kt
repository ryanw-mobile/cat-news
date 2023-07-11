/*
 * Copyright (c) 2023. Ryan Wong (hello@ryanwong.co.uk)
 */

package uk.ryanwong.skycatnews.domain.model.newslist

import io.kotest.core.spec.style.FreeSpec
import io.kotest.matchers.shouldBe
import uk.ryanwong.skycatnews.newslist.data.local.entity.NewsListEntityMapperTestData

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
