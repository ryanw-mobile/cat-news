/*
 * Copyright (c) 2024. Ryan Wong (hello@ryanwong.co.uk)
 */

package uk.ryanwong.catnews.domain.model.storydetail

import io.kotest.matchers.shouldBe
import org.junit.Test

internal class StoryContentTypeTest {

    @Test
    fun `Should correctly parse the string image as StoryContentType_IMAGE`() {
        val jsonValue = "image"
        val storyContentType = StoryContentType.parse(storyContentType = jsonValue)
        storyContentType shouldBe StoryContentType.IMAGE
    }

    @Test
    fun `Should correctly parse the string IMAGE as StoryContentType_IMAGE`() {
        val jsonValue = "IMAGE"
        val storyContentType = StoryContentType.parse(storyContentType = jsonValue)
        storyContentType shouldBe StoryContentType.IMAGE
    }

    @Test
    fun `Should correctly parse the string paragraph as StoryContentType_PARAGRAPH`() {
        val jsonValue = "paragraph"
        val storyContentType = StoryContentType.parse(storyContentType = jsonValue)
        storyContentType shouldBe StoryContentType.PARAGRAPH
    }

    @Test
    fun `Should correctly parse the string PARAGRAPH as StoryContentType_PARAGRAPH`() {
        val jsonValue = "PARAGRAPH"
        val storyContentType = StoryContentType.parse(storyContentType = jsonValue)
        storyContentType shouldBe StoryContentType.PARAGRAPH
    }

    @Test
    fun `Should correctly parse unknown strings as StoryContentType_UNKNOWN`() {
        val jsonValue = "some-very-strange-value"
        val storyContentType = StoryContentType.parse(storyContentType = jsonValue)
        storyContentType shouldBe StoryContentType.UNKNOWN
    }

    @Test
    fun `Should correctly parse null value as StoryContentType_UNKNOWN`() {
        val jsonValue = null
        val storyContentType = StoryContentType.parse(storyContentType = jsonValue)
        storyContentType shouldBe StoryContentType.UNKNOWN
    }
}
