/*
 * Copyright (c) 2022. Ryan Wong (hello@ryanwong.co.uk)
 */

package uk.ryanwong.catnews.newslist.data.remote

import uk.ryanwong.catnews.newslist.data.remote.model.LinksDto
import uk.ryanwong.catnews.newslist.data.remote.model.NewsItemDto
import uk.ryanwong.catnews.newslist.data.remote.model.NewsListDto
import uk.ryanwong.catnews.newslist.data.remote.model.TeaserImageDto
import uk.ryanwong.catnews.newslist.data.remote.model.UrlDto
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

class FakeNewsListService : NewsListService {
    override suspend fun getAllItems(): Result<NewsListDto> {
        val randomTimestamp1 = generateRandomPastDate(1)
        val randomTimestamp2 = generateRandomPastDate(2)
        val randomTimestamp3 = generateRandomPastDate(3)
        val randomTimestamp4 = generateRandomPastDate(4)
        val randomTimestamp5 = generateRandomPastDate(5)
        val randomTimestamp6 = generateRandomPastDate(6)

        return Result.success(
            NewsListDto(
                news = listOf(
                    NewsItemDto(
                        creationDate = randomTimestamp1,
                        headline = "some-story-headline-1",
                        id = 1,
                        modifiedDate = randomTimestamp1,
                        teaserImage = TeaserImageDto(
                            links = LinksDto(
                                url = UrlDto(
                                    href = "https://ryanwong.co.uk/sample-resources/catnews/cat1_hero.jpg",
                                    templated = true,
                                    type = "image/jpeg",
                                ),
                            ),
                            accessibilityText = "some-teaser-image-accessibility-text-1",
                        ),
                        teaserText = "some-teaser-text-1",
                        type = "story",
                        advertUrl = null,
                        weblinkUrl = null,
                    ),
                    NewsItemDto(
                        creationDate = randomTimestamp2,
                        headline = "some-story-headline-2",
                        id = 2,
                        modifiedDate = randomTimestamp2,
                        teaserImage = TeaserImageDto(
                            links = LinksDto(
                                url = UrlDto(
                                    href = "https://ryanwong.co.uk/sample-resources/catnews/cat2_hero.jpg",
                                    templated = true,
                                    type = "image/jpeg",
                                ),
                            ),
                            accessibilityText = "some-teaser-image-accessibility-text-2",
                        ),
                        teaserText = "some-teaser-text-2",
                        type = "story",
                        advertUrl = null,
                        weblinkUrl = null,
                    ),
                    NewsItemDto(
                        creationDate = randomTimestamp3,
                        headline = "some-weblink-headline-3",
                        id = 3,
                        modifiedDate = randomTimestamp3,
                        teaserImage = TeaserImageDto(
                            links = LinksDto(
                                url = UrlDto(
                                    href = "https://ryanwong.co.uk/sample-resources/catnews/cat3_hero.jpg",
                                    templated = true,
                                    type = "image/jpeg",
                                ),
                            ),
                            accessibilityText = "some-teaser-image-accessibility-text-3",
                        ),
                        teaserText = null,
                        type = "weblink",
                        advertUrl = null,
                        weblinkUrl = "https://news.sky.com/weblinkUrl3",
                    ),
                    NewsItemDto(
                        creationDate = randomTimestamp4,
                        headline = "some-story-headline-4",
                        id = 4,
                        modifiedDate = randomTimestamp4,
                        teaserImage = TeaserImageDto(
                            links = LinksDto(
                                url = UrlDto(
                                    href = "https://ryanwong.co.uk/sample-resources/catnews/cat4_hero.jpg",
                                    templated = true,
                                    type = "image/jpeg",
                                ),
                            ),
                            accessibilityText = "some-teaser-image-accessibility-text-4",
                        ),
                        teaserText = "some-teaser-text-4",
                        type = "story",
                        advertUrl = null,
                        weblinkUrl = null,
                    ),
                    NewsItemDto(
                        creationDate = randomTimestamp5,
                        headline = "some-weblink-headline-5",
                        id = 5,
                        modifiedDate = randomTimestamp5,
                        teaserImage = TeaserImageDto(
                            links = LinksDto(
                                url = UrlDto(
                                    href = "https://ryanwong.co.uk/sample-resources/catnews/cat5_hero.jpg",
                                    templated = true,
                                    type = "image/jpeg",
                                ),
                            ),
                            accessibilityText = "some-teaser-image-accessibility-text-5",
                        ),
                        teaserText = null,
                        type = "weblink",
                        advertUrl = null,
                        weblinkUrl = "https://news.sky.com/weblinkUrl5",
                    ),
                    NewsItemDto(
                        creationDate = randomTimestamp6,
                        headline = "some-story-headline-6",
                        id = 6,
                        modifiedDate = randomTimestamp6,
                        teaserImage = TeaserImageDto(
                            links = LinksDto(
                                url = UrlDto(
                                    href = "https://ryanwong.co.uk/sample-resources/catnews/cat6_hero.jpg",
                                    templated = true,
                                    type = "image/jpeg",
                                ),
                            ),
                            accessibilityText = "some-teaser-image-accessibility-text-6",
                        ),
                        teaserText = "some-teaser-text-6",
                        type = "story",
                        advertUrl = null,
                        weblinkUrl = null,
                    ),
                ),
                title = "Cat News",
            ),
        )
    }

    /***
     * Simulate some changes for each request
     */
    private fun generateRandomPastDate(sequence: Int): String {
        val zonedDateTime = ZonedDateTime.now().minusDays(sequence.toLong()).withNano(0)
        return zonedDateTime.format(DateTimeFormatter.ISO_INSTANT)
    }
}
