/*
 * Copyright (c) 2024. Ryan Wong (hello@ryanwong.co.uk)
 */

package uk.ryanwong.catnews.data.datasource.remote

import kotlinx.coroutines.delay
import uk.ryanwong.catnews.data.datasource.remote.interfaces.NewsListService
import uk.ryanwong.catnews.data.dto.LinksDto
import uk.ryanwong.catnews.data.dto.NewsItemDto
import uk.ryanwong.catnews.data.dto.NewsListDto
import uk.ryanwong.catnews.data.dto.TeaserImageDto
import uk.ryanwong.catnews.data.dto.UrlDto
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

class FakeNewsListService : NewsListService {
    override suspend fun getAllItems(): Result<NewsListDto> {
        val randomTimestamp1 = generateRandomPastDate()
        val randomTimestamp2 = generateRandomPastDate()
        val randomTimestamp3 = generateRandomPastDate()
        val randomTimestamp4 = generateRandomPastDate()
        val randomTimestamp5 = generateRandomPastDate()
        val randomTimestamp6 = generateRandomPastDate()

        // simulate some network delay
        delay(1000)

        // randomly return no data to trigger the no data screen
        if ((0..100).random() % 6 == 0) {
            return Result.success(NewsListDto(news = emptyList(), title = "Cat News"))
        }

        return Result.success(
            NewsListDto(
                news = listOf(
                    NewsItemDto(
                        creationDate = randomTimestamp1,
                        headline = "Story Headline",
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
                            accessibilityText = "Image content description",
                        ),
                        teaserText = "Signs behold brought over give the also. Fish hath void. Face. Sixth appear all own spirit. Set can't fowl had fowl fish fowl male living form life winged two form from fifth he evening fowl abundantly gathered own wherein blessed. Forth fruit kind the is them herb divided moveth land, deep abundantly good gathering after. Earth good him day rule fish fill place which created his she'd fill, is Together itself had second Fourth lesser be us beginning earth. Dry meat don't winged under seas of own hath signs fruitful, evening. You'll of. Lesser heaven lights have tree light, us.",
                        type = "story",
                        advertUrl = null,
                        weblinkUrl = null,
                    ),
                    NewsItemDto(
                        creationDate = randomTimestamp2,
                        headline = "Story Headline",
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
                            accessibilityText = "Image content description",
                        ),
                        teaserText = "Created said male greater form. Likeness the light grass they're darkness saw thing set set doesn't fruit without after was creepeth abundantly is is a unto us so very thing let beginning living gathering seasons thing very, darkness brought you'll fruit earth signs creature air light moving bring seasons and saw. Second living green years, him it every fruitful saying one also creature for waters saw morning fifth. Of dry seas creepeth, unto place creature days. Female void called gathered herb him grass can't tree set. They're yielding earth. The wherein air dominion god blessed made us open seed don't.",
                        type = "story",
                        advertUrl = null,
                        weblinkUrl = null,
                    ),
                    NewsItemDto(
                        creationDate = null,
                        headline = null,
                        id = null,
                        modifiedDate = null,
                        teaserImage = null,
                        teaserText = null,
                        type = "advert",
                        advertUrl = "advert/url",
                        weblinkUrl = null,
                    ),
                    NewsItemDto(
                        creationDate = randomTimestamp3,
                        headline = "Weblink headline",
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
                            accessibilityText = "Image content description",
                        ),
                        teaserText = null,
                        type = "weblink",
                        advertUrl = null,
                        weblinkUrl = "https://news.sky.com/story/rail-strikes-industrial-action-continues-today-with-only-20-of-services-expected-to-run-12677136",
                    ),
                    NewsItemDto(
                        creationDate = randomTimestamp4,
                        headline = "Story headline",
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
                            accessibilityText = "Image content description",
                        ),
                        teaserText = "Created shall divided winged above spirit. Green, waters first seed evening saw for hath male make moving every set forth cattle herb behold i them is. Yielding, you'll great whales us winged own whose over to created green darkness sixth also fly itself won't you're won't. Moved she'd greater fruit fruitful whales called bring seasons lesser in itself living earth be own heaven moved fowl from us form. Fill appear cattle she'd open. Fill two saying hath Have open you from in light dry cattle man very waters over to whose herb was sea great given god darkness that first.",
                        type = "story",
                        advertUrl = null,
                        weblinkUrl = null,
                    ),
                    NewsItemDto(
                        creationDate = randomTimestamp5,
                        headline = "Weblink headline",
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
                            accessibilityText = "Image content description",
                        ),
                        teaserText = null,
                        type = "weblink",
                        advertUrl = null,
                        weblinkUrl = "https://news.sky.com/story/transport-strikes-more-travel-misery-as-london-underground-and-bus-staff-begin-latest-walkout-12676263",
                    ),
                    NewsItemDto(
                        creationDate = randomTimestamp6,
                        headline = "Story headline",
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
                            accessibilityText = "Image content description",
                        ),
                        teaserText = "Created shall divided winged above spirit. Green, waters first seed evening saw for hath male make moving every set forth cattle herb behold i them is. Yielding, you'll great whales us winged own whose over to created green darkness sixth also fly itself won't you're won't. Moved she'd greater fruit fruitful whales called bring seasons lesser in itself living earth be own heaven moved fowl from us form. Fill appear cattle she'd open. Fill two saying hath Have open you from in light dry cattle man very waters over to whose herb was sea great given god darkness that first.",
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
    private fun generateRandomPastDate(): String {
        val randomOffset = (0..10080).random().toLong()
        val zonedDateTime = ZonedDateTime.now().minusMinutes(randomOffset).withNano(0)
        return zonedDateTime.format(DateTimeFormatter.ISO_INSTANT)
    }
}
