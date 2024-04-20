/*
 * Copyright (c) 2024. Ryan Wong (hello@ryanwong.co.uk)
 */

package uk.ryanwong.catnews.storydetail.data.local.entity

import io.kotest.matchers.shouldBe
import org.junit.Test
import uk.ryanwong.catnews.data.datasource.local.entity.StoryEntity

internal class StoryEntityTest {

    @Test
    fun `fromDto should return StoryEntity correctly`() {
        val storyDto = StoryEntityTestData.storyDto
        val storyEntity = StoryEntity.fromDto(storyDto = storyDto)
        storyEntity shouldBe StoryEntityTestData.storyEntity
    }
}
