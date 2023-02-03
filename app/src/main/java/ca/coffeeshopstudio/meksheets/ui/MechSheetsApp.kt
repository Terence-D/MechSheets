package ca.coffeeshopstudio.meksheets.ui

import android.net.Uri
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import ca.coffeeshopstudio.meksheets.data.Mech
import ca.coffeeshopstudio.meksheets.data.MechCallbacks
import ca.coffeeshopstudio.meksheets.ui.screens.MechHomeScreen
import ca.coffeeshopstudio.meksheets.ui.state.*
import ca.coffeeshopstudio.meksheets.ui.viewmodels.MechViewModel

@Composable
fun MechSheetsApp(
    viewModel: MechViewModel,
    uiState: MechUiState,
    windowSize: WindowWidthSizeClass,
    modifier: Modifier = Modifier) {
    val navigationType: NavigationType
    val contentType: ContentType

    when (windowSize) {
        WindowWidthSizeClass.Medium -> {
            navigationType = NavigationType.NAVIGATION_RAIL
            contentType = ContentType.LIST_ONLY
        }
        WindowWidthSizeClass.Expanded -> {
            navigationType = NavigationType.PERMANENT_NAVIGATION_DRAWER
            contentType = ContentType.LIST_AND_DETAIL
        }
        else -> { //default to compact
            navigationType = NavigationType.TOP_NAVIGATION
            contentType = ContentType.LIST_ONLY
        }
    }

    val context = LocalContext.current
    val callbacks = MechCallbacks(
        onTabPressed = { navigationTabDestination: NavigationTabDestination ->
            viewModel.updateCurrentTab(navigationTabDestination = navigationTabDestination)
        },
        onMechSelected = { mech: Mech ->
            viewModel.updateSelectedMechStates(mech = mech)
        },
        onImportZip = { uri: Uri? ->
            viewModel.importZipFile(uri = uri!!, context = context)
        },
        onImportMm = { uri: Uri? ->
            viewModel.importMmFile(uri = uri!!, context = context)
        },
        onImportMtf = { uri: Uri? ->
            viewModel.importMtfFile(uri = uri!!, context = context)
        },
        onRecordSheetTabPressed = { tab: RecordSheetTab ->
            viewModel.updateRecordSheetTab(recordSheetTab = tab)
        },
        onEquipmentStateChanged = { index, fired ->
            viewModel.equipmentStateChanged(context, index, fired)
        },
        onAmmoStateChanged = {
                index, changeBy ->
            viewModel.ammoStateChanged(context, index, changeBy)
        },
        onArmorChanged = {
                location, adjustBy, internalStructure ->
            viewModel.changeArmorLevel(context, location, adjustBy, internalStructure)
        },
        onHeatChanged = {
            viewModel.changeHeatLevel(context, it)
        },
        onSaveDetails = {
            viewModel.saveDetails(context, it)
        },
        onChangeGunnery = {
            viewModel.changeGunnery(context, it)
        },
        onChangePilotHits = {
            viewModel.changePilotHits(context, it)
        },
        onChangePiloting = {
            viewModel.changePiloting(context, it)
        },
        onDelete = {
            viewModel.delete(context, uiState.currentMech)
        },
        onReset = {
            viewModel.reset(context, uiState.currentMech)
        },
        onUpdateMechComponent = {location, index ->
            viewModel.updateMechComponent(context, location, index)
        },
        onUpdateDarkMode = {
            viewModel.setDarkMode(context, it)
        },
        onUpdateDynamicColors = {
            viewModel.setDynamicColors(context, it)
        },
        onLoadImport = {
            viewModel.getListOfMechs(context)
        },
        onCheckLegacy = {
            viewModel.checkForLegacy(context)
        },
        onUpdateFolderFilter = {
            viewModel.updateFolderFilter(it)
        },
        onUpdateMechFilter = {
            viewModel.updateMechFilter(it)
        },
        onImport = {
            viewModel.importFromZip(context, it)
        },
        onUpdateMechName = {
            viewModel.updateMechName(context, it)
        },
        onUpdatePilotName = {
            viewModel.updatePilotName(context, it)
        },
        onUpdateColor = {
            viewModel.updateTeamColor(context, it)
        },
        onBackButton = { viewModel.onBackButton() },
        onToggleHelp = { viewModel.toggleHelp() }
    )

    MechHomeScreen(
        navigationType = navigationType,
        contentType = contentType,
        uiState = uiState,
        callbacks = callbacks,
        modifier = modifier,
    )
}