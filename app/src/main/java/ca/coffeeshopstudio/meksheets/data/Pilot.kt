package ca.coffeeshopstudio.meksheets.data

import androidx.compose.ui.graphics.Color

data class Pilot(var name: String = "Pilot",
                 var piloting: Int = 5,
                 var gunnery: Int = 4,
                 var color: ULong = Color.Black.value,
                 var hits: Int = 0)