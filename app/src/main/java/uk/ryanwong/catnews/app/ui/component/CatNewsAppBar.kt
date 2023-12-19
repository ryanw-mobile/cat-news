/*
 * Copyright (c) 2022. Ryan Wong (hello@ryanwong.co.uk)
 */

package uk.ryanwong.catnews.app.ui.component

import android.content.res.Configuration.UI_MODE_NIGHT_NO
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.ContentAlpha
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.LocalContentAlpha
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import uk.ryanwong.catnews.R
import uk.ryanwong.catnews.app.ui.theme.CatNewsTheme
import uk.ryanwong.catnews.app.ui.theme.getDimension

@Composable
fun CatNewsAppBar(
    modifier: Modifier = Modifier,
    navController: NavController,
    customTitle: String? = null,
) {
    // Reference: https://stackoverflow.com/questions/65079798/scaffold-with-topappbar-integration-with-navigation
    val (canPop, setCanPop) = remember { mutableStateOf(false) }
    navController.addOnDestinationChangedListener { controller, _, _ ->
        setCanPop(controller.previousBackStackEntry != null)
    }

    val dimension = LocalConfiguration.current.getDimension()

    TopAppBar(
        backgroundColor = MaterialTheme.colors.surface,
        modifier = modifier,
    ) {
        Box(Modifier.height(dimension.appBarHeight)) {
            // TODO: Material-3 will provide a new way to achieve this - pending rewrite.
            // Doing in this way instead of using the default navigationIcon slot,
            // because we cannot properly center align our custom image title in that case.

            // Navigation Icon
            if (canPop) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    CompositionLocalProvider(
                        LocalContentAlpha provides ContentAlpha.high,
                    ) {
                        IconButton(onClick = {
                            navController.navigateUp()
                        }) {
                            Icon(Icons.Rounded.ArrowBack, "")
                        }
                    }
                }
            }

            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth(),
            ) {
                customTitle?.let {
                    Text(
                        text = customTitle,
                        color = MaterialTheme.colors.onSurface,
                        style = MaterialTheme.typography.h6,
                    )
                } ?: Image(
                    painter = painterResource(id = R.drawable.header_catnews),
                    contentDescription = stringResource(R.string.app_name),
                    alignment = Alignment.Center,
                )
            }
        }
    }
}

@Preview(
    name = "Default image app bar",
    group = "Light",
    showBackground = true,
    uiMode = UI_MODE_NIGHT_NO,
)
@Composable
private fun AppBarPreviewDefaultImageLight() {
    CatNewsTheme {
        CatNewsAppBar(
            customTitle = null,
            navController = rememberNavController(),
        )
    }
}

@Preview(
    name = "Custom title app bar",
    group = "Light",
    showBackground = true,
    uiMode = UI_MODE_NIGHT_NO,
)
@Composable
private fun AppBarPreviewCustomTitleLight() {
    CatNewsTheme {
        CatNewsAppBar(
            customTitle = "Some Other News",
            navController = rememberNavController(),
        )
    }
}

@Preview(
    name = "Default image app bar",
    group = "Dark",
    showBackground = true,
    uiMode = UI_MODE_NIGHT_YES,
)
@Composable
private fun AppBarPreviewDefaultImageDark() {
    CatNewsTheme {
        CatNewsAppBar(
            customTitle = null,
            navController = rememberNavController(),
        )
    }
}

@Preview(
    name = "Custom title app bar",
    group = "Dark",
    showBackground = true,
    uiMode = UI_MODE_NIGHT_YES,
)
@Composable
private fun AppBarPreviewCustomTitleDark() {
    CatNewsTheme {
        CatNewsAppBar(
            customTitle = "Some Other News",
            navController = rememberNavController(),
        )
    }
}
