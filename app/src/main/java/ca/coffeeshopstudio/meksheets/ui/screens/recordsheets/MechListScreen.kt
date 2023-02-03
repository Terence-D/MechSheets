package ca.coffeeshopstudio.meksheets.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import ca.coffeeshopstudio.meksheets.R
import ca.coffeeshopstudio.meksheets.data.Mech
import ca.coffeeshopstudio.meksheets.data.MechCallbacks
import ca.coffeeshopstudio.meksheets.data.Pilot
import ca.coffeeshopstudio.meksheets.ui.state.MechUiState
import ca.coffeeshopstudio.meksheets.ui.utils.ImageLibrary

@Composable
fun MechListScreen(
    uiState: MechUiState,
    onMechSelected: (Mech) -> Unit,
    modifier: Modifier = Modifier
) {
    val mechs: List<Mech> = uiState.currentMechs
    LazyColumn(modifier = modifier.padding(horizontal = 16.dp)) {
        displayList(
            mechs = mechs,
            onMechSelected = onMechSelected,
            selectedMech = uiState.currentMech)
    }
}

/**
 * Component that displays two panes a list and details
 */
@Composable
fun MechListAndDetailScreen(
    uiState: MechUiState,
    callbacks: MechCallbacks,
    modifier: Modifier = Modifier
) {
    val mechs = uiState.currentMechs
    Row(modifier = modifier) {
        LazyColumn(
            modifier = Modifier
                .weight(.5f)
                .padding(16.dp)
        ) {
            displayList(
                mechs = mechs,
                onMechSelected = callbacks.onMechSelected,
                selectedMech = uiState.currentMech)
        }
        //val activity = LocalContext.current as Activity
        MechDetailsScreen(
            uiState = uiState,
            navigationItemContentList = getRecordSheetNavContent(),
            modifier = Modifier.weight(1f),
            callbacks = callbacks
        )
    }
}

private fun LazyListScope.displayList(
    mechs: List<Mech>,
    selectedMech: Mech,
    onMechSelected: (Mech) -> Unit
) {
    if (mechs.isNotEmpty()) {
        items(mechs) { mech ->
            MechListItem(
                mech = mech,
                selected = mech == selectedMech,
                onCardClick = {
                    onMechSelected(mech)
                }
            )
        }
    } else {
        item {
            Text(stringResource(R.string.empty_list))
        }
    }
}

/**
 * Component that displays a single mech list item
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun MechListItem(
    mech: Mech,
    selected: Boolean,
    onCardClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    var cardColor = CardDefaults.cardColors()
    if (selected) {
        cardColor = CardDefaults.cardColors(
            containerColor =  MaterialTheme.colorScheme.primary,
        )
    }
    Card(
        modifier = modifier.padding(vertical = 4.dp),
        onClick = onCardClick,
        colors = cardColor,
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp)
        ) {
            MechItemHeader(mech.pilot)
            Text(
                text = mech.name,
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.padding(top = 12.dp, bottom = 8.dp),
            )
            Text(
                text = mech.description,
                style = MaterialTheme.typography.bodyMedium,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}

@Composable
private fun MechItemHeader(pilot: Pilot, modifier: Modifier = Modifier) {
    val imageLibrary = ImageLibrary()

    Row(modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = ImageVector.vectorResource(imageLibrary.IconPilot),
            contentDescription = stringResource(R.string.tab_rs_pilot),
            modifier = Modifier.size(32.dp),
            tint = Color(pilot.color)
        )
        Column(
            modifier = Modifier
                .weight(1f)
                .padding(horizontal = 12.dp, vertical = 4.dp),
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = pilot.name,
                style = MaterialTheme.typography.labelLarge
            )
            Text(
                text = String.format("${stringResource(R.string.piloting)} ${pilot.piloting} / ${stringResource(R.string.gunnery)}  ${pilot.gunnery}"),
                style = MaterialTheme.typography.labelMedium,
                //color = MaterialTheme.colorScheme.outline
            )
        }
    }
}