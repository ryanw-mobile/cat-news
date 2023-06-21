/*
 * Copyright (c) 2022. Ryan Wong (hello@ryanwong.co.uk)
 */

package uk.ryanwong.skycatnews.storydetail.ui.screen

import android.content.res.Configuration.UI_MODE_NIGHT_NO
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.SnackbarHost
import androidx.compose.material.SnackbarHostState
import androidx.compose.material.Text
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import coil.compose.AsyncImage
import coil.request.ImageRequest
import uk.ryanwong.skycatnews.R
import uk.ryanwong.skycatnews.app.ui.component.NoDataScreen
import uk.ryanwong.skycatnews.app.ui.theme.BlackGradientEnd
import uk.ryanwong.skycatnews.app.ui.theme.BlackGradientStart
import uk.ryanwong.skycatnews.app.ui.theme.CustomTextStyle
import uk.ryanwong.skycatnews.app.ui.theme.SkyCatNewsTheme
import uk.ryanwong.skycatnews.storydetail.ui.previewparameter.StoryProvider
import uk.ryanwong.skycatnews.uk.ryanwong.skycatnews.app.ui.theme.getDimension
import uk.ryanwong.skycatnews.uk.ryanwong.skycatnews.domain.model.storydetail.Content
import uk.ryanwong.skycatnews.uk.ryanwong.skycatnews.domain.model.storydetail.Story
import uk.ryanwong.skycatnews.uk.ryanwong.skycatnews.storydetail.ui.StoryDetailUIEvent
import uk.ryanwong.skycatnews.uk.ryanwong.skycatnews.storydetail.ui.StoryDetailUIState

