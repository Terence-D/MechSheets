package ca.coffeeshopstudio.meksheets.ui.screens

import androidx.activity.compose.BackHandler
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import ca.coffeeshopstudio.meksheets.R
import ca.coffeeshopstudio.meksheets.data.MechCallbacks
import ca.coffeeshopstudio.meksheets.ui.composables.MechNavBar
import ca.coffeeshopstudio.meksheets.ui.composables.MechNavBarItemContent
import ca.coffeeshopstudio.meksheets.ui.state.MechUiState
import ca.coffeeshopstudio.meksheets.ui.state.RecordSheetTab

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MechDetailsScreen(
    uiState: MechUiState,
    modifier: Modifier = Modifier,
    navigationItemContentList: List<MechNavBarItemContent>,
    callbacks: MechCallbacks) {
    BackHandler {
        if (!uiState.isShowingHomepage) {
            callbacks.onBackButton()
        }
    }
    Column(
        modifier = modifier
            .fillMaxSize()
    ) {
        LazyColumn(modifier = Modifier
            .weight(1f)
            .padding(8.dp),
        ) {
            item {
                Row (horizontalArrangement = Arrangement.Center, modifier = Modifier) {
                    var mechName by remember { mutableStateOf(uiState.currentMech.name) }
                    TextField(
                        value = mechName,
                        modifier = Modifier.weight(5f),
                        textStyle = MaterialTheme.typography.titleLarge,
                        maxLines = 1,
                        onValueChange = {
                            mechName = it
                        }
                    )
                    Button(modifier = Modifier.weight(1f),
                        onClick = {
                            callbacks.onUpdateMechName(mechName)
                        }) {
                        Icon(
                            imageVector = Icons.Default.Edit,
                            contentDescription = stringResource(R.string.change_name)
                        )
                    }
                }
            }
            when (uiState.recordSheetTab) {
                RecordSheetTab.Overview -> item {
                    RecordSheetOverview(
                        uiState = uiState,
                        callbacks = callbacks,
                        modifier = modifier
                    )
                }
                RecordSheetTab.Pilot -> item {
                    RecordSheetPilot(
                        uiState = uiState,
                        modifier = modifier,
                        callbacks = callbacks,
                    )
                }
                RecordSheetTab.Armor -> item {
                    RecordSheetArmor(
                        uiState = uiState,
                        callbacks = callbacks,
                        modifier = modifier)
                }
                RecordSheetTab.Components -> item {
                    RecordSheetComponents(
                        uiState = uiState,
                        callbacks = callbacks,
                        modifier = modifier)
                }
            }
        }
        MechNavBar(
            currentTab = uiState.recordSheetTab,
            onTabPressed = callbacks.onRecordSheetTabPressed,
            recordSheetTabList = navigationItemContentList,
            modifier = Modifier.testTag("details"),
        )
    }
}

@Composable
fun SpeedTextEntry(
    @StringRes textRes: Int,
    currentSpeed: Int,
    maxSpeed: Int,
    modifier: Modifier = Modifier) {
    var color = MaterialTheme.colorScheme.primary
    if (maxSpeed > currentSpeed)
        color = MaterialTheme.colorScheme.error

    Text(
        buildAnnotatedString {
        append ("${stringResource(textRes)}: ")
        withStyle(style = SpanStyle(color = color)) {
            append(currentSpeed.toString())
        }
        append (" ($maxSpeed)")
    })
}

