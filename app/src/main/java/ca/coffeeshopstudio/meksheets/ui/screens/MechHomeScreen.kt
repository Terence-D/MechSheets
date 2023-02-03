package ca.coffeeshopstudio.meksheets.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.PermanentDrawerSheet
import androidx.compose.material3.PermanentNavigationDrawer
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import ca.coffeeshopstudio.meksheets.R
import ca.coffeeshopstudio.meksheets.data.MechCallbacks
import ca.coffeeshopstudio.meksheets.ui.composables.*
import ca.coffeeshopstudio.meksheets.ui.screens.importing.MechImportScreen
import ca.coffeeshopstudio.meksheets.ui.state.*
import ca.coffeeshopstudio.meksheets.ui.utils.ImageLibrary

@Composable
fun MechHomeScreen(
    navigationType: NavigationType,
    contentType: ContentType,
    uiState: MechUiState,
    callbacks: MechCallbacks,
    modifier: Modifier = Modifier,
) {
    val navigationItemContentList = getNavigationContent()
    if (navigationType == NavigationType.PERMANENT_NAVIGATION_DRAWER) {
        val navigationDrawerContentDescription = stringResource(R.string.navigation_drawer)
        PermanentNavigationDrawer(
            drawerContent = {
                PermanentDrawerSheet(Modifier.width(240.dp)) {
                    MechNavigationDrawerContent(
                        selectedDestination = uiState.currentDestination,
                        onTabPressed = callbacks.onTabPressed,
                        navigationItemContentList = navigationItemContentList
                    )
                }
            },
            modifier = Modifier.testTag(navigationDrawerContentDescription)
        ) {
            AppContent(
                navigationType = navigationType,
                contentType = contentType,
                uiState = uiState,
                callbacks = callbacks,
                navigationItemContentList = navigationItemContentList,
                modifier = modifier,
            )
        }
    } else {
        AppContent(
            navigationType = navigationType,
            contentType = contentType,
            callbacks = callbacks,
            uiState = uiState,
            navigationItemContentList = navigationItemContentList,
            modifier = modifier,
        )
    }
}

@Composable
fun AppContent(
    navigationType: NavigationType,
    contentType: ContentType,
    uiState: MechUiState,
    callbacks: MechCallbacks,
    navigationItemContentList: List<NavigationItemContent>,
    modifier: Modifier = Modifier,
) {
    callbacks.onCheckLegacy()
    Row(modifier = modifier.fillMaxSize()) {
        AnimatedVisibility(visible = navigationType == NavigationType.NAVIGATION_RAIL) {
            val navigationRailContentDescription = stringResource(R.string.navigation_rail)
            MechNavigationRail(
                currentTab = uiState.currentDestination,
                onTabPressed = callbacks.onTabPressed,
                navigationItemContentList = navigationItemContentList,
                modifier = Modifier.testTag(navigationRailContentDescription)
            )
        }
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.inverseOnSurface)
        ) {
            AnimatedVisibility(visible = navigationType == NavigationType.TOP_NAVIGATION) {
                val bottomNavigationContentDescription = stringResource(R.string.navigation_bottom)
                MechTopToolbar(
                    onTabPressed = callbacks.onTabPressed,
                    navigationItemContentList = navigationItemContentList,
                    modifier = Modifier.testTag(bottomNavigationContentDescription)
                )
            }
            when (uiState.currentDestination) {
                NavigationTabDestination.MechList -> {
                    MechRecordSheet(contentType, uiState, callbacks,
                    modifier = Modifier.weight(1f))
                }
                NavigationTabDestination.Add -> {
                    MechImportScreen(
                        contentType = contentType,
                        uiState = uiState,
                        callbacks = callbacks,
                        modifier = Modifier.weight(1f),
                    )
                }
                NavigationTabDestination.Help -> callbacks.onToggleHelp()
                NavigationTabDestination.Options -> {
                    MechSettings(
                        uiState = uiState,
                        onUpdateDarkMode = callbacks.onUpdateDarkMode,
                        modifier = Modifier.weight(1f),
                        onUpdateDynamicColors = callbacks.onUpdateDynamicColors,
                    )
                }
            }
        }
    }
}

@Composable
private fun getNavigationContent(): List<NavigationItemContent> {
    val imageLibrary = ImageLibrary()
    return listOf(
        NavigationItemContent(
            tabType = NavigationTabDestination.MechList,
            icon = imageLibrary.IconMechList,
            text = stringResource(id = R.string.tab_mech_list)
        ),
        NavigationItemContent(
            tabType = NavigationTabDestination.Add,
            icon = imageLibrary.IconImport,
            text = stringResource(id = R.string.tab_import)
        ),
        NavigationItemContent(
            tabType = NavigationTabDestination.Help,
            icon = imageLibrary.IconHelp,
            text = stringResource(id = R.string.tab_help)
        ),
        NavigationItemContent(
            tabType = NavigationTabDestination.Options,
            icon = imageLibrary.IconOptions,
            text = stringResource(id = R.string.tab_options)
        )
    )
}

@Composable
fun getRecordSheetNavContent(): List<MechNavBarItemContent> {
    val imageLibrary = ImageLibrary()
    return listOf(
        MechNavBarItemContent(
            tabType = RecordSheetTab.Overview,
            icon = imageLibrary.IconOverview,
            text = stringResource(id = R.string.tab_rs_overivew)
        ),
        MechNavBarItemContent(
            tabType = RecordSheetTab.Pilot,
            icon = imageLibrary.IconPilot,
            text = stringResource(id = R.string.tab_rs_pilot)
        ),
        MechNavBarItemContent(
            tabType = RecordSheetTab.Armor,
            icon = imageLibrary.IconArmor,
            text = stringResource(id = R.string.tab_rs_armor)
        ),
        MechNavBarItemContent(
            tabType = RecordSheetTab.Components,
            icon = imageLibrary.IconComponents,
            text = stringResource(id = R.string.tab_rs_components)
        ),
    )
}
