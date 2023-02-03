package ca.coffeeshopstudio.meksheets.ui.state

import ca.coffeeshopstudio.meksheets.data.Mech

data class MechUiState (
    //where we currently are
    val currentDestination: NavigationTabDestination = NavigationTabDestination.MechList,
    /** Mech map for all type of [NavigationType] **/
    val currentMechs: ArrayList<Mech> = ArrayList(),
    /** Current mech being displayed **/
    var currentMech: Mech = Mech(),
    /** Whether currently displaying homepage **/
    val isShowingHomepage: Boolean = true,
    val recordSheetTab: RecordSheetTab = RecordSheetTab.Overview,
    val darkMode: Int = 0,
    val dynamicTheme: Boolean = false,
    val availableToImport: ArrayList<String> = ArrayList(),
    val importFolders: Set<String> = emptySet(),
    val importFolderFilter: String = "",
    val importMechFilter: String = "",
    val showHelp: Boolean = false
)