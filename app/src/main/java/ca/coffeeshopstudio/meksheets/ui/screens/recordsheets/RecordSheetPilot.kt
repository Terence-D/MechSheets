package ca.coffeeshopstudio.meksheets.ui.screens
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import ca.coffeeshopstudio.meksheets.R
import ca.coffeeshopstudio.meksheets.data.MechCallbacks
import ca.coffeeshopstudio.meksheets.ui.composables.HelpDialog
import ca.coffeeshopstudio.meksheets.ui.composables.MechHeaderB
import ca.coffeeshopstudio.meksheets.ui.composables.RSColorPicker
import ca.coffeeshopstudio.meksheets.ui.composables.RSPilot
import ca.coffeeshopstudio.meksheets.ui.state.MechUiState

@Composable
fun RecordSheetPilot(
    uiState: MechUiState,
    callbacks: MechCallbacks,
    modifier: Modifier
) {
    PilotHelpPopup(callbacks.onToggleHelp, uiState, modifier)

    RSPilot(uiState = uiState,
        callbacks = callbacks,
        modifier = modifier)
    RSColorPicker(uiState = uiState,
        onUpdateColor = callbacks.onUpdateColor,
        modifier = modifier)
}

@Composable
fun PilotHelpPopup(onToggleHelp: () -> Unit, uiState: MechUiState, modifier: Modifier) {
    if (uiState.showHelp) {
        HelpDialog(
            setShowDialog = {
                onToggleHelp()
            },
            content = listOf {
                MechHeaderB(stringResource(R.string.help_recordsheet_title))
                MechHeaderB(stringResource(R.string.help_pilot_title))
                Text(text = stringResource(R.string.help_pilot))
                MechHeaderB(stringResource(R.string.help_color_title))
                Text(text = stringResource(R.string.help_color))
            }
        )
    }
}
