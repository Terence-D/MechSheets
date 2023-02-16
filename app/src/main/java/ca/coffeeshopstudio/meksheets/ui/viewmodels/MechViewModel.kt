package ca.coffeeshopstudio.meksheets.ui.viewmodels

import android.app.Application
import android.content.Context
import android.net.Uri
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import ca.coffeeshopstudio.meksheets.R
import ca.coffeeshopstudio.meksheets.data.*
import ca.coffeeshopstudio.meksheets.data.legacy.LegacyLoader
import ca.coffeeshopstudio.meksheets.ui.state.MechUiState
import ca.coffeeshopstudio.meksheets.ui.state.NavigationTabDestination
import ca.coffeeshopstudio.meksheets.ui.state.RecordSheetTab
import ca.coffeeshopstudio.meksheets.utils.FileOperations
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import java.io.*

class MechViewModel(application: Application) : AndroidViewModel(application) {
    //ui state, private and secured away
    private val _uiState = MutableStateFlow(MechUiState())
    //backing property to avoid state updates from other classes than this one
    val uiState: StateFlow<MechUiState> = _uiState.asStateFlow()

    init {
        initializeUiState()
    }

    /**
     * Updates the currently active screen - mech list, import, etc
     */
    fun updateCurrentTab(navigationTabDestination: NavigationTabDestination) {
        //ignore help, no dedicated page for it
        if (navigationTabDestination != NavigationTabDestination.Help) {
            _uiState.update {
                it.copy(currentDestination = navigationTabDestination,
                    isShowingHomepage = true)
            }
        } else {
            toggleHelp()
        }
    }

    fun updateSelectedMechStates(mech: Mech) {
        _uiState.update {
            it.copy(currentMech = mech, isShowingHomepage = false)
        }
    }

    fun ammoStateChanged(context: Context, index: Int, changeBy: Int) {
        val mech = _uiState.value.currentMech
        mech.ammo[index].shotsFired += changeBy
        if (mech.ammo[index].shotsFired < 0)
            mech.ammo[index].shotsFired = 0
        stateChanged(context, mech)
    }

    fun equipmentStateChanged(context: Context, index: Int, fired: Boolean) {
        val mech = _uiState.value.currentMech
        if (index == -1) {
            //fire everything
            mech.equipment.forEach {
                it.fired = fired
            }
        } else
            mech.equipment[index].fired = fired
        stateChanged(context, mech)
    }

    fun changeGunnery(context: Context, adjustBy: Int) {
        val mech = _uiState.value.currentMech
        mech.pilot.gunnery = changePilotStatus(mech.pilot.gunnery, adjustBy)
        stateChanged(context, mech)
    }

    fun changePilotHits(context: Context, adjustBy: Int) {
        val mech = _uiState.value.currentMech
        mech.pilot.hits = changePilotStatus(mech.pilot.hits, adjustBy)
        stateChanged(context, mech)
    }

    fun changePiloting(context: Context, adjustBy: Int) {
        val mech = _uiState.value.currentMech
        mech.pilot.piloting = changePilotStatus(mech.pilot.piloting, adjustBy)
        stateChanged(context, mech)
    }

    private fun changePilotStatus(stat: Int, adjustBy: Int) : Int {
        var rv = stat
        rv += adjustBy
        if (rv < 0)
            rv = 0
        if (rv > 6)
            rv = 6

        return rv
    }

    fun changeHeatLevel(context: Context, adjustBy: Int) {
        val mech = _uiState.value.currentMech
        mech.heatLevel += adjustBy
        stateChanged(context, mech)
    }

    fun changeArmorLevel(context: Context, location: Location, adjustBy: Int, internalStructure: Boolean = false) {
        val mech = _uiState.value.currentMech
        mech.adjustArmorCurrent(location, adjustBy, internalStructure)
        stateChanged(context, mech)
    }

