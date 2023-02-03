package ca.coffeeshopstudio.meksheets

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.runtime.*
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import ca.coffeeshopstudio.meksheets.ui.MechSheetsApp
import ca.coffeeshopstudio.meksheets.ui.theme.MechSheetsTheme
import ca.coffeeshopstudio.meksheets.ui.viewmodels.MechViewModel

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //this helps with debugging resources not being unlocked
//        if(BuildConfig.DEBUG)
//            StrictMode.enableDefaults();
        setContent {
            //get the theme preferences
            val viewModel: MechViewModel = viewModel()
            val uiState = viewModel.uiState.collectAsState().value
            val darkMode = uiState.darkMode
            val dynamicMode = uiState.dynamicTheme
            CompositionLocalProvider(
                LocalContentColor provides MaterialTheme.colorScheme.onSecondary // replace this with needed color from your pallete
            ) {
                MechSheetsTheme(darkMode, dynamicMode) {
                    val windowSize = calculateWindowSizeClass(this)
                    MechSheetsApp(
                        viewModel = viewModel,
                        uiState = uiState,
                        windowSize = windowSize.widthSizeClass)
                }
            }
        }
    }
}

@Preview
@Composable
fun MechSheetsAppPreview() {
    val viewModel: MechViewModel = viewModel()
    val uiState = viewModel.uiState.collectAsState().value
    MechSheetsTheme(0) {
        MechSheetsApp(windowSize = WindowWidthSizeClass.Compact,
            viewModel = viewModel, uiState = uiState)
    }
}

@Preview
@Composable
fun MechSheetsAppMediumPreview() {
    val viewModel: MechViewModel = viewModel()
    val uiState = viewModel.uiState.collectAsState().value
    MechSheetsTheme(0) {
        MechSheetsApp(windowSize = WindowWidthSizeClass.Medium,
            viewModel = viewModel, uiState = uiState)
    }
}

@Preview
@Composable
fun MechSheetsAppExpandedPreview() {
    val viewModel: MechViewModel = viewModel()
    val uiState = viewModel.uiState.collectAsState().value
    MechSheetsTheme(0) {
        MechSheetsApp(windowSize = WindowWidthSizeClass.Expanded,
            viewModel = viewModel, uiState = uiState)
    }
}