package ca.coffeeshopstudio.meksheets.data

import android.util.Log
import java.io.BufferedReader
import java.io.IOException
import java.util.regex.Pattern

const val MTF_EMPTY = "-Empty-"
private const val MTF_HEAT_SINKS = "Heat Sinks:"
private const val MTF_RL = "Right Leg"
private const val MTF_AMMO = "Ammo"
//private const val MTF_ENDO = "Endo-Steel"
//private const val MTF_FERRO = "Ferro-Fibrous"
//private const val MTF_CASE = "CASE"
//private const val MTF_HEAT_SINK = "HeatSink"
private const val MTF_VERSION = "Version:1."
private const val MTF_TONS = "Mass:"
private const val MTF_SINGLE_HS = "Single"
private const val MTF_WALK = "Walk MP:"
private const val MTF_JUMP = "Jump MP:"
private const val MTF_ARMOR = "Armor:"
private const val MTF_ARMOR_LA = "LA Armor:"
private const val MTF_ARMOR_RA = "RA Armor:"
private const val MTF_ARMOR_LT = "LT Armor:"
private const val MTF_ARMOR_RT = "RT Armor:"
private const val MTF_ARMOR_CT = "CT Armor:"
private const val MTF_ARMOR_HD = "HD Armor:"
private const val MTF_ARMOR_LL = "LL Armor:"
private const val MTF_ARMOR_RL = "RL Armor:"
private const val MTF_ARMOR_LTR = "RTL Armor:"
private const val MTF_ARMOR_RTR = "RTR Armor:"
private const val MTF_ARMOR_CTR = "RTC Armor:"
private const val MTF_WEAPONS = "Weapons:"
private const val MTF_LA = "Left Arm:"
private const val MTF_RA = "Right Arm:"
private const val MTF_LT = "Left Torso:"
private const val MTF_RT = "Right Torso:"
private const val MTF_CT = "Center Torso:"
private const val MTF_H = "Head:"
private const val MTF_LL = "Left Leg"
//private const val MTF_CLAN = "CL"

class Mtf : Mech() {
    @Throws(IOException::class)
    fun readMTF(br: BufferedReader) {
        if (!validateMtfFile(br)) {
            throw IOException("invalid file format!")
        }
        try {
            br.forEachLine {
                extractMtfInfo(it, br)
            }
        } catch (e: java.lang.Exception) {
            Log.d("meksheets", e.localizedMessage!!)
        }
        br.close()
    }

    private fun extractNumbers(s: String?): Int {
        var numbers = ""
        val p = Pattern.compile("\\d+")
        val m = p.matcher(s.toString())
        while (m.find()) {
            numbers += m.group().toInt()
        }
        return numbers.toInt()
    }

    @Throws(IOException::class)
    private fun extractComponents(location: Location, component: String, br: BufferedReader) {
        var componentName: String? = component
        var position = -1
        do {
            position++
            components.location(location)[position] =
                components.location(location)[position]!!.copy(componentName!!, true)
            if (checkValue(componentName, MTF_AMMO, false)) {
                addAmmo(componentName, location)
            }
            componentName = br.readLine()
        } while (componentName != null &&
            !checkValue(componentName," ") &&
            componentName.isNotEmpty() &&
            position < components.location(location).size - 1
        )
    }

    private fun extractName(br: BufferedReader): Boolean {
        val line = br.readLine()
        if (checkValue(line, MTF_VERSION)) { //the information we want is on the next 2 lines
            name = br.readLine()
            name += " ${br.readLine()}"
            return true
        }
        return false
    }

    @Throws(IOException::class)
    private fun validateMtfFile(br: BufferedReader): Boolean = extractName(br)