    fun saveDetails(context: Context, details: String) {
        val mech = _uiState.value.currentMech
        mech.description = details
        stateChanged(context, mech)
    }

    private fun updateTheme() {
        val darkMode = darkMode(getApplication())
        val dynamicTheme = dynamicTheme(getApplication())
        _uiState.update {
            it.copy(darkMode =  darkMode,
            dynamicTheme = dynamicTheme)
        }
    }

    private fun initializeUiState() {
        val mechs: ArrayList<Mech> = MechDataProvider().loadMechData(getApplication())
        var destination: NavigationTabDestination = NavigationTabDestination.MechList
        val darkMode = darkMode(getApplication())
        val dynamicTheme = dynamicTheme(getApplication())
        var mech = Mech()
        if (mechs.size < 1)
            destination = NavigationTabDestination.Add
        else
            mech = mechs[0]
        _uiState.update {
            it.copy(
                darkMode = darkMode,
                dynamicTheme = dynamicTheme,
                currentDestination = destination,
                currentMechs = mechs,
                currentMech = mech
            )
        }
    }

    private fun getImportFolders(list: ArrayList<String>): MutableSet<String> {
        val rv: MutableSet<String> = mutableSetOf()
        list.forEach {
            rv.add(it.substringBefore("/"))
        }
        return rv
    }

    fun getListOfMechs(context: Context) {
        val rv: ArrayList<String> = FileOperations().readArrayList(context)
        _uiState.update {
            it.copy(availableToImport = rv,
                importFolders = getImportFolders(rv)
            )
        }
    }

    fun importMmFile(uri: Uri, context: Context) {
        val fo = FileOperations()
        val found = fo.findMechsInsideZip(context, uri)
        if (!found) {
            //bit of a blending of views and viewmodels, but should work for now
            Toast.makeText(context, context.getString(R.string.import_failed), Toast.LENGTH_LONG).show()
        } else {
            val rv: ArrayList<String> = fo.getFilesInZip(context, ".mtf")
            fo.writeArrayToInternal(context, rv)
            _uiState.update {
                it.copy(availableToImport = rv)
            }
            //bit of a blending of views and viewmodels, but should work for now
            Toast.makeText(context, context.getString(R.string.import_complete), Toast.LENGTH_LONG).show()
        }
    }

    fun importZipFile(uri: Uri, context: Context) {
        val fo = FileOperations()
        fo.writeUriToInternal(context, uri)
        val rv: ArrayList<String> = fo.getFilesInZip(context, ".mtf")
        fo.writeArrayToInternal(context, rv)
        _uiState.update {
            it.copy(availableToImport = rv,
                importFolders = getImportFolders(rv)
            )
        }
        //bit of a blending of views and viewmodels, but should work for now
        Toast.makeText(context, context.getString(R.string.import_complete), Toast.LENGTH_LONG).show()
    }

    fun importMtfFile(uri: Uri, context: Context) {
        val inputStream: InputStream = context.contentResolver.openInputStream(uri)!!
        importMtfFromStream(context, inputStream)
        //bit of a blending of views and viewmodels, but should work for now
        Toast.makeText(context, context.getString(R.string.import_complete), Toast.LENGTH_LONG).show()
    }

    private fun importMtfFromStream(context: Context, inputStream: InputStream) {
        val reader = BufferedReader(InputStreamReader(inputStream))

        val mech = Mtf()
        try {
            mech.readMTF(reader)
            FileOperations().writeToJson(context, mech)
            val mechs: ArrayList<Mech> = uiState.value.currentMechs
            mechs.add(mech)
            _uiState.update {
                it.copy(currentMechs = mechs)
            }
            //bit of a blending of views and viewmodels, but should work for now
            Toast.makeText(context, context.getString(R.string.import_complete), Toast.LENGTH_LONG).show()
        } catch (ioe: IOException) {
            //bit of a blending of views and viewmodels, but should work for now
            Toast.makeText(context, ioe.localizedMessage, Toast.LENGTH_LONG).show()
        }
    }

