package ca.coffeeshopstudio.meksheets.ui.composables

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import ca.coffeeshopstudio.meksheets.R
import ca.coffeeshopstudio.meksheets.data.Location

@Composable
fun ArmorDialog(location: Location,
                setShowDialog: (Boolean) -> Unit,
                setValue: (Int) -> Unit)
{
    val damageValue = remember { mutableStateOf(0) }

    Dialog(onDismissRequest = { setShowDialog(false) }) {
        Surface(
            shape = RoundedCornerShape(16.dp),
            color = MaterialTheme.colorScheme.surfaceVariant
        ) {
            Column(modifier = Modifier.padding(20.dp).fillMaxWidth()) {
                MechHeaderB(stringResource(R.string.adjust_damage))
                Text("${stringArrayResource(id = R.array.location_names)[location.value]} ${stringResource(
                    id = R.string.hit_for)} ${damageValue.value} ${stringResource(id = R.string.damage)}",
                    textAlign = TextAlign.Center,
                )
                Spacer(modifier = Modifier.height(20.dp))
                Column(modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    PlusMinusButton(
                        modifier = Modifier.fillMaxWidth(),
                        onMinusClick = { damageValue.value-- },
                        onPlusClick = { damageValue.value++ },
                        text = stringResource(id = R.string.adjust_damage)
                    )
                }

                //we need to loop twice as we want rows of 3
                //this array is the common damage values for 1 click entry
                val damageValues = intArrayOf(2,3,5,6,7,8,9,10,15,16,20,25)
                for (i in damageValues.indices step 3) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Button(onClick = { damageValue.value = damageValues[i] }) {
                            Text(damageValues[i].toString())
                        }
                        if (i + 1 < damageValues.size)
                            Button(onClick = { damageValue.value = damageValues[i+1] }) {
                                Text(damageValues[i+1].toString())
                            }
                        if (i + 2 < damageValues.size)
                            Button(onClick = { damageValue.value = damageValues[i+2] }) {
                                Text(damageValues[i+2].toString())
                            }
                    }
                }

                Spacer(modifier = Modifier.height(20.dp))
                Box(modifier = Modifier.padding(40.dp, 0.dp, 40.dp, 0.dp)
                ) {
                    FloatingActionButton(
                        onClick = {
                            setValue(damageValue.value)
                            setShowDialog(false)
                        }
                        )
                    {
                        Text(stringResource(id = R.string.done))
                    }
                }
            }
        }
    }
}