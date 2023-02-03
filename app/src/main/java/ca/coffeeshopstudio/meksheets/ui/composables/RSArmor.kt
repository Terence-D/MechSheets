package ca.coffeeshopstudio.meksheets.ui.composables

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.unit.dp
import ca.coffeeshopstudio.meksheets.R
import ca.coffeeshopstudio.meksheets.data.Location


@Composable
fun RSArmor(
    modifier: Modifier = Modifier,
    current: IntArray,
    max: IntArray,
    isInternalStructure: Boolean = false,
    onArmorChanged: (Location, Int, Boolean) -> Unit,
) {
    val showDialog =  remember { mutableStateOf(false) }
    val locationHit = remember { mutableStateOf(Location.LeftArm) }
    if(showDialog.value) {
        ArmorDialog(location = locationHit.value,
            setShowDialog = {
                showDialog.value = it
            },
            setValue = {
                onArmorChanged(locationHit.value, it, isInternalStructure)
            }
        )
    }

    var location: Location
    Card(
        modifier = modifier.padding(vertical = 4.dp),
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp)
        ) {
            if (current.size > 3) {
                Row() {
                    Spacer(modifier = Modifier.weight(1f))
                    location = Location.Head
                    ArmorButton(
                        showDialog = showDialog,
                        text = stringArrayResource(id = R.array.location_names)[location.value],
                        location = location,
                        locationHit = locationHit,
                        current = current[location.value], max = max[location.value]
                    )
                    Spacer(modifier = Modifier.weight(1f))
                }
                Row {
                    stringArrayResource(id = R.array.location_names)
                    location = Location.LeftArm
                    ArmorButton(
                        text = stringArrayResource(id = R.array.location_names)[location.value],
                        location = location,
                        locationHit = locationHit,
                        showDialog = showDialog,
                        current = current[location.value], max = max[location.value]
                    )
                    Spacer(modifier = Modifier.weight(1f))
                    location = Location.RightArm
                    ArmorButton(
                        text = stringArrayResource(id = R.array.location_names)[location.value],
                        showDialog = showDialog,
                        locationHit = locationHit,
                        location = location,
                        current = current[location.value], max = max[location.value]
                    )
                }
            }
            Row() {
                location = Location.LeftTorso
                ArmorButton(
                    text = stringArrayResource(id = R.array.location_names)[location.value],
                    location = location,
                    locationHit = locationHit,
                    showDialog = showDialog,
                    current = current[location.value], max = max[location.value]
                )
                location = Location.CenterTorso
                ArmorButton(
                    text = stringArrayResource(id = R.array.location_names)[location.value],
                    showDialog = showDialog,
                    locationHit = locationHit,
                    location = location,
                    current = current[location.value], max = max[location.value]
                )
                location = Location.RightTorso
                ArmorButton(
                    text = stringArrayResource(id = R.array.location_names)[location.value],
                    showDialog = showDialog,
                    locationHit = locationHit,
                    location = location,
                    current = current[location.value], max = max[location.value]
                )
            }
            if (current.size > 3) {
                Row() {
                    location = Location.LeftLeg
                    ArmorButton(
                        showDialog = showDialog,
                        text = stringArrayResource(id = R.array.location_names)[location.value],
                        locationHit = locationHit,
                        location = location,
                        current = current[location.value], max = max[location.value]
                    )
                    Spacer(modifier = Modifier.weight(1f))
                    location = Location.RightLeg
                    ArmorButton(
                        showDialog = showDialog,
                        text = stringArrayResource(id = R.array.location_names)[location.value],
                        locationHit = locationHit,
                        location = location,
                        current = current[location.value], max = max[location.value]
                    )
                }
            }
        }
    }
}

@Composable
private fun RowScope.ArmorButton(
    text: String,
    current: Int,
    location: Location,
    locationHit: MutableState<Location>,
    showDialog: MutableState<Boolean>,
    max: Int,
) {
    var color = MaterialTheme.colorScheme.primary
    if (current <= 0)
        color = MaterialTheme.colorScheme.error
    else if (current < max)
        color = MaterialTheme.colorScheme.tertiary
    Button(
        colors = ButtonDefaults.buttonColors(containerColor = color),
        shape = CutCornerShape(4.dp),
        contentPadding = PaddingValues(4.dp),
        onClick = {
            locationHit.value = location
            showDialog.value = true
        },
        modifier = Modifier
            .weight(1f)
            .padding(4.dp)
    )
    {
        Text("$text\n$current / $max")
    }
}