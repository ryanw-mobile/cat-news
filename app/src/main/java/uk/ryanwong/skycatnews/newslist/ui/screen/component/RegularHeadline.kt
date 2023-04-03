/*
 * Copyright (c) 2022. Ryan Wong (hello@ryanwong.co.uk)
 */

package uk.ryanwong.skycatnews.newslist.ui.screen.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import coil.compose.AsyncImage
import coil.request.ImageRequest
import coil.size.Size
import uk.ryanwong.skycatnews.R
import uk.ryanwong.skycatnews.app.ui.theme.CustomTextStyle
import uk.ryanwong.skycatnews.app.ui.theme.SkyCatNewsTheme
import uk.ryanwong.skycatnews.newslist.domain.model.NewsItem
import uk.ryanwong.skycatnews.uk.ryanwong.skycatnews.app.ui.theme.getDimension

@Composable
fun RegularStoryHeadline(
    modifier: Modifier = Modifier,
    story: NewsItem.Story,
    onItemClicked: () -> Unit,
) {
    RegularHeadline(
        imageUrl = story.teaserImageUrl,
        imageAccessibilityText = story.teaserImageAccessibilityText,
        headline = story.headline,
        teaserText = story.teaserText,
        date = story.niceDate,
        onItemClicked = onItemClicked,
        modifier = modifier
    )
}

@Composable
fun RegularWebLinkHeadline(
    modifier: Modifier = Modifier,
    webLink: NewsItem.WebLink,
    onItemClicked: () -> Unit,
) {
    RegularHeadline(
        headline = webLink.headline,
        teaserText = null,
        date = webLink.niceDate,
        imageUrl = webLink.teaserImageUrl,
        imageAccessibilityText = webLink.teaserImageAccessibilityText,
        onItemClicked = onItemClicked,
        modifier = modifier
    )
}

@Composable
fun RegularHeadline(
    modifier: Modifier = Modifier,
    imageUrl: String?,
    imageAccessibilityText: String?,
    headline: String,
    teaserText: String?,
    date: String,
    onItemClicked: () -> Unit,
) {
    val dimension = LocalConfiguration.current.getDimension()

    Card(
        modifier = modifier
            .padding(horizontal = dimension.grid_2)
            .padding(bottom = dimension.grid_2)
            .fillMaxWidth()
            .wrapContentHeight()
            .defaultMinSize(minHeight = dimension.minListItemHeight)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .clickable(
                    enabled = true,
                    onClick = onItemClicked
                )
        ) {
            AsyncImage(
                model = ImageRequest
                    .Builder(LocalContext.current)
                    .data(imageUrl)
                    .size(Size.ORIGINAL)
                    .crossfade(true)
                    .build(),
                fallback = painterResource(R.drawable.placeholder),
                error = painterResource(R.drawable.placeholder),
                placeholder = painterResource(R.drawable.placeholder),
                contentDescription = imageAccessibilityText,
                contentScale = ContentScale.FillWidth,
                modifier = Modifier
                    .weight(0.3f)
                    .padding(all = dimension.grid_0_5)
            )

            Column(
                verticalArrangement = Arrangement.Center,
                modifier = modifier
                    .weight(0.7f)
                    .height(intrinsicSize = IntrinsicSize.Max)
            ) {
                Text(
                    text = headline,
                    maxLines = 1,
                    style = CustomTextStyle.regularHeadline,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight()
                        .padding(horizontal = dimension.grid_2, vertical = dimension.grid_0_5)
                )
                Text(
                    text = teaserText ?: "",
                    maxLines = 2,
                    style = CustomTextStyle.regularHeadlineTeaserText,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight()
                        .weight(weight = 1.0f)
                        .padding(horizontal = dimension.grid_2)
                )
                Text(
                    text = date,
                    maxLines = 1,
                    style = CustomTextStyle.regularHeadlineDate,
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight()
                        .padding(horizontal = dimension.grid_2, vertical = dimension.grid_1)
                )
            }
        }
    }
}

@Preview(
    name = "Regular Story Headline",
    showBackground = true,
)
@Composable
private fun RegularStoryHeadlinePreview() {
    SkyCatNewsTheme {
        RegularStoryHeadline(
            story = NewsItem.Story(
                newsId = 1,
                headline = "Story Headline 1 but it is getting really very long",
                teaserText = "Breakfast agreeable incommode departure it an. By ignorant at on wondered relation. Enough at tastes really so cousin am of. Extensive therefore supported by extremity of contented. Is pursuit compact demesne invited elderly be. View him she roof tell her case has sigh. Moreover is possible he admitted sociable concerns. By in cold no less been sent hard hill.",
                modifiedDate = "2022-09-01T00:00:00Z",
                niceDate = "2 days ago",
                teaserImageUrl = "https://www.google.com/",
                teaserImageAccessibilityText = "some-accessibility-text",
            ),
            onItemClicked = {},
        )
    }
}

@Preview(
    name = "Regular WebLink Headline",
    showBackground = true,
)
@Composable
private fun RegularWebLinkHeadlinePreview() {
    SkyCatNewsTheme {
        RegularWebLinkHeadline(
            webLink = NewsItem.WebLink(
                newsId = 1,
                headline = "Story WebLink but it is getting really very long",
                modifiedDate = "2022-09-06T00:00:00Z",
                niceDate = "2 days ago",
                teaserImageUrl = "https://www.google.com/",
                teaserImageAccessibilityText = "some-accessibility-text",
                url = "https://some.url/"
            ),
            onItemClicked = {},
        )
    }
}
