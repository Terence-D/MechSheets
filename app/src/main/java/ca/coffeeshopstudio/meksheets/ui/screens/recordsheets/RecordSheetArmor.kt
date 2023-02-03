package ca.coffeeshopstudio.meksheets.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import ca.coffeeshopstudio.meksheets.R
import ca.coffeeshopstudio.meksheets.ui.composables.RSArmor
import ca.coffeeshopstudio.meksheets.ui.state.MechUiState
import ca.coffeeshopstudio.meksheets.data.MechCallbacks
import ca.coffeeshopstudio.meksheets.ui.composables.HelpDialog
import ca.coffeeshopstudio.meksheets.ui.composables.MechHeaderB

@Composable
fun RecordSheetArmor(
    uiState: MechUiState,
    modifier: Modifier,
    callbacks: MechCallbacks,
) {
    ArmorHelpPopup(callbacks.onToggleHelp, uiState, modifier)
    Row (horizontalArrangement = Arrangement.Center, modifier = Modifier.fillMaxWidth()) {
        MechHeaderB(stringResource(id = R.string.armor), modifier = Modifier)
    }
    RSArmor(
        current = uiState.currentMech.armorCurrent,
        max = uiState.currentMech.armorMax,
        modifier = modifier,
        onArmorChanged = callbacks.onArmorChanged
    )
    Spacer(modifier = Modifier.height(20.dp))
    Row (horizontalArrangement = Arrangement.Center, modifier = Modifier.fillMaxWidth()) {
        MechHeaderB(stringResource(id = R.string.rear_armor), modifier = Modifier)
    }
    RSArmor(
        current = uiState.currentMech.armorRearCurrent,
        max = uiState.currentMech.armorRearMax,
        modifier = modifier,
        onArmorChanged = callbacks.onArmorChanged
    )
    Spacer(modifier = Modifier.height(20.dp))
    Row (horizontalArrangement = Arrangement.Center, modifier = Modifier.fillMaxWidth()) {
        MechHeaderB(stringResource(id = R.string.internal_structure), modifier = Modifier)
    }
    RSArmor(
        current = uiState.currentMech.internalCurrent,
        max = uiState.currentMech.internalMax,
        isInternalStructure = true,
        modifier = modifier,
        onArmorChanged = callbacks.onArmorChanged
    )
}

@Composable
fun ArmorHelpPopup(onToggleHelp: () -> Unit, uiState: MechUiState, modifier: Modifier) {
    if (uiState.showHelp) {
        HelpDialog(
            setShowDialog = {
                onToggleHelp()
            },
            content = listOf {
                MechHeaderB(stringResource(R.string.help_recordsheet_title))
                MechHeaderB(stringResource(R.string.help_armor_title))
                Text(stringResource(R.string.help_armor))
            }
        )
    }
}
