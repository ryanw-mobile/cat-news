/*
 * Copyright (c) 2024. Ryan Wong (hello@ryanwong.co.uk)
 */

package uk.ryanwong.catnews.ui.screens

import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import uk.ryanwong.catnews.ui.components.CatNewsAppBar
import uk.ryanwong.catnews.ui.navigation.AppNavHost

@Composable
fun CatNewsApp() {
    val navController = rememberNavController()
    val scaffoldState = rememberScaffoldState()

    Scaffold(
        scaffoldState = scaffoldState,
        topBar = { CatNewsAppBar(navController = navController) },
    ) { padding ->
        AppNavHost(
            modifier = Modifier.padding(padding),
            navController = navController,
        )
    }
}
