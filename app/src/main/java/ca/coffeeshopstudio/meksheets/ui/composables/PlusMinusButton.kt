package ca.coffeeshopstudio.meksheets.ui.composables

import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import ca.coffeeshopstudio.meksheets.R

@Composable
fun PlusMinusButton(
    modifier: Modifier = Modifier,
    onMinusClick: () -> Unit,
    onPlusClick: () -> Unit,
    @DrawableRes
    decreaseIcon: Int = R.drawable.ic_baseline_remove_24,
    @DrawableRes
    increaseIcon: Int = R.drawable.ic_baseline_add_24,
    text: String,
    color: Color = Color.Unspecified,
) {
    Card() {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Button(
                onClick = onMinusClick
            ) {
                Icon(
                    painter = painterResource(id = decreaseIcon),
                    contentDescription = stringResource(R.string.decrease),
                    modifier = Modifier.size(ButtonDefaults.IconSize)
                )
            }
            Text(text = text,
                textAlign = TextAlign.Center,
                color = color,
                modifier = Modifier.padding(8.dp, 0.dp).weight(1f))
            Button(
                onClick = onPlusClick
            ) {
                Icon(
                    painter = painterResource(id = increaseIcon),
                    contentDescription = stringResource(R.string.increase),
                    modifier = Modifier.size(ButtonDefaults.IconSize)
                )
            }
        }
    }
}