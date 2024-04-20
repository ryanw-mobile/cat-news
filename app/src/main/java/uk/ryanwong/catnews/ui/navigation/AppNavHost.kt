/*
 * Copyright (c) 2024. Ryan Wong (hello@ryanwong.co.uk)
 */

package uk.ryanwong.catnews.ui.navigation

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
import uk.ryanwong.catnews.ui.screens.newslist.NewsListScreen
import uk.ryanwong.catnews.ui.screens.newslist.NewsListUIEvent
import uk.ryanwong.catnews.ui.screens.storydetail.StoryDetailScreen
import uk.ryanwong.catnews.ui.screens.storydetail.StoryDetailUIEvent
import uk.ryanwong.catnews.ui.screens.weblink.WebLinkScreen
import uk.ryanwong.catnews.ui.screens.weblink.WebLinkUIEvent
import uk.ryanwong.catnews.ui.viewmodel.NewsListViewModel
import uk.ryanwong.catnews.ui.viewmodel.StoryDetailViewModel
import uk.ryanwong.catnews.ui.viewmodel.WebLinkViewModel

@Composable
fun AppNavHost(
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
