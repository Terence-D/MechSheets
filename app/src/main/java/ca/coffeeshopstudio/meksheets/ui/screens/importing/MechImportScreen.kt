package ca.coffeeshopstudio.meksheets.ui.screens.importing

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import ca.coffeeshopstudio.meksheets.data.MechCallbacks
import ca.coffeeshopstudio.meksheets.ui.state.ContentType
import ca.coffeeshopstudio.meksheets.ui.state.MechUiState

@Composable
fun MechImportScreen(
    callbacks: MechCallbacks,
    uiState: MechUiState,
    contentType: ContentType,
    modifier: Modifier = Modifier,
) {
    if (contentType == ContentType.LIST_AND_DETAIL) {
        Row(Modifier.padding(16.dp)) {
            Column(
                Modifier.weight(1f)
                    .padding(end = 16.dp, top = 20.dp)
            ) {
                ImportListScreen(
                    callbacks = callbacks,
                    uiState = uiState,
                )
            }
            Column(
                Modifier.weight(1.5f).padding(end = 16.dp, top = 20.dp)
            )
            {
                ImportMainScreen(
                    callbacks = callbacks,
                    uiState = uiState,
                )
            }
        }
    } else {
        Column(Modifier.padding(16.dp)){
            ImportMainScreen(callbacks = callbacks,
                uiState = uiState,
                modifier = modifier)
            ImportListScreen(callbacks = callbacks,
                uiState = uiState,
                modifier = modifier)
        }
    }
}
