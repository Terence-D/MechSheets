package ca.coffeeshopstudio.meksheets.ui.screens.importing

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import ca.coffeeshopstudio.meksheets.R
import ca.coffeeshopstudio.meksheets.data.MechCallbacks
import ca.coffeeshopstudio.meksheets.ui.state.MechUiState

@Composable
fun ImportListScreen(
    callbacks: MechCallbacks,
    uiState: MechUiState,
    modifier: Modifier = Modifier,
) {
    callbacks.onLoadImport()
    if (uiState.availableToImport.size == 0) {
        Card(modifier = modifier.padding(vertical = 8.dp)) {
            Text(stringResource(R.string.missing_imports),
            modifier = modifier.padding(8.dp))
        }
    } else {
        LazyColumn {
            items(uiState.availableToImport.size) { index ->
                val mech = uiState.availableToImport[index]//.substringBefore(".mtf")
                if (uiState.importFolderFilter == "" || mech.substringBefore("/") == uiState.importFolderFilter) {
                    if (mech.lowercase().contains(uiState.importMechFilter.lowercase())) {
                        Card(
                            modifier = Modifier.fillMaxWidth()
                                .padding(vertical = 4.dp),
                        ) {
                            Text(mech,
                                modifier = Modifier
                                    .padding(12.dp)
                                    .clickable {
                                        callbacks.onImport(mech)
                                    }
                            )
                        }
                    }
                }
            }
        }
    }
}