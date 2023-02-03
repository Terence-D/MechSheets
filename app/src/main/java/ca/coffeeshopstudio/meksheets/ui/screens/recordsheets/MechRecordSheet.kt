package ca.coffeeshopstudio.meksheets.ui.screens

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import ca.coffeeshopstudio.meksheets.data.MechCallbacks
import ca.coffeeshopstudio.meksheets.ui.state.ContentType
import ca.coffeeshopstudio.meksheets.ui.state.MechUiState

@Composable
fun MechRecordSheet(
    contentType: ContentType,
    uiState: MechUiState,
    callbacks: MechCallbacks,
    modifier: Modifier = Modifier
) {
    if (contentType == ContentType.LIST_AND_DETAIL) {
        MechListAndDetailScreen(
            uiState = uiState,
            callbacks = callbacks,
            modifier = modifier
        )
    } else {
        if (uiState.isShowingHomepage) {
            MechListScreen(
                uiState = uiState,
                onMechSelected = callbacks.onMechSelected,
                modifier = modifier
            )
        } else {
            MechDetailsScreen(
                uiState = uiState,
                navigationItemContentList = getRecordSheetNavContent(),
                callbacks = callbacks,
                modifier = modifier
            )
        }
    }
}