    fun updateRecordSheetTab(recordSheetTab: RecordSheetTab) {
        when (recordSheetTab) {
            RecordSheetTab.Overview -> {
                _uiState.update {
                    it.copy(recordSheetTab = RecordSheetTab.Overview)
                }
            }
            RecordSheetTab.Pilot -> {
                _uiState.update {
                    it.copy(recordSheetTab = RecordSheetTab.Pilot)
                }
            }
            RecordSheetTab.Armor -> {
                _uiState.update {
                    it.copy(recordSheetTab = RecordSheetTab.Armor)
                }
            }
            RecordSheetTab.Components -> {
                _uiState.update {
                    it.copy(recordSheetTab = RecordSheetTab.Components)
                }
            }
        }
    }

    private fun stateChanged(context: Context, mech: Mech) {
        FileOperations().writeToJson(context, mech)
        val mechs: ArrayList<Mech> = MechDataProvider().loadMechData(getApplication())
        _uiState.update {
            it.copy(currentMechs = mechs,
                currentMech = mech,
            )
        }
    }

    fun reset(context: Context, mech: Mech) {
        mech.reset()
        stateChanged(context, mech)
    }

    fun delete(context: Context, mech: Mech) {
        FileOperations().deleteFile(context, mech.filename)
        initializeUiState()
    }

    fun updateMechComponent(context: Context, location: Location, componentIndex: Int) {
        val currentStatus =
            uiState.value.currentMech.getComponentsFromLocation(location)[componentIndex]!!.second
        uiState.value.currentMech.updateComponentStatus(location, componentIndex, !currentStatus)
        stateChanged(context, uiState.value.currentMech)
    }

    private fun darkMode(context: Context): Int {
        return PrefManager(context).darkMode()
    }

    fun setDarkMode(context: Context, mode: Int) {
        PrefManager(context).setDarkMode(mode)
        updateTheme()
    }

    private fun dynamicTheme(context: Context): Boolean {
        return PrefManager(context).dynamicMode()
    }

    fun setDynamicColors(context: Context, mode: Boolean) {
        PrefManager(context).setDynamicMode(mode)
        updateTheme()
    }

    fun checkForLegacy(context: Context) {
        val ll = LegacyLoader()
        if (ll.loadLegacy(context)) {
            val mechs: ArrayList<Mech> = MechDataProvider().loadMechData(getApplication())
            _uiState.update {
                it.copy(currentMechs = mechs)
            }
        }
    }

    fun updateFolderFilter(folderFilter: String) {
        _uiState.update {
            it.copy(importFolderFilter = folderFilter)
        }
    }

    fun updateMechFilter(filter: String) {
        _uiState.update {
            it.copy(importMechFilter = filter)
        }
    }

    fun importFromZip(context: Context, fileToExtract: String) {
        //Toast.makeText(context, fileToExtract, Toast.LENGTH_LONG).show()
        val fileOp = FileOperations()
        val path = fileOp.unzip(context, fileToExtract)
        if (path != "") {
            val inputStream: InputStream = FileInputStream(path)
            importMtfFromStream(context, inputStream)
            File(path).delete()
        }
    }

    fun updateMechName(context: Context, newName: String) {
        uiState.value.currentMech.name = newName
        stateChanged(context, uiState.value.currentMech)
    }

    fun updatePilotName(context: Context, newName: String) {
        uiState.value.currentMech.pilot.name = newName
        stateChanged(context, uiState.value.currentMech)
    }

    fun updateTeamColor(context: Context, color: ULong) {
        uiState.value.currentMech.pilot.color = color
        stateChanged(context, uiState.value.currentMech)
    }

    fun onBackButton() {
        _uiState.value = _uiState.value.copy(isShowingHomepage = true,)
    }

    fun toggleHelp() {
        _uiState.value = _uiState.value.copy(showHelp = !_uiState.value.showHelp,)
    }
}