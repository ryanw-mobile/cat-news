/*
 * Copyright (c) 2022. Ryan Wong (hello@ryanwong.co.uk)
 */

package uk.ryanwong.catnews.storydetail.data.remote

import kotlinx.coroutines.delay
import uk.ryanwong.catnews.storydetail.data.remote.model.ContentDto
import uk.ryanwong.catnews.storydetail.data.remote.model.HeroImageDto
import uk.ryanwong.catnews.storydetail.data.remote.model.StoryDto

class FakeStoryService : StoryService {
    private val mockImageUrl = listOf(
        "https://ryanwong.co.uk/sample-resources/catnews/cat1_hero.jpg",
        "https://ryanwong.co.uk/sample-resources/catnews/cat2_hero.jpg",
        "https://ryanwong.co.uk/sample-resources/catnews/cat3_hero.jpg",
        "https://ryanwong.co.uk/sample-resources/catnews/cat4_hero.jpg",
        "https://ryanwong.co.uk/sample-resources/catnews/cat5_hero.jpg",
        "https://ryanwong.co.uk/sample-resources/catnews/cat6_hero.jpg",
    )

    override suspend fun getStory(storyId: Int): Result<StoryDto> {
        // simulate some network delay
        delay(1000)

        return Result.success(
            StoryDto(
                contents = listOf(
                    ContentDto(
                        accessibilityText = null,
                        text = "Is at purse tried jokes china ready decay an. Small its shy way had woody downs power. To denoting admitted speaking learning my exercise so in. Procured shutters mr it feelings. To or three offer house begin taken am at. As dissuade cheerful overcame so of friendly he indulged unpacked. Alteration connection to so as collecting me. Difficult in delivered extensive at direction allowance. Alteration put use diminution can considered sentiments interested discretion. An seeing feebly stairs am branch income me unable.",
                        type = "paragraph",
                        url = null,
                    ),
                    ContentDto(
                        accessibilityText = null,
                        text = "Old unsatiable our now but considered travelling impression. In excuse hardly summer in basket misery. By rent an part need. At wrong of of water those linen. Needed oppose seemed how all. Very mrs shed shew gave you. Oh shutters do removing reserved wandered an. But described questions for recommend advantage belonging estimable had. Pianoforte reasonable as so am inhabiting. Chatty design remark and his abroad figure but its.",
                        type = "paragraph",
                        url = null,
                    ),
                    ContentDto(
                        accessibilityText = "some-accessibility-text-1",
                        text = null,
                        type = "image",
                        url = mockImageUrl.random(),
                    ),
                    ContentDto(
                        accessibilityText = null,
                        text = "Add you viewing ten equally believe put. Separate families my on drawings do oh offended strictly elegance. Perceive jointure be mistress by jennings properly. An admiration at he discovered difficulty continuing. We in building removing possible suitable friendly on. Nay middleton him admitting consulted and behaviour son household. Recurred advanced he oh together entrance speedily suitable. Ready tried gay state fat could boy its among shall.",
                        type = "paragraph",
                        url = null,
                    ),
                    ContentDto(
                        accessibilityText = "some-accessibility-text-2",
                        text = null,
                        type = "image",
                        url = mockImageUrl.random(),
                    ),
                    ContentDto(
                        accessibilityText = null,
                        text = "On insensible possession oh particular attachment at excellence in. The books arose but miles happy she. It building contempt or interest children mistress of unlocked no. Offending she contained mrs led listening resembled. Delicate marianne absolute men dashwood landlord and offended. Suppose cottage between and way. Minuter him own clothes but observe country. Agreement far boy otherwise rapturous incommode favourite.",
                        type = "paragraph",
                        url = null,
                    ),
                ),
                creationDate = "2020-11-18T00:00:00Z",
                headline = "Cat story headline",
                heroImage = HeroImageDto(
                    accessibilityText = "some-hero-image-accessibility-text",
                    imageUrl = mockImageUrl.random(),
                ),
                id = storyId,
                modifiedDate = "2020-11-19T00:00:00Z",
            ),
        )
    }
}
