package ca.coffeeshopstudio.meksheets.ui.composables

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import ca.coffeeshopstudio.meksheets.R
import ca.coffeeshopstudio.meksheets.ui.screens.SpeedTextEntry
import ca.coffeeshopstudio.meksheets.ui.state.MechUiState
import ca.coffeeshopstudio.meksheets.ui.utils.ImageLibrary

@Composable
fun RSOverview(
    uiState: MechUiState,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.padding(vertical = 4.dp),
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp)
        ) {
            Row(horizontalArrangement = Arrangement.Center, modifier = Modifier.fillMaxWidth()) {
                MechHeaderB(stringRes = R.string.overview, modifier = modifier)
            }
            Text("${stringResource(id = R.string.mech_weight)} ${uiState.currentMech.tons}")
            Row(horizontalArrangement = Arrangement.SpaceEvenly, modifier = Modifier.fillMaxWidth()) {
                SpeedTextEntry(
                    textRes = R.string.mech_walking,
                    maxSpeed = uiState.currentMech.walk,
                    currentSpeed = uiState.currentMech.currentWalk()
                )
                SpeedTextEntry(
                    textRes = R.string.mech_running,
                    maxSpeed = uiState.currentMech.run,
                    currentSpeed = uiState.currentMech.currentRun()
                )
                SpeedTextEntry(
                    textRes = R.string.mech_jumping,
                    maxSpeed = uiState.currentMech.jump,
                    currentSpeed = uiState.currentMech.currentJump()
                )
            }
        }
    }
}

@Composable
fun RSEquipment(
    uiState: MechUiState,
    equipmentStateChanged: (Int, Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.padding(vertical = 4.dp),
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp)
        ) {
            Row(horizontalArrangement = Arrangement.Center, modifier = Modifier.fillMaxWidth()) {
                MechHeaderB(stringRes = R.string.equipment, modifier = modifier)
            }

            Row(
                verticalAlignment = Alignment.CenterVertically,
            ) {
                val checked = remember { mutableStateOf(false) }
                Checkbox(
                    checked = checked.value,
                    onCheckedChange = {
                        checked.value = it
                        equipmentStateChanged(-1, checked.value)
                    }
                )
                Text(text = stringResource(id = R.string.fire_all))
            }

            for ((i, item) in uiState.currentMech.equipment.withIndex()) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Checkbox(
                        checked = item.fired,
                        onCheckedChange = {
                            equipmentStateChanged(i, !item.fired)
                        }
                    )
                    if (item.destroyed)
                        Text(text = item.name, color = MaterialTheme.colorScheme.error)
                    else
                        Text(text = item.name)
                }
            }
        }
    }
}

@Composable
fun RSAmmo(
    uiState: MechUiState,
    ammoStateChanged: (Int, Int) -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.padding(vertical = 4.dp),
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp)
        ) {
            Row(horizontalArrangement = Arrangement.Center, modifier = Modifier.fillMaxWidth()) {
                MechHeaderB(stringRes = R.string.ammo, modifier = modifier)
            }
            for ((i, item) in uiState.currentMech.ammo.withIndex()) {
                PlusMinusButton(
                    onMinusClick = {
                        ammoStateChanged(i, -1)
                    },
                    onPlusClick = {
                        ammoStateChanged(i, 1)
                    },
                    text = "${item.name}\n${stringResource(id = R.string.shots_fired)}${item.shotsFired}"
                )
            }
        }
    }
}
@Composable
fun RSHeatSinks(
    uiState: MechUiState,
    heatChanged: (Int) -> Unit,
    modifier: Modifier = Modifier
) {

    val imageLibrary = ImageLibrary()
    val display = "${stringResource(id = R.string.heat_sinks)}${uiState.currentMech.currentHeatSinks} / ${uiState.currentMech.maxHeatSinks}"
    var color = MaterialTheme.colorScheme.primary
    if (uiState.currentMech.heatLevel > 14)
        color = MaterialTheme.colorScheme.error
    else if (uiState.currentMech.heatLevel > 4)
        color = MaterialTheme.colorScheme.tertiary

    Card(
        modifier = modifier.padding(vertical = 4.dp),
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp)
        ) {
            Row(horizontalArrangement = Arrangement.Center, modifier = Modifier.fillMaxWidth()) {
                MechHeaderB(stringRes = R.string.heat, modifier = modifier)
            }
            Row(
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxWidth()
            ) {
                if (uiState.currentMech.currentHeatSinks != uiState.currentMech.maxHeatSinks)
                    Text(text = display, color = MaterialTheme.colorScheme.error)
                else
                    Text(text = display, color = MaterialTheme.colorScheme.primary)
            }

            PlusMinusButton(
                onMinusClick = {
                    heatChanged(-1)
                },
                onPlusClick = {
                    heatChanged(1)
                },
                color = color,
                decreaseIcon = imageLibrary.IconCold,
                increaseIcon = imageLibrary.IconHot,
                text = "${stringResource(id = R.string.heat_scale)} ${uiState.currentMech.heatLevel}"
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RSDescription(
    uiState: MechUiState,
    saveDetails: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.padding(vertical = 4.dp),
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp)
        ) {
            Row(horizontalArrangement = Arrangement.Center, modifier = Modifier.fillMaxWidth()) {
                MechHeaderB(stringRes = R.string.description, modifier = modifier)
            }

            var details by remember { mutableStateOf(uiState.currentMech.description) }

            TextField(
                value = details,
                modifier = Modifier.fillMaxWidth(),
                maxLines = 3,
                onValueChange = {
                    details = it
                }
            )

            Button(
                onClick = {
                    saveDetails(details)
                }
            ) {
                Text(stringResource(R.string.save_description))
            }
        }
    }
}