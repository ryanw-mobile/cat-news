/*
 * Copyright (c) 2022. Ryan Wong (hello@ryanwong.co.uk)
 */

package uk.ryanwong.skycatnews.newslist.domain.model

import io.kotest.core.spec.style.FreeSpec
import io.kotest.matchers.shouldBe
import uk.ryanwong.skycatnews.domain.model.newslist.NewsType

internal class NewsTypeTest : FreeSpec() {

    init {
        "Should correctly parse the string story as NewsType.STORY" {
            // Given
            val jsonValue = "story"

            // When
            val newsType = NewsType.parse(newsType = jsonValue)

            // Then
            newsType shouldBe NewsType.STORY
        }

        "Should correctly parse the string STORY as NewsType.STORY" {
            // Given
            val jsonValue = "STORY"

            // When
            val newsType = NewsType.parse(newsType = jsonValue)

            // Then
            newsType shouldBe NewsType.STORY
        }

        "Should correctly parse the string advert as NewsType.ADVERT" {
            // Given
            val jsonValue = "advert"

            // When
            val newsType = NewsType.parse(newsType = jsonValue)

            // Then
            newsType shouldBe NewsType.ADVERT
        }

        "Should correctly parse the string ADVERT as NewsType.ADVERT" {
            // Given
            val jsonValue = "ADVERT"

            // When
            val newsType = NewsType.parse(newsType = jsonValue)

            // Then
            newsType shouldBe NewsType.ADVERT
        }

        "Should correctly parse the string weblink as NewsType.WEBLINK" {
            // Given
            val jsonValue = "weblink"

            // When
            val newsType = NewsType.parse(newsType = jsonValue)

            // Then
            newsType shouldBe NewsType.WEBLINK
        }

        "Should correctly parse the string WEBLINK as NewsType.WEBLINK" {
            // Given
            val jsonValue = "WEBLINK"

            // When
            val newsType = NewsType.parse(newsType = jsonValue)

            // Then
            newsType shouldBe NewsType.WEBLINK
        }

        "Should correctly parse unknown strings as NewsType.UNKNOWN" {
            // Given
            val jsonValue = "some-very-string-value"

            // When
            val newsType = NewsType.parse(newsType = jsonValue)

            // Then
            newsType shouldBe NewsType.UNKNOWN
        }

        "Should correctly parse null value as NewsType.UNKNOWN" {
            // Given
            val jsonValue = null

            // When
            val newsType = NewsType.parse(newsType = jsonValue)

            // Then
            newsType shouldBe NewsType.UNKNOWN
        }
    }
}
