/*
 * Copyright (c) 2024. Ryan Wong (hello@ryanwong.co.uk)
 */

package uk.ryanwong.catnews.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import coil.compose.AsyncImage
import coil.request.ImageRequest
import uk.ryanwong.catnews.R
import uk.ryanwong.catnews.domain.model.newslist.NewsItem
import uk.ryanwong.catnews.ui.theme.CatNewsTheme
import uk.ryanwong.catnews.ui.theme.CustomTextStyle
import uk.ryanwong.catnews.ui.theme.getDimension

@Composable
fun LargeStoryHeadline(
    modifier: Modifier = Modifier,
    story: NewsItem.Story,
    onItemClicked: () -> Unit,
) {
    LargeHeadline(
        imageUrl = story.teaserImageUrl,
        imageAccessibilityText = story.teaserImageAccessibilityText,
        headline = story.headline,
        teaserText = story.teaserText,
        date = story.niceDate,
        onItemClicked = onItemClicked,
        modifier = modifier,
    )
}

@Composable
fun LargeWebLinkHeadline(
    modifier: Modifier = Modifier,
    webLink: NewsItem.WebLink,
    onItemClicked: () -> Unit,
) {
    LargeHeadline(
        imageUrl = webLink.teaserImageUrl,
        imageAccessibilityText = webLink.teaserImageAccessibilityText,
        headline = webLink.headline,
        teaserText = null,
        date = webLink.niceDate,
        onItemClicked = onItemClicked,
        modifier = modifier,
    )
}

@Composable
fun LargeHeadline(
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
            .aspectRatio(ratio = 1f, matchHeightConstraintsFirst = true),
    ) {
        Column(
            modifier = Modifier
                .background(color = MaterialTheme.colors.primary)
                .fillMaxWidth()
                .wrapContentHeight()
                .clickable(
                    enabled = true,
                    onClick = onItemClicked,
                ),
        ) {
            AsyncImage(
                model = ImageRequest
                    .Builder(LocalContext.current)
                    .data(imageUrl)
                    .crossfade(true)
                    .build(),
                fallback = painterResource(R.drawable.placeholder),
                error = painterResource(R.drawable.placeholder),
                placeholder = painterResource(R.drawable.placeholder),
                contentDescription = imageAccessibilityText,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .weight(weight = 1f, fill = true),
            )

            Text(
                text = headline,
                maxLines = 1,
                style = CustomTextStyle.largeHeadline,
                color = MaterialTheme.colors.onPrimary,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .padding(horizontal = dimension.grid_2, vertical = dimension.grid_1),
            )

            teaserText?.let {
                Text(
                    text = teaserText,
                    maxLines = 3,
                    style = CustomTextStyle.largeHeadlineTeaserText,
                    color = MaterialTheme.colors.onPrimary,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight()
                        .padding(horizontal = dimension.grid_2),
                )
            }

            Text(
                text = date,
                maxLines = 1,
                style = CustomTextStyle.largeHeadlineDate,
                color = MaterialTheme.colors.onPrimary,
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .padding(horizontal = dimension.grid_2, vertical = dimension.grid_1),
            )
        }
    }
}

@Preview(
    name = "Large Story Headline",
    showBackground = true,
    heightDp = 320,
)
@Composable
private fun LargeStoryHeadlinePreview() {
    CatNewsTheme {
        LargeStoryHeadline(
            story = NewsItem.Story(
                newsId = 1,
                headline = "Story Headline 1",
                teaserText = "Breakfast agreeable incommode departure it an. By ignorant at on wondered relation. Enough at tastes really so cousin am of. Extensive therefore supported by extremity of contented. Is pursuit compact demesne invited elderly be. View him she roof tell her case has sigh. Moreover is possible he admitted sociable concerns. By in cold no less been sent hard hill.",
                modifiedDate = "2022-09-06T00:00:00Z",
                niceDate = "2 days ago",
                teaserImageUrl = "https://ryanwong.co.uk/sample-resources/catnews/cat1_hero.jpg",
                teaserImageAccessibilityText = "some-accessibility-text",
            ),
            onItemClicked = {},
        )
    }
}

@Preview(
    name = "Large WebLink Headline",
    showBackground = true,
    heightDp = 320,
)
@Composable
private fun LargeWebLinkHeadlinePreview() {
    CatNewsTheme {
        LargeWebLinkHeadline(
            webLink = NewsItem.WebLink(
                newsId = 1,
                headline = "Story Headline 1",
                url = "https://some.url/",
                modifiedDate = "2022-09-06T00:00:00Z",
                niceDate = "2 days ago",
                teaserImageUrl = "https://ryanwong.co.uk/sample-resources/catnews/cat1_hero.jpg",
                teaserImageAccessibilityText = "some-accessibility-text",
            ),
            onItemClicked = {},
        )
    }
}
