package ca.coffeeshopstudio.meksheets.data

import kotlin.math.ceil
import kotlin.math.roundToInt

enum class Location(val value: Int) {
    LeftTorso(0),
    CenterTorso(1),
    RightTorso(2),
    LeftArm(3),
    RightArm(4),
    LeftLeg(5),
    RightLeg(6),
    Head(7),
}

enum class ComponentType(val value: String) {
    HeatSink("Heat Sink"),
    Jump("Jump")
}
enum class TechType(val value: String) {
    Clan("CLAN"),
    //InnerSphere("IS")
}

open class Mech {
    val version = "2.0"
    var filename: String = ""
    var name: String = " "
    var description: String = " "
    var pilot: Pilot = Pilot()
    var tons: Int
        get() = _tons
        set(value) {
            _tons = value
            setInternalStructure()
        }

    val equipment: ArrayList<Equipment> = ArrayList()
    val ammo: ArrayList<Ammo> = ArrayList()
    protected val components = Components()

    var maxHeatSinks = 0
    var heatSinkType = 0
    var heatLevel = 0
    //this needs to be set like this so that the legacy import will work correctly
    var currentHeatSinks: Int = 0
        get() = maxHeatSinks - destroyedHeatSinkCount()

    var walk = 0
    val run: Int
        get() {
            return ceil(walk * 1.5).toInt()
        }

    var jump = 0
    //var torsoTwist = 0

    var internalCurrent = IntArray(Location.values().size)
        private set
    var internalMax = IntArray(Location.values().size)
        private set
    val armorMax = IntArray(Location.values().size)
    val armorCurrent = IntArray(Location.values().size)
    val armorRearMax = IntArray(3)
    val armorRearCurrent = IntArray(3)

    private var _tons = 0

    fun getComponentsFromLocation(location: Location): Array<Pair<String, Boolean>?> {
        return components.location(location)
    }

    /**
     * This will update the destroyed/not destroyed status of a component item
     * it will also trigger a refresh of the equipment for that location
     */
    fun updateComponentStatus(location: Location, index: Int, functional: Boolean) {
        components.location(location)[index] = components.location(location)[index]?.copy(second = functional)
        updateEquipmentStatus(location)
    }

    fun reset() {
        for (equipmentItem in equipment) {
            equipmentItem.fired = false
            equipmentItem.destroyed = false
        }
        for (ammoItem in ammo) {
            ammoItem.shotsFired = 0
        }
        heatLevel = 0
        for (location in Location.values()) {
            setArmorCurrent(location, armorMax[location.value])
            setInternalCurrent(location, getInternalMax(location))
            for (index in 0 until components.location(location).size)
                updateComponentStatus(location, index, true)
        }
        setRearArmorCurrent(Location.CenterTorso, armorRearMax[Location.CenterTorso.value])
        setRearArmorCurrent(Location.LeftTorso, armorRearMax[Location.LeftTorso.value])
        setRearArmorCurrent(Location.RightTorso, armorRearMax[Location.RightTorso.value])
    }

    fun currentWalk(): Int {
        return calculateCurrentSpeeds(true)
    }
    fun currentRun(): Int {
        return calculateCurrentSpeeds(false)
    }

    fun currentJump(): Int {
        var jumpSpeed: Int = jump

        //for jump jets look for broken ones, subtract by 1 per
        for (i in 0..11) {
            if (isJumpJetDestroyed(Location.LeftTorso, i))
                jumpSpeed--
            if (isJumpJetDestroyed(Location.RightTorso, i))
                jumpSpeed--
            if (isJumpJetDestroyed(Location.CenterTorso, i))
                jumpSpeed--
        }
        for (i in 3..5) {
            if (isJumpJetDestroyed(Location.LeftLeg, i))
                jumpSpeed--
            if (isJumpJetDestroyed(Location.RightTorso, i))
                jumpSpeed--
        }

        return jumpSpeed
    }

