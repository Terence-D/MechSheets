package ca.coffeeshopstudio.meksheets.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import ca.coffeeshopstudio.meksheets.R
import ca.coffeeshopstudio.meksheets.data.Location
import ca.coffeeshopstudio.meksheets.data.Mech
import ca.coffeeshopstudio.meksheets.data.MechCallbacks
import ca.coffeeshopstudio.meksheets.ui.composables.HelpDialog
import ca.coffeeshopstudio.meksheets.ui.composables.MechHeaderB
import ca.coffeeshopstudio.meksheets.ui.composables.RSComponent
import ca.coffeeshopstudio.meksheets.ui.state.MechUiState

@Composable
fun RecordSheetComponents(
    uiState: MechUiState,
    callbacks: MechCallbacks,
    modifier: Modifier,
) {
    ComponentHelpPopup(callbacks.onToggleHelp, uiState, modifier)
    Row (horizontalArrangement = Arrangement.Center, modifier = Modifier.fillMaxWidth()) {
        //left arm/torso/leg
        Column(modifier = Modifier
            .weight(1f)
            .padding(8.dp)) {
            ShowComponent(
                onUpdateComponent = callbacks.onUpdateMechComponent,
                mech = uiState.currentMech,
                location = Location.LeftArm,
            )
            ShowComponent(
                onUpdateComponent = callbacks.onUpdateMechComponent,
                mech = uiState.currentMech,
                location = Location.LeftTorso,
            )
            ShowComponent(
                onUpdateComponent = callbacks.onUpdateMechComponent,
                mech = uiState.currentMech,
                location = Location.LeftLeg,
            )
        }
        Column(modifier = Modifier
            .weight(1f)
            .padding(8.dp)) {
            ShowComponent(
                onUpdateComponent = callbacks.onUpdateMechComponent,
                mech = uiState.currentMech,
                location = Location.Head,
            )
            ShowComponent(
                onUpdateComponent = callbacks.onUpdateMechComponent,
                mech = uiState.currentMech,
                location = Location.CenterTorso,
            )
        }
        Column(modifier = Modifier
            .weight(1f)
            .padding(8.dp)) {
            ShowComponent(
                onUpdateComponent = callbacks.onUpdateMechComponent,
                mech = uiState.currentMech,
                location = Location.RightArm,
            )
            ShowComponent(
                onUpdateComponent = callbacks.onUpdateMechComponent,
                mech = uiState.currentMech,
                location = Location.RightTorso,
            )
            ShowComponent(
                onUpdateComponent = callbacks.onUpdateMechComponent,
                mech = uiState.currentMech,
                location = Location.RightLeg,
            )
        }
    }
}

@Composable
fun ShowComponent(onUpdateComponent: (Location, Int) -> Unit, mech: Mech, location: Location) {
    RSComponent(
        onUpdateComponent = onUpdateComponent,
        components = mech.getComponentsFromLocation(location),
        location = location,
        title = stringArrayResource(id = R.array.location_names)[location.value]
    )
}

@Composable
fun ComponentHelpPopup(onToggleHelp: () -> Unit, uiState: MechUiState, modifier: Modifier) {
    if (uiState.showHelp) {
        HelpDialog(
            setShowDialog = {
                onToggleHelp()
            },
            content = listOf {
                MechHeaderB(stringResource(R.string.help_recordsheet_title))
                MechHeaderB(stringResource(R.string.help_components_title))
                Text(stringResource(R.string.help_components))
            }
        )
    }
}