    @Throws(IOException::class)
    private fun extractMtfInfo(line: String, br: BufferedReader) {
        //TONS
        if (checkValue(line,MTF_TONS)) {
            tons = line.substring(MTF_TONS.length).toInt()
            //we can now calculate the internal structure
            setInternalStructure()
        }
        //HEAT SINKS
        else if (checkValue(line,MTF_HEAT_SINKS))
            extractHeatSinks(line)
        //WALKING SPEED
        else if (checkValue(line,MTF_WALK))
            walk = line.substring(MTF_WALK.length).toInt()
        //JUMP SPEED
        else if (checkValue(line,MTF_JUMP))
            jump = line.substring(MTF_JUMP.length).toInt()
        //ARMOR
        else if (checkValue(line,MTF_ARMOR))
            extractArmor(br.readLine(), br)
        //WEAPONS
        else if (checkValue(line,MTF_WEAPONS))
            extractEquipment(br.readLine(), br)
        //COMPONENTS
        else if (checkValue(line,MTF_LA))
            extractComponents(Location.LeftArm, br.readLine(), br)
        else if (checkValue(line,MTF_RA))
            extractComponents(Location.RightArm, br.readLine(), br)
        else if (checkValue(line,MTF_CT))
            extractComponents(Location.CenterTorso, br.readLine(), br)
        else if (checkValue(line,MTF_H))
            extractComponents(Location.Head, br.readLine(), br)
        else if (checkValue(line,MTF_LT))
            extractComponents(Location.LeftTorso, br.readLine(), br)
        else if (checkValue(line,MTF_RT))
            extractComponents(Location.RightTorso, br.readLine(), br)
        else if (checkValue(line,MTF_LL))
            extractComponents(Location.LeftLeg, br.readLine(), br)
        else if (checkValue(line,MTF_RL))
            extractComponents(Location.RightLeg, br.readLine(), br)
    }

    @Throws(IOException::class)
    private fun extractEquipment(line: String, br: BufferedReader) {
        var extractedLine = line
        do {
            //first find out where the , part of the string is, as that separates the name and
            // the loc
            val location = getEquipmentLocation(extractedLine)
            val separator = extractedLine.indexOf(", ")
            val name = extractedLine.substring(0, separator)
            val item = Equipment(
                name = name,
                location = location!!
            )
            equipment.add(item)
            extractedLine = br.readLine()
        } while (!checkValue(extractedLine," ") && extractedLine.isNotEmpty())
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

    //we will loop through here until we find a space
    @Throws(IOException::class)
    private fun extractArmor(line: String, br: BufferedReader) {
        var extractedLine = line
        do {
            if (checkValue(extractedLine,MTF_ARMOR_LA))
                setArmorMax(Location.LeftArm, extractedLine.substring(MTF_ARMOR_LA.length).toInt())
            else if (checkValue(extractedLine,MTF_ARMOR_RA))
                setArmorMax(Location.RightArm, extractedLine.substring(MTF_ARMOR_RA.length).toInt())
            else if (checkValue(extractedLine,MTF_ARMOR_LT))
                setArmorMax(
                    Location.LeftTorso,
                    extractedLine.substring(MTF_ARMOR_LT.length).toInt()
                )
            else if (checkValue(extractedLine,MTF_ARMOR_RT))
                setArmorMax(
                    Location.RightTorso,
                    extractedLine.substring(MTF_ARMOR_RT.length).toInt()
                )
            else if (checkValue(extractedLine,MTF_ARMOR_CT))
                setArmorMax(
                    Location.CenterTorso,
                    extractedLine.substring(MTF_ARMOR_CT.length).toInt()
                )
            else if (checkValue(extractedLine,MTF_ARMOR_HD))
                setArmorMax(Location.Head, extractedLine.substring(MTF_ARMOR_HD.length).toInt())
            else if (checkValue(extractedLine,MTF_ARMOR_LL))
                setArmorMax(Location.LeftLeg, extractedLine.substring(MTF_ARMOR_LL.length).toInt())
            else if (checkValue(extractedLine,MTF_ARMOR_RL))
                setArmorMax(Location.RightLeg, extractedLine.substring(MTF_ARMOR_RA.length).toInt())
            else if (checkValue(extractedLine,MTF_ARMOR_CTR))
                setRearArmorMax(
                    Location.CenterTorso,
                    extractedLine.substring(MTF_ARMOR_CTR.length).toInt()
                )
            else if (checkValue(extractedLine,MTF_ARMOR_LTR))
                setRearArmorMax(
                    Location.LeftTorso,
                    extractedLine.substring(MTF_ARMOR_LTR.length).toInt()
                )
            else if (checkValue(extractedLine,MTF_ARMOR_RTR))
                setRearArmorMax(
                    Location.RightTorso,
                    extractedLine.substring(MTF_ARMOR_RTR.length).toInt()
                )
            extractedLine = br.readLine()
        } while (!checkValue(extractedLine," ") && extractedLine.isNotEmpty())
        System.arraycopy(armorMax, 0, armorCurrent, 0, armorMax.size)
        System.arraycopy(armorRearMax, 0, armorRearCurrent, 0, armorRearMax.size)
    }

    private fun extractHeatSinks(line: String) {
        val heatsinks: String =
            line.substring(MTF_HEAT_SINKS.length)
        maxHeatSinks = extractNumbers(heatsinks)

        heatSinkType = if (checkValue(heatsinks, MTF_SINGLE_HS, false))
            0
        else
            1
    }
}