package ca.coffeeshopstudio.meksheets.ui.screens.importing

import android.net.Uri
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import ca.coffeeshopstudio.meksheets.R
import ca.coffeeshopstudio.meksheets.data.MechCallbacks
import ca.coffeeshopstudio.meksheets.ui.composables.HelpDialog
import ca.coffeeshopstudio.meksheets.ui.composables.MechHeaderA
import ca.coffeeshopstudio.meksheets.ui.composables.MechHeaderB
import ca.coffeeshopstudio.meksheets.ui.state.MechUiState
import ca.coffeeshopstudio.meksheets.ui.utils.ImageLibrary

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ImportMainScreen(
    uiState: MechUiState,
    modifier: Modifier = Modifier,
    callbacks: MechCallbacks) {
    val filePicker = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent(),
        onResult = callbacks.onImportMtf
    )
    val filePickerZip = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent(),
        onResult = callbacks.onImportZip
    )
    val filePickerMm = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent(),
        onResult = callbacks.onImportMm
    )

    val importItems: List<ImportItemCard> = importListItems()

    ImportHelpPopup(callbacks.onToggleHelp, uiState, modifier)

    Column {
        Row (horizontalArrangement = Arrangement.Center, modifier = Modifier.fillMaxWidth()) {
            MechHeaderA(stringResource(R.string.import_mechs), modifier = Modifier)
        }

        for (item in importItems) {
            ImportListItem(
                contents = item,
                onCardClick = {
                    onImportTypeSelected(
                        item.titleRes,
                        filePicker = filePicker,
                        filePickerZip = filePickerZip,
                        filePickerMm = filePickerMm
                    )
                }
            )
        }
        if (uiState.availableToImport.size > 0) {
            FilterComponent(uiState, callbacks)
        }
    }
}

@Composable
fun ImportHelpPopup(
    onToggleHelp: () -> Unit,
    uiState: MechUiState,
    modifier: Modifier
) {
    val uriHandler = LocalUriHandler.current
    val url = stringResource(R.string.url_megamek)
    if (uiState.showHelp) {
        HelpDialog(
            setShowDialog = {
                onToggleHelp()
            },
            content = listOf {
                MechHeaderB(stringResource(R.string.help_import_title))
                Text(text = stringResource(R.string.help_import))
                Text(text = stringResource(R.string.help_import_download_warning),
                    fontWeight = FontWeight.ExtraBold)
                Text(text = stringResource(R.string.help_import_2))
                Button(
                    onClick = { uriHandler.openUri(url) }
                ) {
                    Text(stringResource(id = R.string.mega_mek), modifier = Modifier)
                }
            }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun FilterComponent(
    uiState: MechUiState,
    callbacks: MechCallbacks,
) {
    var mechFilter by remember { mutableStateOf(uiState.importMechFilter) }
    Row(
        modifier = Modifier.padding(8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        FolderFilters(
            uiState,
            modifier = Modifier.weight(3f),
            callbacks.onUpdateFolderFilter,
        )
        TextField(
            value = mechFilter,
            modifier = Modifier.weight(3f),
            placeholder = { Text(stringResource(R.string.change_name)) },
            maxLines = 1,
            onValueChange = {
                mechFilter = it
            },
        )
        Button(modifier = Modifier.weight(2f),
            onClick = {
                callbacks.onUpdateMechFilter(mechFilter)
            }) {
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = stringResource(R.string.change_name)
            )
        }
    }
}

@Composable
fun FolderFilters(uiState: MechUiState,
                  modifier: Modifier = Modifier,
                  onUpdateFolderFilter: (String) -> Unit) {
    var expanded by remember { mutableStateOf(false) }
    Box(
        contentAlignment = Alignment.Center
    ) {
        Button(onClick = {
            expanded = true
        }) {
            Text(if (uiState.importFolderFilter == "")
                stringResource(R.string.folder_filter)
            else
                uiState.importFolderFilter)
            Icon(
                imageVector = Icons.Default.ArrowDropDown,
                contentDescription = stringResource(R.string.folder_filter)
            )
        }

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = {
                expanded = false
            }
        ) {
            uiState.importFolders.forEach { itemValue ->
                DropdownMenuItem(
                    onClick = {
                        onUpdateFolderFilter(itemValue)
                        expanded = false
                    },
                    text = {
                        Text(text = itemValue)
                    }
                )
            }
        }
    }
}

/**
 * Component that displays a single list item
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ImportListItem(
    contents: ImportItemCard,
    onCardClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.padding(vertical = 4.dp),
        onClick = onCardClick
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            Row() {
                if (contents.iconRes != null)
                    Icon(
                        painter = painterResource(id = contents.iconRes),
                        contentDescription = stringResource(R.string.decrease),
                        modifier = Modifier.size(ButtonDefaults.IconSize)
                    )
                if (contents.titleRes != null)
                    ImportItemHeader( stringResource(contents.titleRes))
            }
            if (contents.detailsRes != null)
                Text(
                    text = stringResource(contents.detailsRes),
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier.padding(top = 12.dp, bottom = 8.dp),
                    maxLines = 4
                )
        }
    }
}

@Composable
private fun ImportItemHeader(title: String, modifier: Modifier = Modifier) {
    Row(modifier = modifier.fillMaxWidth()) {
        Column(
            modifier = Modifier
                .weight(1f)
                .padding(horizontal = 12.dp, vertical = 4.dp),
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.labelLarge
            )
        }
    }
}


private data class ImportItemCard (
    val titleRes: Int?,
    val detailsRes: Int?,
    val iconRes: Int?
)
private fun importListItems(): List<ImportItemCard> {
    return listOf(
        ImportItemCard(
            null,
            R.string.help_starting,
            null
        ),
        ImportItemCard(
            R.string.import_single_mtf,
            R.string.import_single_mtf_details,
            ImageLibrary().IconImportOne
        ),
        ImportItemCard(
            R.string.import_multiple,
            R.string.import_multiple_details,
            ImageLibrary().IconImportMany
        ),
        ImportItemCard(
            R.string.import_from_mm,
            R.string.import_mm_details,
            ImageLibrary().IconImportMany
        ),
    )
}

private fun onImportTypeSelected(
    titleRes: Int?,
    filePicker: ManagedActivityResultLauncher<String, Uri?>,
    filePickerZip: ManagedActivityResultLauncher<String, Uri?>,
    filePickerMm: ManagedActivityResultLauncher<String, Uri?>,

    ) {
    if (titleRes != null)
        when (titleRes) {
            R.string.import_single_mtf -> {
                filePicker.launch("*/*")
            }
            R.string.import_multiple -> {
                filePickerZip.launch("*/*")
            }
            R.string.import_from_mm -> {
                filePickerMm.launch("*/*")
            }
        }
}
