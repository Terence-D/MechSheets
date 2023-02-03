package ca.coffeeshopstudio.meksheets.ui.composables

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog

@Composable
fun YesNoDialog(text: String,
                setShowDialog: (Boolean) -> Unit,
                onClickAffirmative: (Boolean) -> Unit)
{
    Dialog(onDismissRequest = { setShowDialog(false) }) {
        Surface(
            shape = RoundedCornerShape(16.dp),
            color = MaterialTheme.colorScheme.surface
        ) {
            Box(
                contentAlignment = Alignment.Center
            ) {
                Column(modifier = Modifier.padding(20.dp)) {
                    Text(text)
                    Spacer(modifier = Modifier.height(20.dp))
                    Row(horizontalArrangement = Arrangement.SpaceEvenly,
                        modifier = Modifier.fillMaxWidth()) {
                        Button(
                            onClick = {
                                onClickAffirmative(true)
                                setShowDialog(false)
                            }
                        )
                        {
                            Text(stringResource(id = android.R.string.ok))
                        }
                        Button(
                            onClick = {
                                setShowDialog(false)
                            }
                        )
                        {
                            Text(stringResource(id = android.R.string.cancel))
                        }
                    }
                }
            }
        }
    }
}