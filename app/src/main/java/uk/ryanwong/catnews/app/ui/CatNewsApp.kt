/*
 * Copyright (c) 2023. Ryan Wong (hello@ryanwong.co.uk)
 */

package uk.ryanwong.catnews.app.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import uk.ryanwong.catnews.newslist.ui.NewsListUIEvent
import uk.ryanwong.catnews.newslist.ui.screen.NewsListScreen
import uk.ryanwong.catnews.newslist.ui.viewmodel.NewsListViewModel
import uk.ryanwong.catnews.storydetail.ui.StoryDetailUIEvent
import uk.ryanwong.catnews.storydetail.ui.screen.StoryDetailScreen
import uk.ryanwong.catnews.storydetail.ui.viewmodel.StoryDetailViewModel
import uk.ryanwong.catnews.weblink.ui.WebLinkUIEvent
import uk.ryanwong.catnews.weblink.ui.screen.WebLinkScreen
import uk.ryanwong.catnews.weblink.ui.viewmodel.WebLinkViewModel

@Composable
fun CatNewsApp(
    modifier: Modifier = Modifier,
    navController: NavHostController,
) {
    NavHost(navController = navController, startDestination = "newslist") {
        composable(route = "newslist") {
            val newsListViewModel: NewsListViewModel = hiltViewModel()
            val uiState by newsListViewModel.uiState.collectAsStateWithLifecycle()

            NewsListScreen(
                modifier = modifier,
                uiState = uiState,
                uiEvent = NewsListUIEvent(
                    onRefresh = { newsListViewModel.refreshNewsList() },
                    onStoryItemClicked = { listId ->
                        navController.navigate(
                            "newslist/story/$listId",
                        )
                    },
                    onWebLinkItemClicked = { listId ->
                        navController.navigate(
                            "newslist/weblink/$listId",
                        )
                    },
                    onErrorShown = { errorId -> (newsListViewModel::errorShown)(errorId) },
                ),
            )
        }
        composable(
            route = "newslist/story/{list_id}",
            arguments = listOf(
                navArgument("list_id") {
                    type = NavType.IntType
                },
            ),
        ) {
            val storyDetailViewModel: StoryDetailViewModel = hiltViewModel()
            val uiState by storyDetailViewModel.uiState.collectAsStateWithLifecycle()

            StoryDetailScreen(
                modifier = modifier,
                uiState = uiState,
                uiEvent = StoryDetailUIEvent(
                    onRefresh = { storyDetailViewModel.refreshStory() },
                    onErrorShown = { errorId -> (storyDetailViewModel::errorShown)(errorId) },
                ),
            )
        }

        composable(
            route = "newslist/weblink/{list_id}",
            arguments = listOf(
                navArgument("list_id") {
                    type = NavType.IntType
                },
            ),
        ) {
            val webLinkViewModel: WebLinkViewModel = hiltViewModel()
            val uiState by webLinkViewModel.uiState.collectAsStateWithLifecycle()

            WebLinkScreen(
                modifier = modifier,
                uiState = uiState,
                uiEvent = WebLinkUIEvent(
                    onErrorShown = { errorId -> (webLinkViewModel::errorShown)(errorId) },
                ),
            )
        }
    }
}
