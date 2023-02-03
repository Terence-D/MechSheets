package ca.coffeeshopstudio.meksheets.data.legacy

import android.content.Context
import android.util.Log
import ca.coffeeshopstudio.meksheets.data.Ammo
import ca.coffeeshopstudio.meksheets.data.Equipment
import ca.coffeeshopstudio.meksheets.data.Location
import ca.coffeeshopstudio.meksheets.data.Mech
import ca.coffeeshopstudio.meksheets.utils.FileOperations
import com.google.gson.Gson
import java.io.BufferedReader
import java.io.File
import java.io.FileInputStream
import java.io.InputStreamReader

class LegacyLoader {
    fun loadLegacy(context: Context) : Boolean {
        val files: File = context.filesDir
        var rv = false
        for (file in files.listFiles()!!) {
            if (file.name.endsWith(".json")) {
                rv = true //we got one!
                val mek: Mek? =
                    readFile(context, file.name)
                val mech = convert(mek!!)
                file.delete()
                FileOperations().writeToJson(context, mech)
            }
        }
        return rv
    }

    private fun readFile(context: Context, fileName: String): Mek? {
        val fis: FileInputStream
        return try {
            fis = context.openFileInput(fileName)
            val isr = InputStreamReader(fis)
            val bufferedReader = BufferedReader(isr)
            val sb = StringBuilder()
            var line: String?
            while (bufferedReader.readLine().also { line = it } != null) {
                sb.append(line)
            }
            val json = sb.toString()
            val gson = Gson()
            gson.fromJson(json, Mek::class.java)
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    private fun convert(mek: Mek): Mech {
        val mech = Mech()
        mech.name = mek.name
        mek.ammo.forEach {
            val ammo = Ammo()
            ammo.location = getEquipmentLocation(it.name)
            ammo.name = it.name
            ammo.shotsFired = it.shotsFired
            mech.ammo.add(ammo)
        }
        mek.equipment.forEach {
            val equip = Equipment(
                location = getEquipmentLocation(it.name)!!,
                name = it.name,
                fired = it.isChecked
            )
            mech.equipment.add(equip)
        }
        mech.currentHeatSinks = mek.currentHeatSinks
        mech.description = if (mek.description == null) "" else mek.description
        mech.filename = mek.fileName
        if (mech.filename.contains(".json"))
            mech.filename = mech.filename.replace(".json", ".jsn")
        mech.heatLevel = mek.heatLevel
        mech.heatSinkType = mek.heatSinkType
        mech.jump = mek.jump
        mech.maxHeatSinks = mek.maxHeatSinks
        val pilot = ca.coffeeshopstudio.meksheets.data.Pilot()
        pilot.piloting = mek.pilot.piloting
        pilot.gunnery = mek.pilot.gunnery
        pilot.hits = mek.pilot.hits
        mech.pilot = pilot
        mech.tons = mek.tons
        mech.walk = mek.walk

        mech.armorMax[Location.LeftLeg.value] = mek.getArmorMax(Locations.leftLeg)
        mech.armorMax[Location.RightLeg.value] = mek.getArmorMax(Locations.rightLeg)
        mech.armorMax[Location.LeftArm.value] = mek.getArmorMax(Locations.leftArm)
        mech.armorMax[Location.RightArm.value] = mek.getArmorMax(Locations.rightArm)
        mech.armorMax[Location.LeftTorso.value] = mek.getArmorMax(Locations.leftTorso)
        mech.armorMax[Location.RightTorso.value] = mek.getArmorMax(Locations.rightTorso)
        mech.armorMax[Location.CenterTorso.value] = mek.getArmorMax(Locations.centerTorso)
        mech.armorMax[Location.Head.value] = mek.getArmorMax(Locations.head)
        mech.armorCurrent[Location.LeftLeg.value] = mek.getArmorCurrent(Locations.leftLeg)
        mech.armorCurrent[Location.RightLeg.value] = mek.getArmorCurrent(Locations.rightLeg)
        mech.armorCurrent[Location.LeftArm.value] = mek.getArmorCurrent(Locations.leftArm)
        mech.armorCurrent[Location.RightArm.value] = mek.getArmorCurrent(Locations.rightArm)
        mech.armorCurrent[Location.LeftTorso.value] = mek.getArmorCurrent(Locations.leftTorso)
        mech.armorCurrent[Location.RightTorso.value] = mek.getArmorCurrent(Locations.rightTorso)
        mech.armorCurrent[Location.CenterTorso.value] = mek.getArmorCurrent(Locations.centerTorso)
        mech.armorCurrent[Location.Head.value] = mek.getArmorCurrent(Locations.head)
        mech.internalCurrent[Location.LeftLeg.value] = mek.getInternalCurrent(Locations.leftLeg)
        mech.internalCurrent[Location.RightLeg.value] = mek.getInternalCurrent(Locations.rightLeg)
        mech.internalCurrent[Location.LeftArm.value] = mek.getInternalCurrent(Locations.leftArm)
        mech.internalCurrent[Location.RightArm.value] = mek.getInternalCurrent(Locations.rightArm)
        mech.internalCurrent[Location.LeftTorso.value] = mek.getInternalCurrent(Locations.leftTorso)
        mech.internalCurrent[Location.RightTorso.value] = mek.getInternalCurrent(Locations.rightTorso)
        mech.internalCurrent[Location.CenterTorso.value] = mek.getInternalCurrent(Locations.centerTorso)
        mech.internalCurrent[Location.Head.value] = mek.getInternalCurrent(Locations.head)
        mech.internalMax[Location.LeftLeg.value] = mek.getInternalCurrent(Locations.leftLeg)
        mech.internalMax[Location.RightLeg.value] = mek.getInternalCurrent(Locations.rightLeg)
        mech.internalMax[Location.LeftArm.value] = mek.getInternalCurrent(Locations.leftArm)
        mech.internalMax[Location.RightArm.value] = mek.getInternalCurrent(Locations.rightArm)
        mech.internalMax[Location.LeftTorso.value] = mek.getInternalCurrent(Locations.leftTorso)
        mech.internalMax[Location.RightTorso.value] = mek.getInternalCurrent(Locations.rightTorso)
        mech.internalMax[Location.CenterTorso.value] = mek.getInternalCurrent(Locations.centerTorso)
        mech.internalMax[Location.Head.value] = mek.getInternalCurrent(Locations.head)

        mech.armorRearMax[Location.LeftTorso.value] = mek.getArmorRearMax(Locations.leftTorso)
        mech.armorRearMax[Location.RightTorso.value] = mek.getArmorRearMax(Locations.rightTorso)
        mech.armorRearMax[Location.CenterTorso.value] = mek.getArmorRearMax(Locations.centerTorso)
        mech.armorRearCurrent[Location.LeftTorso.value] = mek.getArmorRearCurrent(Locations.leftTorso)
        mech.armorRearCurrent[Location.RightTorso.value] = mek.getArmorRearCurrent(Locations.rightTorso)
        mech.armorRearCurrent[Location.CenterTorso.value] = mek.getArmorRearCurrent(Locations.centerTorso)

        for ((index, it) in mek.getComponents(Locations.centerTorso).withIndex()) {
            val component = Pair<String, Boolean>(it.name, it.isFunctioning)
            mech.getComponentsFromLocation(Location.CenterTorso)[index] = component
        }
        for ((index, it) in mek.getComponents(Locations.leftTorso).withIndex()) {
            val component = Pair<String, Boolean>(it.name, it.isFunctioning)
            mech.getComponentsFromLocation(Location.LeftTorso)[index] = component
        }
        for ((index, it) in mek.getComponents(Locations.rightTorso).withIndex()) {
            val component = Pair<String, Boolean>(it.name, it.isFunctioning)
            mech.getComponentsFromLocation(Location.RightTorso)[index] = component
        }
        for ((index, it) in mek.getComponents(Locations.leftArm).withIndex()) {
            val component = Pair<String, Boolean>(it.name, it.isFunctioning)
            mech.getComponentsFromLocation(Location.LeftArm)[index] = component
        }
        for ((index, it) in mek.getComponents(Locations.rightArm).withIndex()) {
            val component = Pair<String, Boolean>(it.name, it.isFunctioning)
            mech.getComponentsFromLocation(Location.RightArm)[index] = component
        }
        for ((index, it) in mek.getComponents(Locations.leftLeg).withIndex()) {
            val component = Pair<String, Boolean>(it.name, it.isFunctioning)
            mech.getComponentsFromLocation(Location.LeftLeg)[index] = component
        }
        for ((index, it) in mek.getComponents(Locations.rightLeg).withIndex()) {
            val component = Pair<String, Boolean>(it.name, it.isFunctioning)
            mech.getComponentsFromLocation(Location.RightLeg)[index] = component
        }
        for ((index, it) in mek.getComponents(Locations.head).withIndex()) {
            val component = Pair<String, Boolean>(it.name, it.isFunctioning)
            mech.getComponentsFromLocation(Location.Head)[index] = component
        }

        return mech
    }

    private fun getEquipmentLocation(toSearch: String) : Location? {
        val startPosition = toSearch.indexOf(", ")
        if (startPosition < 1)
            return null
        var locationString: String = toSearch.substring(startPosition).lowercase()
        val reverseIndicator = locationString.indexOf(" (R)")
        if (reverseIndicator > 0) {
            locationString = locationString.substring(0, reverseIndicator)
        }
        if (locationString.contains("left arm"))
            return Location.LeftArm
        else if (locationString.contains("left leg"))
            return Location.LeftLeg
        else if (locationString.contains("left torso"))
            return Location.LeftTorso
        else if (locationString.contains("right arm"))
            return Location.RightArm
        else if (locationString.contains("right leg"))
            return Location.RightLeg
        else if (locationString.contains("right torso"))
            return Location.RightTorso
        else if (locationString.contains("center torso"))
            return Location.CenterTorso
        else if (locationString.contains("head"))
            return Location.Head

        Log.d("meksheets", "unable to find equipment location - $toSearch")
        return null
    }
}