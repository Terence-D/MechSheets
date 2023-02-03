package ca.coffeeshopstudio.meksheets.ui.composables

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import ca.coffeeshopstudio.meksheets.R
import ca.coffeeshopstudio.meksheets.data.MechCallbacks
import ca.coffeeshopstudio.meksheets.ui.state.MechUiState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RSPilot(
    uiState: MechUiState,
    callbacks: MechCallbacks,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.padding(vertical = 4.dp),
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp)
        ) {
            Row(horizontalArrangement = Arrangement.Center, modifier = Modifier.fillMaxWidth()) {
                var pilotName by remember { mutableStateOf(uiState.currentMech.pilot.name) }
                TextField(
                    value = pilotName,
                    modifier = Modifier.weight(5f),
                    textStyle = MaterialTheme.typography.titleLarge,
                    maxLines = 1,
                    onValueChange = {
                        pilotName = it
                    }
                )
                Button(modifier = Modifier.weight(1f),
                    onClick = {
                        callbacks.onUpdatePilotName(pilotName)
                    }) {
                    Icon(
                        imageVector = Icons.Default.Edit,
                        contentDescription = stringResource(R.string.change_name)
                    )
                }
            }
            var color = MaterialTheme.colorScheme.primary
            if (uiState.currentMech.pilot.hits > 5)
                color = MaterialTheme.colorScheme.error
            else if (uiState.currentMech.pilot.hits > 0)
                color = MaterialTheme.colorScheme.tertiary

            PlusMinusButton(
                onMinusClick = { callbacks.onChangePiloting(-1) },
                onPlusClick = { callbacks.onChangePiloting(1) },
                text = "${stringResource(id = R.string.piloting)}: ${uiState.currentMech.pilot.piloting}"
            )
            PlusMinusButton(
                onMinusClick = { callbacks.onChangeGunnery(-1) },
                onPlusClick = { callbacks.onChangeGunnery(1) },
                text = "${stringResource(id = R.string.gunnery)}: ${uiState.currentMech.pilot.gunnery}"
            )
            PlusMinusButton(
                onMinusClick = { callbacks.onChangePilotHits(-1) },
                onPlusClick = { callbacks.onChangePilotHits(1) },
                color = color,
                text = "${stringResource(id = R.string.hits)}: ${uiState.currentMech.pilot.hits}"
            )
        }
    }
}