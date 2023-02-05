package ca.coffeeshopstudio.meksheets.data

import ca.coffeeshopstudio.meksheets.testJsonLoad
import ca.coffeeshopstudio.meksheets.utils.FileOperations
import org.junit.Assert
import org.junit.Test
import java.io.InputStreamReader

class MechTest: AbstractMechTest() {
    @Test
    fun mech_speed() {
        val toTest = Mech()
        toTest.walk = 3
        toTest.jump = 3

        Assert.assertEquals(3, toTest.walk)
        Assert.assertEquals(5, toTest.run)
        Assert.assertEquals(3, toTest.jump)
    }

    @Test
    fun mech_damaged_hip() {
        val toTest = loadData()
        toTest.walk = 6
        toTest.getComponentsFromLocation(Location.LeftLeg)[0] = toTest.getComponentsFromLocation(Location.LeftLeg)[0]!!.copy(second = false)

        Assert.assertEquals(3, toTest.currentWalk())
        Assert.assertEquals(5, toTest.currentRun())
    }

    @Test
    fun mech_damaged_leg_actuator() {
        val toTest = loadData()
        toTest.walk = 6
        toTest.getComponentsFromLocation(Location.RightLeg)[1] = toTest.getComponentsFromLocation(Location.RightLeg)[1]!!.copy(second = false)

        Assert.assertEquals(5, toTest.currentWalk())
        Assert.assertEquals(8, toTest.currentRun())
    }

    @Test
    fun mech_multiple_damaged_leg_actuator() {
        val toTest = loadData()
        toTest.walk = 6
        toTest.getComponentsFromLocation(Location.LeftLeg)[1] = toTest.getComponentsFromLocation(Location.LeftLeg)[1]!!.copy(second = false)
        toTest.getComponentsFromLocation(Location.RightLeg)[1] = toTest.getComponentsFromLocation(Location.RightLeg)[1]!!.copy(second = false)
        toTest.getComponentsFromLocation(Location.RightLeg)[2] = toTest.getComponentsFromLocation(Location.RightLeg)[2]!!.copy(second = false)

        Assert.assertEquals(3, toTest.currentWalk())
        Assert.assertEquals(5, toTest.currentRun())
    }

    @Test
    fun mech_destroyed_leg() {
        val toTest = loadData()
        toTest.walk = 6
        toTest.internalCurrent[Location.RightLeg.value] = 0

        Assert.assertEquals(1, toTest.currentWalk())
        Assert.assertEquals(0, toTest.currentRun())
    }

    @Test
    fun mech_reset() {
        val toTest = loadData()
        toTest.equipment[0].fired = true
        toTest.equipment[0].destroyed = true
        toTest.ammo[0].shotsFired = 5
        toTest.heatLevel = 10
        for (location in Location.values()) {
            toTest.armorCurrent[location.value] = 1
            toTest.internalCurrent[location.value] = 1
            for (index in 0 until toTest.getComponentsFromLocation(location).size)
                toTest.getComponentsFromLocation(location)[index] = toTest.getComponentsFromLocation(location)[index]?.copy(second = false)
        }
        toTest.armorRearCurrent[Location.CenterTorso.value] = 1
        toTest.armorRearCurrent[Location.LeftTorso.value] = 1
        toTest.armorRearCurrent[Location.RightTorso.value] = 1

        toTest.reset()

        Assert.assertEquals(false, toTest.equipment[0].fired)
        Assert.assertEquals(false, toTest.equipment[0].destroyed)
        Assert.assertEquals(0, toTest.ammo[0].shotsFired)
        Assert.assertEquals(0, toTest.heatLevel)
        for (location in Location.values()) {
            Assert.assertEquals(toTest.armorMax[location.value], toTest.armorCurrent[location.value])
            Assert.assertEquals(toTest.internalMax[location.value], toTest.internalCurrent[location.value])
            for (index in 0 until toTest.getComponentsFromLocation(location).size)
                Assert.assertEquals(true, toTest.getComponentsFromLocation(location)[index]!!.second)
        }
        Assert.assertEquals(toTest.armorRearMax[Location.CenterTorso.value], toTest.armorRearCurrent[Location.CenterTorso.value])
        Assert.assertEquals(toTest.armorRearMax[Location.LeftTorso.value], toTest.armorRearCurrent[Location.LeftTorso.value])
        Assert.assertEquals(toTest.armorRearMax[Location.RightTorso.value], toTest.armorRearCurrent[Location.RightTorso.value])
    }

