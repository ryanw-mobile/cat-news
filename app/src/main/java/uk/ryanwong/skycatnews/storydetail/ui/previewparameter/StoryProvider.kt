/*
 * Copyright (c) 2022. Ryan Wong (hello@ryanwong.co.uk)
 */

package uk.ryanwong.skycatnews.storydetail.ui.previewparameter

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import uk.ryanwong.skycatnews.domain.model.storydetail.Content
import uk.ryanwong.skycatnews.domain.model.storydetail.Story

internal class StoryProvider : PreviewParameterProvider<Story> {
    override val values = sequenceOf(
        Story(
            id = 2,
            contents = listOf(
                Content.Paragraph(
                    text = "Is at purse tried jokes china ready decay an. Small its shy way had woody downs power. To denoting admitted speaking learning my exercise so in. Procured shutters mr it feelings. To or three offer house begin taken am at. As dissuade cheerful overcame so of friendly he indulged unpacked. Alteration connection to so as collecting me. Difficult in delivered extensive at direction allowance. Alteration put use diminution can considered sentiments interested discretion. An seeing feebly stairs am branch income me unable.",
                ),
                Content.Paragraph(
                    text = "Old unsatiable our now but considered travelling impression. In excuse hardly summer in basket misery. By rent an part need. At wrong of of water those linen. Needed oppose seemed how all. Very mrs shed shew gave you. Oh shutters do removing reserved wandered an. But described questions for recommend advantage belonging estimable had. Pianoforte reasonable as so am inhabiting. Chatty design remark and his abroad figure but its.",
                ),
                Content.Image(
                    url = "https://ryanwong.co.uk/sample-resources/skycatnews/cat6_hero.jpg",
                    accessibilityText = "some-accessibility-text-1",
                ),
                Content.Paragraph(
                    text = "Add you viewing ten equally believe put. Separate families my on drawings do oh offended strictly elegance. Perceive jointure be mistress by jennings properly. An admiration at he discovered difficulty continuing. We in building removing possible suitable friendly on. Nay middleton him admitting consulted and behaviour son household. Recurred advanced he oh together entrance speedily suitable. Ready tried gay state fat could boy its among shall.",
                ),
                Content.Image(
                    url = "https://ryanwong.co.uk/sample-resources/skycatnews/cat5_hero.jpg",
                    accessibilityText = "some-accessibility-text-2",
                ),
                Content.Paragraph(
                    text = "On insensible possession oh particular attachment at excellence in. The books arose but miles happy she. It building contempt or interest children mistress of unlocked no. Offending she contained mrs led listening resembled. Delicate marianne absolute men dashwood landlord and offended. Suppose cottage between and way. Minuter him own clothes but observe country. Agreement far boy otherwise rapturous incommode favourite.",
                ),
            ),
            date = "2020-11-19T00:00:00Z",
            headline = "Cat story headline",
            heroImageAccessibilityText = "some-hero-image-accessibility-text",
            heroImageUrl = "https://ryanwong.co.uk/sample-resources/skycatnews/cat5_hero.jpg",
        ),
    )
}
