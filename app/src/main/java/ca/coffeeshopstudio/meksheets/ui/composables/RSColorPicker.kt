package ca.coffeeshopstudio.meksheets.ui.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import ca.coffeeshopstudio.meksheets.R
import ca.coffeeshopstudio.meksheets.ui.state.MechUiState
import ca.coffeeshopstudio.meksheets.ui.utils.ImageLibrary

@Composable
fun RSColorPicker(
    uiState: MechUiState,
    onUpdateColor: (ULong) -> Unit,
    modifier: Modifier = Modifier
) {
    val colors: List<Color> = listOf(
        Color.Red,
        Color.Blue,
        Color.Green,
        Color.Yellow,
        Color.Gray,
        Color.White,
        Color.Black,
        Color.Cyan,
        Color.Magenta
    )
    Card(
        modifier = modifier.padding(vertical = 4.dp),
    ) {
        Row(modifier = Modifier
            .fillMaxWidth()
            .padding(20.dp)
        ) {
            Row(horizontalArrangement = Arrangement.Center, modifier = Modifier.fillMaxWidth()) {
                MechHeaderB(stringRes = R.string.team_color, modifier = modifier)
            }
        }
        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            Column (modifier = Modifier.padding(12.dp)) {
                Spacer(modifier = Modifier.height(48.dp))
                Icon(
                    modifier = Modifier.width(96.dp).height(96.dp),
                    painter = painterResource(ImageLibrary().IconPilot),
                    contentDescription = stringResource(R.string.piloting),
                    tint = Color(uiState.currentMech.pilot.color)
                )
            }
            Column {
                var i = 0
                while (i < colors.size) {
                    i = colorRow(
                        onUpdateColor = onUpdateColor,
                        colors = colors,
                        i = i
                    )
                }
            }
         }
    }
}

@Composable
fun colorRow(onUpdateColor: (ULong) -> Unit, colors: List<Color>, i: Int): Int {
    Row {
        Box(modifier = Modifier.width(64.dp).height(64.dp)
            .clip(RoundedCornerShape(10.dp)).background(colors[i]).clickable {
                onUpdateColor(colors[i].value)
            }
        )
        if (i + 1 < colors.size)
            Box(modifier = Modifier.width(64.dp).height(64.dp)
                .clip(RoundedCornerShape(10.dp)).background(colors[i+1]).clickable {
                    onUpdateColor(colors[i+1].value)
                }
            )
        if (i + 2 < colors.size)
            Box(modifier = Modifier.width(64.dp).height(64.dp)
                .clip(RoundedCornerShape(10.dp)).background(colors[i+2]).clickable {
                    onUpdateColor(colors[i+2].value)
                }
            )
    }
    return i+3
}