    @Test
    fun mech_setTons_adjustsInternal() {
        val toTest = loadData()
        toTest.tons = 50
        Assert.assertEquals(16, toTest.internalCurrent[Location.CenterTorso.value])
        Assert.assertEquals(12, toTest.internalCurrent[Location.LeftTorso.value])
        Assert.assertEquals(12, toTest.internalCurrent[Location.RightLeg.value])
        Assert.assertEquals(8, toTest.internalCurrent[Location.LeftArm.value])
        toTest.tons = 100
        Assert.assertEquals(31, toTest.internalCurrent[Location.CenterTorso.value])
        Assert.assertEquals(21, toTest.internalCurrent[Location.LeftTorso.value])
        Assert.assertEquals(21, toTest.internalCurrent[Location.RightLeg.value])
        Assert.assertEquals(17, toTest.internalCurrent[Location.LeftArm.value])
    }

    @Test
    fun mech_heatSinks() {
        val toTest = loadData()
        val expectedHs = toTest.maxHeatSinks
        for (location in Location.values()) {
            for (index in 0 until toTest.getComponentsFromLocation(location).size)
                if (toTest.getComponentsFromLocation(location)[index]!!.first == ComponentType.HeatSink.value)
                    toTest.getComponentsFromLocation(location)[index] = toTest.getComponentsFromLocation(location)[index]!!.copy(second = false)
        }
        Assert.assertNotEquals(expectedHs, toTest.currentHeatSinks)
    }

    @Test
    fun mech_adjustInternal_noOtherDamage() {
        val toTest = loadData()
        //simulate 1 damage point
        toTest.adjustArmorCurrent(Location.RightArm, toTest.internalMax[Location.RightArm.value] - 1, isInternalStructure = true)
        //make sure it doesn't damage the IS or other locations
        Assert.assertEquals(1, toTest.internalCurrent[Location.RightArm.value])
        Assert.assertEquals(toTest.armorMax[Location.RightArm.value], toTest.armorCurrent[Location.RightArm.value])
        Assert.assertEquals(toTest.armorMax[Location.RightTorso.value], toTest.armorCurrent[Location.RightTorso.value])
    }

    @Test
    fun mech_adjustInternal_destroyed() {
        val toTest = loadData()
        toTest.adjustArmorCurrent(Location.RightArm, toTest.internalMax[Location.RightArm.value], isInternalStructure = true)

        Assert.assertEquals(0, toTest.internalCurrent[Location.RightArm.value])
        Assert.assertEquals(0, toTest.armorCurrent[Location.RightArm.value])
        Assert.assertEquals(toTest.armorMax[Location.RightTorso.value], toTest.armorCurrent[Location.RightTorso.value])
        for (index in 0 until toTest.getComponentsFromLocation(Location.RightArm).size)
            Assert.assertEquals(false, toTest.getComponentsFromLocation(Location.RightArm)[index]!!.second)
    }

    @Test
    fun mech_adjustArmor_noOtherDamage() {
        val toTest = loadData()
        //simulate 1 damage point
        toTest.adjustArmorCurrent(Location.RightArm, toTest.armorMax[Location.RightArm.value] - 1, isInternalStructure = false)
        //make sure it doesn't damage the IS or other locations
        Assert.assertEquals(1, toTest.armorCurrent[Location.RightArm.value])
        Assert.assertEquals(toTest.internalMax[Location.RightArm.value], toTest.internalCurrent[Location.RightArm.value])
        Assert.assertEquals(toTest.armorMax[Location.RightTorso.value], toTest.armorCurrent[Location.RightTorso.value])
    }

