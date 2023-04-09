/*
 * Copyright (c) 2023. Ryan Wong (hello@ryanwong.co.uk)
 */

package uk.ryanwong.skycatnews.uk.ryanwong.skycatnews.newslist.ui.screen.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import uk.ryanwong.skycatnews.R
import uk.ryanwong.skycatnews.app.ui.component.NoDataScreen
import uk.ryanwong.skycatnews.newslist.domain.model.NewsItem
import uk.ryanwong.skycatnews.newslist.ui.screen.component.LargeStoryHeadline
import uk.ryanwong.skycatnews.newslist.ui.screen.component.LargeWebLinkHeadline
import uk.ryanwong.skycatnews.newslist.ui.screen.component.RegularStoryHeadline
import uk.ryanwong.skycatnews.newslist.ui.screen.component.RegularWebLinkHeadline
import uk.ryanwong.skycatnews.uk.ryanwong.skycatnews.app.ui.theme.getDimension

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun NewsListScreenLayout(
    newsList: List<NewsItem>,
    isLoading: Boolean,
    onRefresh: () -> Unit,
    onStoryItemClicked: (id: Int) -> Unit,
    onWebLinkItemClicked: (id: Int) -> Unit,
) {
    val dimension = LocalConfiguration.current.getDimension()
    val contentDescriptionNewsList = stringResource(R.string.content_description_news_list)
    val pullRefreshState = rememberPullRefreshState(refreshing = isLoading, onRefresh = onRefresh)

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colors.background)
            .pullRefresh(pullRefreshState),
    ) {
        LazyColumn(
            contentPadding = PaddingValues(vertical = dimension.grid_2),
            modifier = Modifier
                .fillMaxSize()
                .semantics { contentDescription = contentDescriptionNewsList }
        ) {
            if (newsList.isEmpty()) {
                if (!isLoading) {
                    item {
                        NoDataScreen(modifier = Modifier.fillParentMaxHeight())
                    }
                }
                return@LazyColumn
            }

            item {
                val firstItem = newsList.first()
                when (firstItem) {
                    is NewsItem.Story -> {
                        LargeStoryHeadline(
                            story = firstItem,
                            onItemClicked = { onStoryItemClicked(firstItem.newsId) },
                        )
                    }

                    is NewsItem.WebLink -> {
                        LargeWebLinkHeadline(
                            webLink = firstItem,
                            onItemClicked = { onWebLinkItemClicked(firstItem.newsId) },
                        )
                    }
                }
            }

            val regularItemsList = newsList.subList(1, newsList.size)
            itemsIndexed(regularItemsList) { _, newsItem ->
                when (newsItem) {
                    is NewsItem.Story -> {
                        RegularStoryHeadline(
                            story = newsItem,
                            onItemClicked = { onStoryItemClicked(newsItem.newsId) },
                        )
                    }

                    is NewsItem.WebLink -> {
                        RegularWebLinkHeadline(
                            webLink = newsItem,
                            onItemClicked = { onWebLinkItemClicked(newsItem.newsId) },
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
