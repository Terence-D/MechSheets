package ca.coffeeshopstudio.meksheets.ui.composables

import androidx.annotation.StringRes
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign

@Composable
fun MechHeaderA(
    @StringRes
    stringRes: Int,
    modifier: Modifier = Modifier
) {
    MechHeaderA(string = stringResource(id = stringRes), modifier = modifier)
}

@Composable
fun MechHeaderA(
    string: String,
    modifier: Modifier = Modifier
) {
    Text(
        modifier = modifier,
        text = string,
        color = MaterialTheme.colorScheme.primary,
        style = MaterialTheme.typography.titleLarge,
        textAlign = TextAlign.Center)
}

@Composable
fun MechHeaderB(
    @StringRes
    stringRes: Int,
    modifier: Modifier = Modifier
) {
    MechHeaderB(string = stringResource(id = stringRes), modifier = modifier)
}

@Composable
fun MechHeaderB(
    string: String,
    modifier: Modifier = Modifier
) {
    Text(string,
        style = MaterialTheme.typography.titleMedium,
        color = MaterialTheme.colorScheme.primary,
        textAlign = TextAlign.Center)
}
