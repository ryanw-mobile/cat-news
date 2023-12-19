/*
 * Copyright (c) 2022. Ryan Wong (hello@ryanwong.co.uk)
 */

package uk.ryanwong.catnews.storydetail.data.local.entity

import io.kotest.core.spec.style.FreeSpec
import io.kotest.matchers.shouldBe

internal class StoryEntityTest : FreeSpec() {

    init {
        "fromDto" - {
            "Should return StoryEntity correctly" {
                // Given
                val storyDto = StoryEntityTestData.mockStoryDto

                // When
                val storyEntity = StoryEntity.fromDto(storyDto = storyDto)

                // Then
                storyEntity shouldBe StoryEntityTestData.mockStoryEntity
            }
        }
    }
}
