package ca.coffeeshopstudio.meksheets.ui.composables

import android.os.Build
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import ca.coffeeshopstudio.meksheets.R
import ca.coffeeshopstudio.meksheets.ui.state.MechUiState
import ca.coffeeshopstudio.meksheets.ui.utils.ImageLibrary

@Composable
fun SettingsCard(
    modifier: Modifier = Modifier,
    uiState: MechUiState,
    onUpdateDarkMode: (Int) -> Unit,
    onUpdateDynamicColors: (Boolean) -> Unit,
) {
    val imageLibrary = ImageLibrary()
    Card(
        modifier = modifier.padding(8.dp),
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp)
        ) {
            SettingsTitle(stringResource(id = R.string.settings))
            SettingsSubTitle(stringResource(id = R.string.dark_mode))
            SettingsRadioButton(
                modifier = modifier,
                stringResource(id = R.string.follow_system),
                uiState.darkMode == 0,
                setMode = {
                    onUpdateDarkMode(0)
                },
                imageLibrary.IconDarkModeAuto
            )
            SettingsRadioButton(
                modifier = modifier,
                text = stringResource(id = R.string.dark_mode),
                checked = uiState.darkMode == 1,
                setMode = {
                    onUpdateDarkMode(1)
                },
                icon = imageLibrary.IconDarkMode
            )
            SettingsRadioButton(
                modifier = modifier,
                text = stringResource(id = R.string.always_light),
                checked = uiState.darkMode == -1,
                setMode = {
                    onUpdateDarkMode(-1)
                },
                icon = imageLibrary.IconLightMode
            )
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                SettingsSubTitle(stringResource(id = R.string.dynamic_colors))
                SettingsOnOff(
                    modifier = modifier,
                    stringResource(id = R.string.use_dynamic),
                    uiState.dynamicTheme,
                    setMode = {
                        onUpdateDynamicColors(!it)
                    },
                    imageLibrary.IconDynamicTheme
                )
            }
        }
    }
}