/*
 * Copyright (c) 2022. Ryan Wong (hello@ryanwong.co.uk)
 */

package uk.ryanwong.catnews.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.material.rememberScaffoldState
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import uk.ryanwong.catnews.app.ui.CatNewsApp
import uk.ryanwong.catnews.app.ui.component.CatNewsAppBar
import uk.ryanwong.catnews.app.ui.theme.CatNewsTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
            val scaffoldState = rememberScaffoldState()

            CatNewsTheme {
                Scaffold(
                    scaffoldState = scaffoldState,
                    topBar = { CatNewsAppBar(navController = navController) },
                ) { padding ->
                    CatNewsApp(
                        navController = navController,
                        modifier = Modifier.padding(padding),
                    )
                }
            }
        }
    }
}
