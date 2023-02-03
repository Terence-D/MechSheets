package ca.coffeeshopstudio.meksheets.ui.composables

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import ca.coffeeshopstudio.meksheets.data.Location
import ca.coffeeshopstudio.meksheets.data.MTF_EMPTY
import ca.coffeeshopstudio.meksheets.data.legacy.Mek.*

@Composable
fun RSComponent(
    components: Array<Pair<String, Boolean>?>,
    title: String,
    location: Location,
    onUpdateComponent: (Location, Int) -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.padding(vertical = 4.dp),
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp)
        ) {
            MechHeaderB(title, modifier = modifier)
            for ((index, it) in components.withIndex()) {
                val color = componentStatusColor(it)
                Button(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(0.dp, 4.dp),
                    shape = CutCornerShape(4.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = (color)),
                    contentPadding = PaddingValues(4.dp),
                    onClick = {
//                        components[index] =
//                            components[index]!!.copy(second = !components[index]!!.second)
                        onUpdateComponent(location, index)
                    }
                ) {
                    Text(
                        "$index) ${it!!.first}",
                        modifier = Modifier.fillMaxWidth(),
                        minLines = 2,
                        maxLines = 2,
                        textAlign = TextAlign.Start
                    )
                }
            }
        }
    }
}

@Composable
fun componentStatusColor(it: Pair<String, Boolean>?): Color {
    if (!it!!.second)
        return MaterialTheme.colorScheme.error

    if (it.first.equals(MTF_EMPTY) ||
        it.first.equals(MTF_ENDO) ||
        it.first.equals(MTF_CASE) ||
        it.first.equals(MTF_FERRO))
        return MaterialTheme.colorScheme.secondary

    return MaterialTheme.colorScheme.primary
}
