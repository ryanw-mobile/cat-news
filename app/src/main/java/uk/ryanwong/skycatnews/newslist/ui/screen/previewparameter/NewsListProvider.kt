/*
 * Copyright (c) 2022. Ryan Wong (hello@ryanwong.co.uk)
 */

package uk.ryanwong.skycatnews.newslist.ui.screen.previewparameter

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import uk.ryanwong.skycatnews.uk.ryanwong.skycatnews.domain.model.newslist.NewsItem

internal class NewsListProvider : PreviewParameterProvider<List<NewsItem>> {
    override val values = sequenceOf(
        listOf(
            NewsItem.WebLink(
                newsId = 5,
                headline = "Weblink headline",
                url = "https://news.sky.com/story/tory-leadership-rishi-sunak-and-liz-truss-promise-to-increase-scrutiny-of-scottish-govt-as-they-head-to-perth-12674081",
                modifiedDate = "2022-09-06T02:30:19Z",
                niceDate = "2 days ago",
                teaserImageUrl = "https://ryanwong.co.uk/sample-resources/skycatnews/cat5_hero.jpg",
                teaserImageAccessibilityText = "Image content description"
            ),

            NewsItem.Story(
                newsId = 2,
                headline = "Story Headline",
                teaserText = "Created said male greater form. Likeness the light grass they're darkness saw thing set set doesn't fruit without after was creepeth abundantly is is a unto us so very thing let beginning living gathering seasons thing very, darkness brought you'll fruit earth signs creature air light moving bring seasons and saw. Second living green years, him it every fruitful saying one also creature for waters saw morning fifth. Of dry seas creepeth, unto place creature days. Female void called gathered herb him grass can't tree set. They're yielding earth. The wherein air dominion god blessed made us open seed don't.",
                modifiedDate = "2022-09-05T14:26:19Z",
                niceDate = "2 days ago",
                teaserImageUrl = "https://ryanwong.co.uk/sample-resources/skycatnews/cat2_hero.jpg",
                teaserImageAccessibilityText = "Image content description"
            ),

            NewsItem.WebLink(
                newsId = 3,
                headline = "Weblink headline",
                url = "https://news.sky.com/story/john-shuttleworth-comedy-gig-inside-cave-halted-halfway-after-fan-gets-trapped-in-tree-above-gorge-12617846",
                modifiedDate = "2022-09-03T09:11:19Z",
                niceDate = "2 days ago",
                teaserImageUrl = "https://ryanwong.co.uk/sample-resources/skycatnews/cat3_hero.jpg",
                teaserImageAccessibilityText = "Image content description"
            ),

            NewsItem.Story(
                newsId = 1,
                headline = "Story Headline",
                teaserText = "Signs behold brought over give the also. Fish hath void. Face. Sixth appear all own spirit. Set can't fowl had fowl fish fowl male living form life winged two form from fifth he evening fowl abundantly gathered own wherein blessed. Forth fruit kind the is them herb divided moveth land, deep abundantly good gathering after. Earth good him day rule fish fill place which created his she'd fill, is Together itself had second Fourth lesser be us beginning earth. Dry meat don't winged under seas of own hath signs fruitful, evening. You'll of. Lesser heaven lights have tree light, us.",
                modifiedDate = "2022-09-02T11:52:19Z",
                niceDate = "2 days ago",
                teaserImageUrl = "https://ryanwong.co.uk/sample-resources/skycatnews/cat1_hero.jpg",
                teaserImageAccessibilityText = "Image content description"
            ),

            NewsItem.Story(
                newsId = 4,
                headline = "Story headline",
                teaserText = "Created shall divided winged above spirit. Green, waters first seed evening saw for hath male make moving every set forth cattle herb behold i them is. Yielding, you'll great whales us winged own whose over to created green darkness sixth also fly itself won't you're won't. Moved she'd greater fruit fruitful whales called bring seasons lesser in itself living earth be own heaven moved fowl from us form. Fill appear cattle she'd open. Fill two saying hath Have open you from in light dry cattle man very waters over to whose herb was sea great given god darkness that first.",
                modifiedDate = "2022-08-31T05:23:19Z",
                niceDate = "2 days ago",
                teaserImageUrl = "https://ryanwong.co.uk/sample-resources/skycatnews/cat4_hero.jpg",
                teaserImageAccessibilityText = "Image content description"
            ),

            NewsItem.Story(
                newsId = 6,
                headline = "Story headline",
                teaserText = "Created shall divided winged above spirit. Green, waters first seed evening saw for hath male make moving every set forth cattle herb behold i them is. Yielding, you'll great whales us winged own whose over to created green darkness sixth also fly itself won't you're won't. Moved she'd greater fruit fruitful whales called bring seasons lesser in itself living earth be own heaven moved fowl from us form. Fill appear cattle she'd open. Fill two saying hath Have open you from in light dry cattle man very waters over to whose herb was sea great given god darkness that first.",
                modifiedDate = "2022-08-31T05:23:19Z",
                niceDate = "2 days ago",
                teaserImageUrl = "https://ryanwong.co.uk/sample-resources/skycatnews/cat6_hero.jpg",
                teaserImageAccessibilityText = "Image content description"
            ),
        )
    )
}
