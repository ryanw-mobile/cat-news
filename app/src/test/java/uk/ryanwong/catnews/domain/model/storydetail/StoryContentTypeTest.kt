/*
 * Copyright (c) 2023. Ryan Wong (hello@ryanwong.co.uk)
 */

package uk.ryanwong.catnews.domain.model.storydetail

import io.kotest.core.spec.style.FreeSpec
import io.kotest.matchers.shouldBe

internal class StoryContentTypeTest : FreeSpec() {

    init {
        "Should correctly parse the string image as StoryContentType.IMAGE" {
            // Given
            val jsonValue = "image"

            // When
            val storyContentType = StoryContentType.parse(storyContentType = jsonValue)

            // Then
            storyContentType shouldBe StoryContentType.IMAGE
        }

        "Should correctly parse the string IMAGE as StoryContentType.IMAGE" {
            // Given
            val jsonValue = "IMAGE"

            // When
            val storyContentType = StoryContentType.parse(storyContentType = jsonValue)

            // Then
            storyContentType shouldBe StoryContentType.IMAGE
        }

        "Should correctly parse the string paragraph as StoryContentType.PARAGRAPH" {
            // Given
            val jsonValue = "paragraph"

            // When
            val storyContentType = StoryContentType.parse(storyContentType = jsonValue)

            // Then
            storyContentType shouldBe StoryContentType.PARAGRAPH
        }

        "Should correctly parse the string PARAGRAPH as StoryContentType.PARAGRAPH" {
            // Given
            val jsonValue = "PARAGRAPH"

            // When
            val storyContentType = StoryContentType.parse(storyContentType = jsonValue)

            // Then
            storyContentType shouldBe StoryContentType.PARAGRAPH
        }

        "Should correctly parse unknown strings as StoryContentType.UNKNOWN" {
            // Given
            val jsonValue = "some-very-string-value"

            // When
            val storyContentType = StoryContentType.parse(storyContentType = jsonValue)

            // Then
            storyContentType shouldBe StoryContentType.UNKNOWN
        }

        "Should correctly parse null value as StoryContentType.UNKNOWN" {
            // Given
            val jsonValue = null

            // When
            val storyContentType = StoryContentType.parse(storyContentType = jsonValue)

            // Then
            storyContentType shouldBe StoryContentType.UNKNOWN
        }
    }
}