    @Test
    fun mech_adjustArmor_damagesToIsOnly() {
        val toTest = loadData()
        //simulate max+1 damage point
        toTest.adjustArmorCurrent(Location.RightArm, toTest.armorMax[Location.RightArm.value] + 1, isInternalStructure = false)
        //make sure it only damages the armor and IS
        Assert.assertEquals(0, toTest.armorCurrent[Location.RightArm.value])
        Assert.assertEquals(toTest.internalMax[Location.RightArm.value]-1, toTest.internalCurrent[Location.RightArm.value])
        Assert.assertEquals(toTest.armorMax[Location.RightTorso.value], toTest.armorCurrent[Location.RightTorso.value])
    }

    @Test
    fun mech_adjustArmor_destroysComponents() {
        val toTest = loadData()
        //simulate max+1 damage point
        toTest.adjustArmorCurrent(Location.RightArm, 999, isInternalStructure = false)
        //make sure it only damages the armor and IS
        Assert.assertEquals(0, toTest.armorCurrent[Location.RightArm.value])
        Assert.assertEquals(0, toTest.internalCurrent[Location.RightArm.value])
        for (index in 0 until toTest.getComponentsFromLocation(Location.RightArm).size)
            Assert.assertEquals(false, toTest.getComponentsFromLocation(Location.RightArm)[index]!!.second)
    }

    @Test
    fun mech_adjustArmor_destroySameSide() {
        val toTest = loadData()
        //simulate max+1 damage point
        toTest.adjustArmorCurrent(Location.RightArm, 999, isInternalStructure = false)
        //make sure it only damages the armor and IS
        Assert.assertEquals(0, toTest.armorCurrent[Location.RightTorso.value])
        Assert.assertEquals(0, toTest.internalCurrent[Location.RightTorso.value])
        Assert.assertEquals(0, toTest.armorCurrent[Location.RightLeg.value])
        Assert.assertEquals(0, toTest.internalCurrent[Location.RightLeg.value])
        for (index in 0 until toTest.getComponentsFromLocation(Location.RightTorso).size)
            Assert.assertEquals(false, toTest.getComponentsFromLocation(Location.RightTorso)[index]!!.second)
        for (index in 0 until toTest.getComponentsFromLocation(Location.RightLeg).size)
            Assert.assertEquals(false, toTest.getComponentsFromLocation(Location.RightLeg)[index]!!.second)
    }

    @Test
    fun mech_adjustArmor_destroyCenterTorso() {
        val toTest = loadData()
        //simulate max+1 damage point
        toTest.adjustArmorCurrent(Location.RightArm, 999, isInternalStructure = false)
        //make sure it only damages the armor and IS
        Assert.assertEquals(0, toTest.armorCurrent[Location.CenterTorso.value])
        Assert.assertEquals(0, toTest.internalCurrent[Location.CenterTorso.value])
        for (index in 0 until toTest.getComponentsFromLocation(Location.CenterTorso).size)
            Assert.assertEquals(false, toTest.getComponentsFromLocation(Location.CenterTorso)[index]!!.second)
    }

    @Test
    fun mech_destroyComponent_destroysEquipment() {
        val toTest = loadData()
        //simulate max+1 damage point
        toTest.updateComponentStatus(Location.CenterTorso, 10, false)
        //make sure it only damages the armor and IS
        Assert.assertEquals(true, toTest.equipment[0].destroyed)
        Assert.assertEquals(false, toTest.equipment[1].destroyed)
        Assert.assertEquals(false, toTest.equipment[2].destroyed)
    }

    //ensure we load in data accurately
    private fun loadData(): Mech {
        val isr = InputStreamReader(testJsonLoad.byteInputStream())
        return FileOperations().loadMech(isr)!!
    }

    @Test
    fun mtf_loadOverview() { loadOverview(loadData()) }

    @Test
    fun mtf_loadEquipment()  { loadEquipment(loadData()) }

    @Test
    fun mtf_loadAmmo() { loadAmmo(loadData()) }

    @Test
    fun mtf_loadHeat() { loadHeat(loadData()) }

    @Test
    fun mtf_loadArmor() { loadArmor(loadData()) }

    @Test
    fun mtf_loadInternal() { loadInternal(loadData()) }

    @Test
    fun mtf_loadComponentsLimbs() { loadComponentsLimbs(loadData()) }

    @Test
    fun mtf_loadComponentsHead() { loadComponentsHead(loadData()) }

    @Test
    fun mtf_loadComponentsTorso() { loadComponentsTorso(loadData()) }
}