    /**
     * This will adjust the remaining health of the armor or IS for the given location
     * if damage is too severe, it will destroy that locations components then carry on
     * to the next relevant area (la -> lt -> ct for example)
     */
    fun adjustArmorCurrent(location: Location, adjustBy: Int, isInternalStructure: Boolean = false) {
        if (isInternalStructure) {
            if (internalCurrent[location.value] > adjustBy) {
                internalCurrent[location.value] -= adjustBy
            } else { //destroyed
                val remainder = adjustBy - internalCurrent[location.value]
                internalCurrent[location.value] = 0
                armorCurrent[location.value] = 0
                //destroy all components
                components.locationDestroyed(location)
                //clear out the armor as well, but we don't want to trigger another recursion,
                // so make sure we delete exactly what remains with getArmorCurrent
                adjustArmorCurrent(location, armorCurrent[location.value], false)
                when (location) {
                    Location.LeftArm, Location.LeftLeg
                        //also wipe out the left torso
                    -> adjustArmorCurrent(Location.LeftTorso, remainder, false)
                    Location.RightArm, Location.RightLeg
                        //also wipe out the right torso
                    -> adjustArmorCurrent(Location.RightTorso, remainder, false)
                    Location.LeftTorso, Location.RightTorso -> {
                        //handle the torso
                        setArmorCurrent(location, 0)
                        if (location == Location.LeftTorso) {
                            setArmorCurrent(Location.LeftArm, 0)
                            setInternalCurrent(Location.LeftArm, 0)
                            setArmorCurrent(Location.LeftLeg, 0)
                            setInternalCurrent(Location.LeftLeg, 0)
                            components.locationDestroyed(Location.LeftArm)
                            components.locationDestroyed(Location.LeftLeg)
                        } else  {
                            setArmorCurrent(Location.RightArm, 0)
                            setInternalCurrent(Location.RightArm, 0)
                            setArmorCurrent(Location.RightLeg, 0)
                            setInternalCurrent(Location.RightLeg, 0)
                            components.locationDestroyed(Location.RightArm)
                            components.locationDestroyed(Location.RightLeg)
                        }
                        adjustArmorCurrent(Location.CenterTorso, remainder, false)
                    }
                    else -> {} //no special handling for CT or Head
                }
            }
        } else {
            if (armorCurrent[location.value] >= adjustBy) {
                armorCurrent[location.value] -= adjustBy
            } else {
                val remainder = adjustBy - armorCurrent[location.value]
                armorCurrent[location.value] = 0
                adjustArmorCurrent(location, remainder, true)
            }
        }
    }

    protected fun addAmmo(component: String?, location: Location) {
        val itemName = String.format("$component ${fixLocationName(location)}")
        val ammo = Ammo(
            shotsFired = 0,
            location = location,
            name = itemName
        )
        this.ammo.add(ammo)
    }

    protected fun setArmorMax(position: Location, value: Int) {
        if (value >= 0) armorMax[position.value] = value
    }

    protected fun setRearArmorMax(position: Location, value: Int) {
        if (value >= 0) armorRearMax[position.value ] = value
    }

    protected fun setInternalStructure() {
        //array order - head, ct, lt, rt, la, ra, ll, rl
        when (tons) {
            10 -> buildInternal(4, 3, 1, 2)
            15 -> buildInternal(5, 4, 2, 3)
            20 -> buildInternal(6, 5, 3, 4)
            25 -> buildInternal(8, 6, 4, 6)
            30 -> buildInternal(10, 7, 5, 7)
            35 -> buildInternal(11, 8, 6, 8)
            40 -> buildInternal(12, 10, 6, 10)
            45 -> buildInternal(14, 11, 7, 11)
            50 -> buildInternal(16, 12, 8, 12)
            55 -> buildInternal(18, 13, 9, 13)
            60 -> buildInternal(20, 14, 10, 14)
            65 -> buildInternal(21, 15, 10, 15)
            70 -> buildInternal(22, 15, 11, 15)
            75 -> buildInternal(23, 16, 12, 16)
            80 -> buildInternal(25, 17, 13, 17)
            85 -> buildInternal(27, 18, 14, 18)
            90 -> buildInternal(29, 19, 15, 19)
            95 -> buildInternal(30, 20, 16, 20)
            100 -> buildInternal(31, 21, 17, 21)
        }
        internalCurrent = internalMax.copyOf()
    }
    /**
     * This checks if the mtf file or component contains the expected value
     * it handles the case sensitivity etc automatically and just returns
     * true if it matches and false if it doesn't
     * startsWith will return true if the value only starts with the string
     * otherwise it'll return true if it finds it anywhere in there
     */
    protected fun checkValue(source: String, compareWith: String, startsWith: Boolean = true): Boolean {
        if (source.length < compareWith.length)
            return false
        val valueToCheck = source.substring(0, compareWith.length).lowercase()

        return if (startsWith)
            valueToCheck == compareWith.lowercase()
        else
            source.lowercase().contains(compareWith.lowercase())
    }

    private fun setArmorCurrent(position: Location, value: Int) {
        if (value >= 0)
            armorCurrent[position.value] = value
    }

    private fun setRearArmorCurrent(position: Location, value: Int) {
        if (value >= 0) armorRearCurrent[position.value ] = value
    }

    private fun getInternalMax(position: Location): Int = internalMax[position.value]

    private fun getInternalCurrent(position: Location): Int =  internalCurrent[position.value]

    private fun setInternalCurrent(position: Location, value: Int) {
        if (value >= 0) internalCurrent[position.value] = value
    }

    private fun fixLocationName(location: Location) :String {
        var locationName = location.name
        //tweak the name a bit
        val torsoIndex = locationName.indexOf("Torso")
        val legIndex = locationName.indexOf("Leg")
        val armIndex = locationName.indexOf("Arm")
        if (torsoIndex > 0)
            locationName = "${locationName.substring(0, torsoIndex)} ${locationName.substring(torsoIndex)}"
        if (armIndex > 0)
            locationName = "${locationName.substring(0, torsoIndex)} ${locationName.substring(armIndex)}"
        if (legIndex > 0)
            locationName = "${locationName.substring(0, torsoIndex)} ${locationName.substring(legIndex)}"

        return locationName
    }