@Composable
fun StoryDetailScreen(
    modifier: Modifier = Modifier,
    uiState: StoryDetailUIState,
    uiEvent: StoryDetailUIEvent,
) {
    val snackbarHostState = remember { SnackbarHostState() }

    Box(modifier = modifier.fillMaxSize()) {
        StoryDetailScreenLayout(
            story = uiState.story,
            isLoading = uiState.isLoading,
            onRefresh = uiEvent.onRefresh,
        )

        SnackbarHost(
            hostState = snackbarHostState,
            modifier = Modifier.align(Alignment.BottomCenter)
        )
    }

    if (uiState.errorMessages.isNotEmpty()) {
        val errorMessage = remember(uiState) { uiState.errorMessages[0] }
        val errorMessageText = stringResource(errorMessage.messageId)
        val actionLabel = stringResource(R.string.ok)

        LaunchedEffect(errorMessage.id) {
            snackbarHostState.showSnackbar(
                message = errorMessageText,
                actionLabel = actionLabel
            )
            uiEvent.onErrorShown(errorMessage.id)
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun StoryDetailScreenLayout(
    modifier: Modifier = Modifier,
    isLoading: Boolean,
    story: Story?,
    onRefresh: () -> Unit,
) {
    val dimension = LocalConfiguration.current.getDimension()
    val shouldAllowSwipeRefresh = (story == null && !isLoading)
    val contentDescriptionStoryDetail = stringResource(R.string.content_description_story_detail)
    val pullRefreshState = rememberPullRefreshState(refreshing = isLoading, onRefresh = onRefresh)

    val pullRefreshModifier = if (shouldAllowSwipeRefresh) {
        modifier.pullRefresh(pullRefreshState)
    } else {
        modifier
    }

    Box(
        modifier = pullRefreshModifier
            .fillMaxSize()
            .background(color = MaterialTheme.colors.background),
    ) {
        LazyColumn(
            contentPadding = PaddingValues(bottom = dimension.grid_2),
            modifier = Modifier
                .fillMaxSize()
                .semantics { contentDescription = contentDescriptionStoryDetail },
        ) {
            if (story == null) {
                if (!isLoading) {
                    item {
                        NoDataScreen(modifier = Modifier.fillParentMaxHeight())
                    }
                }
                return@LazyColumn
            }

            item {
                HeroImageSection(
                    headline = story.headline,
                    heroImageUrl = story.heroImageUrl,
                    heroImageAccessibilityText = story.heroImageAccessibilityText
                )
            }

            itemsIndexed(story.contents) { _, content ->
                when (content) {
                    is Content.Paragraph -> {
                        Text(
                            text = content.text,
                            color = MaterialTheme.colors.onBackground,
                            style = CustomTextStyle.storyDetailParagraph,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(all = dimension.grid_2)
                        )
                    }

                    is Content.Image -> {
                        AsyncImage(
                            model = ImageRequest
                                .Builder(LocalContext.current)
                                .data(content.url)
                                .crossfade(true)
                                .build(),
                            fallback = painterResource(R.drawable.placeholder),
                            error = painterResource(R.drawable.placeholder),
                            placeholder = painterResource(R.drawable.placeholder),
                            contentDescription = content.accessibilityText,
                            contentScale = ContentScale.FillWidth,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(all = dimension.grid_2)
                                .wrapContentHeight()
                        )
                    }
                }
            }
        }

        PullRefreshIndicator(
            refreshing = isLoading,
            state = pullRefreshState,
            modifier = Modifier.align(Alignment.TopCenter)
        )
    }
}

@Composable
private fun HeroImageSection(
    modifier: Modifier = Modifier,
    headline: String,
    heroImageUrl: String?,
    heroImageAccessibilityText: String?,
) {
    val dimension = LocalConfiguration.current.getDimension()

    Box(
        modifier = modifier
            .background(color = MaterialTheme.colors.primary)
            .fillMaxWidth()
            .wrapContentHeight()
    ) {
        AsyncImage(
            model = ImageRequest
                .Builder(LocalContext.current)
                .data(heroImageUrl)
                .crossfade(true)
                .build(),
            fallback = painterResource(R.drawable.placeholder),
            error = painterResource(R.drawable.placeholder),
            placeholder = painterResource(R.drawable.placeholder),
            contentDescription = heroImageAccessibilityText,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(ratio = 4 / 3f)
        )

        Column(
            modifier = Modifier
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            BlackGradientStart,
                            BlackGradientEnd
                        ),
                        startY = 0.0f,
                        endY = 100.0f
                    )
                )
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(all = dimension.grid_2)
                .align(alignment = Alignment.BottomStart)

        ) {
            Text(
                text = headline,
                style = CustomTextStyle.storyDetailHeadline,
                color = Color.White,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
            )
        }
    }
}

@Preview(
    group = "story loaded",
    name = "small screen - light",
    showSystemUi = true,
    showBackground = true,
    uiMode = UI_MODE_NIGHT_NO,
    device = "id:pixel_5"
)
@Preview(
    group = "story loaded",
    name = "regular screen - light",
    showSystemUi = true,
    showBackground = true,
    uiMode = UI_MODE_NIGHT_NO,
)
@Preview(
    group = "story loaded",
    name = "regular screen - dark",
    showSystemUi = true,
    showBackground = true,
    uiMode = UI_MODE_NIGHT_YES,
)
@Composable
private fun StoryDetailScreenLayoutPreview(
    @PreviewParameter(StoryProvider::class)
    story: Story,
) {
    SkyCatNewsTheme {
        StoryDetailScreenLayout(
            isLoading = false,
            story = story,
            onRefresh = {},
        )
    }
}

@Preview(
    group = "no data",
    name = "light",
    showSystemUi = true,
    showBackground = true,
    uiMode = UI_MODE_NIGHT_NO,
)
@Preview(
    group = "no data",
    name = "dark",
    showSystemUi = true,
    showBackground = true,
    uiMode = UI_MODE_NIGHT_YES,
)
@Composable
private fun StoryDetailScreenLayoutNoDataPreview() {
    SkyCatNewsTheme {
        StoryDetailScreenLayout(
            isLoading = false,
            story = null,
            onRefresh = {},
        )
    }
}
