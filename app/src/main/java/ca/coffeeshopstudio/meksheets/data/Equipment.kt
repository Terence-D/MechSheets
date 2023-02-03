package ca.coffeeshopstudio.meksheets.data

data class Equipment (
    var name: String,
    var location: Location,
    var destroyed: Boolean = false,
    var fired: Boolean = false,
)