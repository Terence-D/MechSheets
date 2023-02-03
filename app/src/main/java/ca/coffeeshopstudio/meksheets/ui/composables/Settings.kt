package ca.coffeeshopstudio.meksheets.ui.composables

import androidx.annotation.DrawableRes
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ca.coffeeshopstudio.meksheets.ui.theme.MechSheetsTheme

@Composable
fun SettingsTitle(
    text: String,
    modifier: Modifier = Modifier,
) {
    Text(text, modifier = modifier,
        style = MaterialTheme.typography.displayMedium)
}

@Composable
fun SettingsSubTitle(
    text: String,
    modifier: Modifier = Modifier,
) {
    Text(text, modifier = modifier,
        style = MaterialTheme.typography.displaySmall)
}

@Composable
fun SettingsRadioButton(
    modifier: Modifier = Modifier,
    text: String,
    checked: Boolean,
    setMode: (Boolean) -> Unit,
    @DrawableRes icon: Int? = null,
) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier.fillMaxWidth().clickable {
            setMode(true)
        },
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Row{
            if (icon != null) {
                Icon(
                    painter = painterResource(id = icon),
                    contentDescription = text,
                    modifier = Modifier.size(ButtonDefaults.IconSize)
                )
            }
            Text(text, modifier = Modifier.padding(8.dp, 0.dp))
        }
        RadioButton(selected = checked, onClick = {
            setMode(checked)
        })
    }
}

@Composable
fun SettingsOnOff(
    modifier: Modifier = Modifier,
    text: String,
    checked: Boolean,
    setMode: (Boolean) -> Unit,
    @DrawableRes icon: Int? = null,
) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier.fillMaxWidth().padding(8.dp).clickable {
            setMode(true)
        },
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Row(modifier = Modifier.weight(5f)){
            if (icon != null) {
                Icon(
                    painter = painterResource(id = icon),
                    contentDescription = text,
//                    modifier = Modifier.size(ButtonDefaults.)
                )
            }
            Text(text, modifier = Modifier.padding(8.dp))
        }
        Switch(checked = checked, modifier = Modifier.weight(1f), onCheckedChange = {
            setMode(checked)
        })
    }
}

@Preview
@Composable
fun SettingsTitlePreview() {
    MechSheetsTheme {
        SettingsTitle("Title Preview")
    }
}
@Preview
@Composable
fun SettingsSubTitlePreview() {
    MechSheetsTheme {
        SettingsSubTitle("Sub Title Preview")
    }
}

@Preview
@Composable
fun SettingsRadioButtonPreview() {
    MechSheetsTheme {
        SettingsRadioButton(text = "Radio Button Preview", checked = true,
            setMode = { },
            modifier = Modifier
        )
    }
}
@Preview
@Composable
fun SettingsOnOffPreview() {
    MechSheetsTheme {
        SettingsOnOff(text = "Yes No Preview", checked = true,
            setMode = { },
            modifier = Modifier
        )
    }
}