    /**
     * this returns either the walking speed (True) or running speed (False)
     */
    private fun calculateCurrentSpeeds(returnWalk: Boolean): Int {
        //get the basics
        var walkSpeed: Double = walk.toDouble()
        var runSpeed: Double = walk.toDouble() * 1.5

        //calculate damage penalties
        //for walking / running - hips cut us in half per destroyed hip.  actuators do not matter
        //at that point
        if (getInternalCurrent(Location.LeftLeg) == 0 || getInternalCurrent(Location.RightLeg) == 0) {
            walkSpeed = 1.0
            runSpeed = 0.0
        } else {
            val leftHipDestroyed: Boolean = !components.location(Location.LeftLeg)[0]!!.second
            val rightHipDestroyed: Boolean = !components.location(Location.RightLeg)[0]!!.second

            //check the hips
            if (leftHipDestroyed && rightHipDestroyed) {
                walkSpeed = 0.0
            } else {
                if (leftHipDestroyed || rightHipDestroyed) {
                    walkSpeed /= 2
                }
            }

            //actuator damage only counts for legs where the hip isn't destroyed
            for (i in 1..3) {
                if (!leftHipDestroyed && !components.location(Location.LeftLeg)[i]!!.second) {
                    walkSpeed--
                }
                if (!rightHipDestroyed && !components.location(Location.RightLeg)[i]!!.second) {
                    walkSpeed--
                }
            }
        }

        //adjust for calculations and rounding
        var actualWalkSpeed = walkSpeed.roundToInt()
        //first set to zero for run calculation, then reset to 1 - if necessary
        if (actualWalkSpeed < 0) actualWalkSpeed = 0
        else if (actualWalkSpeed < 1) actualWalkSpeed = 1

        if (runSpeed > 0)
            runSpeed = ceil(actualWalkSpeed * 1.5)

        return if (returnWalk)
            actualWalkSpeed
        else
            runSpeed.toInt()
    }

    /**
     * used to simplify the jump jet calculations in currentJumpJet
     */
    private fun isJumpJetDestroyed(location: Location, index: Int): Boolean {
        val component = components.location(location)[index]
        if (component!!.second &&
            checkValue(component.first.replace(" ", ""), ComponentType.Jump.value)) {
            return true
        }
        return false
    }

    //builds the internal structure array based on the passed in values
    private fun buildInternal(centerTorso: Int, sideTorso: Int, arms: Int, legs: Int) {
        //lt, ct, rt, la, ra, ll, rl, h
        internalMax = intArrayOf(sideTorso, centerTorso, sideTorso, arms, arms, legs, legs, 3)
    }

    //determines how many heat sinks are in a location
    private fun getHeatSinksForLocation(location: Array<Pair<String, Boolean>?>): Int {
        var hsCount = 0
        val isDouble = heatSinkType != 0
        if (!isDouble) {
            //regular heatsinks are easy - just count
            for (component in location) {
                if (component!!.first.contains(ComponentType.HeatSink.value)) {
                    if (!component.second) hsCount++ //that was easy
                }
            }
        } else { //double heat sinks
            var i = 0
            while (i < location.size) {
                val componentName = location[i]!!.first
                if (componentName.contains(ComponentType.HeatSink.value)) {
                    //first see if it's clan based.  default to IS
                    var hsSize = 3
                    if (componentName.startsWith(TechType.Clan.value)) {
                        hsSize = 2
                    }
                    var isFunctioning = true
                    //now that we found a new heat sink, go ahead by hsSize positions to see if it's functional
                    for (hsPos in 0 until hsSize) {
                        if (!location[i + hsPos]!!.second) {
                            isFunctioning = false
                            break
                        }
                    }
                    i = i + hsSize - 1
                    if (!isFunctioning) hsCount++
                }
                i++
            }
        }
        return hsCount
    }

    private fun destroyedHeatSinkCount(): Int {
        return  getHeatSinksForLocation(components.location(Location.LeftArm)) +
            getHeatSinksForLocation(components.location(Location.RightArm))+
            getHeatSinksForLocation(components.location(Location.CenterTorso))+
            getHeatSinksForLocation(components.location(Location.RightLeg))+
            getHeatSinksForLocation(components.location(Location.Head))+
            getHeatSinksForLocation(components.location(Location.LeftTorso)) +
            getHeatSinksForLocation(components.location(Location.RightTorso)) +
            getHeatSinksForLocation(components.location(Location.LeftLeg))
    }

    /**
     * updates the equipment list with destroyed or not status based on the component list
     */
    private fun updateEquipmentStatus(location: Location) {
        //reset
        equipment.forEach {
            if (it.location == location)
                it.destroyed = false
        }
        components.location(location).forEach { component ->
            if (!component!!.second) {
                equipment.forEach {item ->
                    if (item.location == location)
                        if (item.name == component.first && !item.destroyed)
                            item.destroyed = true
                }
            }
        }
    }
}