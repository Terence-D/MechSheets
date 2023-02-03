package ca.coffeeshopstudio.meksheets.data

import android.net.Uri
import ca.coffeeshopstudio.meksheets.ui.state.NavigationTabDestination
import ca.coffeeshopstudio.meksheets.ui.state.RecordSheetTab

class MechCallbacks(
    val onReset: () -> Unit,
    val onDelete: () -> Unit,
    val onUpdateDarkMode: (Int) -> Unit,
    val onUpdateDynamicColors: (Boolean) -> Unit,
    val onTabPressed: (NavigationTabDestination) -> Unit,
    val onEquipmentStateChanged: (Int, Boolean) -> Unit,
    val onRecordSheetTabPressed: (RecordSheetTab) -> Unit,
    val onAmmoStateChanged: (Int, Int) -> Unit,
    val onChangeGunnery: (Int) -> Unit,
    val onChangePiloting: (Int) -> Unit,
    val onChangePilotHits: (Int) -> Unit,
    val onUpdateMechComponent: (Location, Int) -> Unit,
    val onMechSelected: (Mech) -> Unit,
    val onArmorChanged: (Location, Int, Boolean) -> Unit,
    val onImportMtf: (Uri?) -> Unit,
    val onImportZip: (Uri?) -> Unit,
    val onImportMm: (Uri?) -> Unit,
    val onHeatChanged: (Int) -> Unit,
    val onSaveDetails: (String) -> Unit,
    val onLoadImport: () -> Unit,
    val onCheckLegacy: () -> Unit,
    val onUpdateFolderFilter: (String) -> Unit,
    val onUpdateMechFilter: (String) -> Unit,
    val onImport: (String) -> Unit,
    val onUpdateMechName: (String) -> Unit,
    val onUpdatePilotName: (String) -> Unit,
    val onUpdateColor: (ULong) -> Unit,
    val onBackButton: () -> Unit,
    val onToggleHelp: () -> Unit,
)