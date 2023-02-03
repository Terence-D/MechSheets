package ca.coffeeshopstudio.meksheets.data

import android.content.Context
import ca.coffeeshopstudio.meksheets.utils.FileOperations

class MechDataProvider {
    fun loadMechData(context: Context): ArrayList<Mech> {
        return FileOperations().getJsonFiles(context)
    }
}