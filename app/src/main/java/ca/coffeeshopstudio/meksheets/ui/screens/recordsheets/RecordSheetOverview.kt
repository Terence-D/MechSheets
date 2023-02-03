package ca.coffeeshopstudio.meksheets.ui.screens
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import ca.coffeeshopstudio.meksheets.R
import ca.coffeeshopstudio.meksheets.data.MechCallbacks
import ca.coffeeshopstudio.meksheets.ui.composables.*
import ca.coffeeshopstudio.meksheets.ui.state.MechUiState
import ca.coffeeshopstudio.meksheets.ui.utils.ImageLibrary

@Composable
fun RecordSheetOverview(
    uiState: MechUiState,
    callbacks: MechCallbacks,
    modifier: Modifier
) {
    val showDialogReset =  remember { mutableStateOf(false) }
    val showDialogDelete =  remember { mutableStateOf(false) }
    val question: String = stringResource(id = R.string.confirm_reset, uiState.currentMech.name)
    val deleteQuestion: String = stringResource(id = R.string.confirm_delete, uiState.currentMech.name)
    YesNo(question, showDialogReset, callbacks.onReset, modifier)
    YesNo(deleteQuestion, showDialogDelete, callbacks.onDelete, modifier)

    val imageLibrary = ImageLibrary()

    OverviewHelpPopup(callbacks.onToggleHelp, uiState, modifier)

    Row (horizontalArrangement = Arrangement.SpaceEvenly,
        modifier = Modifier.fillMaxWidth()) {
        Button(onClick = {
            showDialogReset.value = true
        }) {
            Icon(
                painter = painterResource(id = imageLibrary.IconReset),
                contentDescription = stringResource(R.string.reset),
                modifier = Modifier.size(ButtonDefaults.IconSize)
            )
            Text(stringResource(id = R.string.reset))
        }
        Button(onClick = {
            showDialogDelete.value = true
        }) {
            Icon(
                painter = painterResource(id = imageLibrary.IconDelete),
                contentDescription = stringResource(R.string.delete),
                modifier = Modifier.size(ButtonDefaults.IconSize)
            )
            Text(stringResource(id = R.string.delete))
        }
    }
    RSOverview(uiState = uiState, modifier = modifier)
    RSEquipment(uiState = uiState,
        equipmentStateChanged = callbacks.onEquipmentStateChanged,
        modifier = modifier)
    if (uiState.currentMech.ammo.size > 0)
        RSAmmo(uiState = uiState,
            ammoStateChanged = callbacks.onAmmoStateChanged,
            modifier = modifier)
    RSHeatSinks(uiState = uiState,
        heatChanged = callbacks.onHeatChanged,
        modifier = modifier)
    RSDescription(uiState = uiState,
        saveDetails = callbacks.onSaveDetails,
        modifier = modifier)
}

@Composable
private fun YesNo(
    text: String,
    showDialog: MutableState<Boolean>,
    onAffirmative: () -> Unit,
    modifier: Modifier = Modifier
) {
    if(showDialog.value) {
        YesNoDialog(
            text = text,
            setShowDialog = {
                showDialog.value = it
            },
            onClickAffirmative = {
                onAffirmative()
            },
        )
    }
}

@Composable
fun OverviewHelpPopup(onToggleHelp: () -> Unit, uiState: MechUiState, modifier: Modifier) {
    if (uiState.showHelp) {
        HelpDialog(
            setShowDialog = {
                onToggleHelp()
            },
            content = listOf {
                MechHeaderB(stringResource(R.string.help_recordsheet_title))
                MechHeaderB(stringResource(R.string.help_overview_title))
                Text(text = stringResource(R.string.help_overview))
                MechHeaderB(stringResource(R.string.help_equipment_title))
                Text(text = stringResource(R.string.help_equipment))
                MechHeaderB(stringResource(R.string.help_ammo_title))
                Text(text = stringResource(R.string.help_ammo))
                MechHeaderB(stringResource(R.string.help_heat_title))
                Text(text = stringResource(R.string.help_heat))
                MechHeaderB(stringResource(R.string.help_details_title))
                Text(text = stringResource(R.string.help_details))
            }
        )
    }
}

