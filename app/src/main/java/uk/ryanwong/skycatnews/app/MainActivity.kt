/*
 * Copyright (c) 2022. Ryan Wong (hello@ryanwong.co.uk)
 */

package uk.ryanwong.skycatnews.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.material.rememberScaffoldState
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import uk.ryanwong.skycatnews.app.ui.SkyCatNewsApp
import uk.ryanwong.skycatnews.app.ui.component.SkyCatNewsAppBar
import uk.ryanwong.skycatnews.app.ui.theme.SkyCatNewsTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
            val scaffoldState = rememberScaffoldState()

            SkyCatNewsTheme {
                Scaffold(
                    scaffoldState = scaffoldState,
                    topBar = { SkyCatNewsAppBar(navController = navController) },
                ) { padding ->
                    SkyCatNewsApp(
                        navController = navController,
                        modifier = Modifier.padding(padding),
                    )
                }
            }
        }
    }
}
