/*
 * Copyright (c) 2023. Ryan Wong (hello@ryanwong.co.uk)
 */

package uk.ryanwong.catnews.domain.model.newslist

import io.kotest.matchers.shouldBe
import org.junit.Test

internal class NewsTypeTest {
    @Test
    fun `Should correctly parse the string story as NewsType_STORY`() {
        val jsonValue = "story"
        val newsType = NewsType.parse(newsType = jsonValue)
        newsType shouldBe NewsType.STORY
    }

    @Test
    fun `Should correctly parse the string STORY as NewsType_STORY`() {
        val jsonValue = "STORY"
        val newsType = NewsType.parse(newsType = jsonValue)
        newsType shouldBe NewsType.STORY
    }

    @Test
    fun `Should correctly parse the string advert as NewsType_ADVERT`() {
        val jsonValue = "advert"
        val newsType = NewsType.parse(newsType = jsonValue)
        newsType shouldBe NewsType.ADVERT
    }

    @Test
    fun `Should correctly parse the string ADVERT as NewsType_ADVERT`() {
        val jsonValue = "ADVERT"
        val newsType = NewsType.parse(newsType = jsonValue)
        newsType shouldBe NewsType.ADVERT
    }

    @Test
    fun `Should correctly parse the string weblink as NewsType_WEBLINK`() {
        val jsonValue = "weblink"
        val newsType = NewsType.parse(newsType = jsonValue)
        newsType shouldBe NewsType.WEBLINK
    }

    @Test
    fun `Should correctly parse the string WEBLINK as NewsType_WEBLINK`() {
        val jsonValue = "WEBLINK"
        val newsType = NewsType.parse(newsType = jsonValue)
        newsType shouldBe NewsType.WEBLINK
    }

    @Test
    fun `Should correctly parse unknown strings as NewsType_UNKNOWN`() {
        val jsonValue = "some-very-strange-value"
        val newsType = NewsType.parse(newsType = jsonValue)
        newsType shouldBe NewsType.UNKNOWN
    }

    @Test
    fun `Should correctly parse null value as NewsType_UNKNOWN`() {
        val jsonValue = null
        val newsType = NewsType.parse(newsType = jsonValue)
        newsType shouldBe NewsType.UNKNOWN
    }
}
