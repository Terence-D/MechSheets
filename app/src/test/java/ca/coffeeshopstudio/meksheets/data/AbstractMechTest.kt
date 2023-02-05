package ca.coffeeshopstudio.meksheets.data

import ca.coffeeshopstudio.meksheets.testJump
import ca.coffeeshopstudio.meksheets.testName
import ca.coffeeshopstudio.meksheets.testWalk
import ca.coffeeshopstudio.meksheets.testWeight
import org.junit.Assert

open class AbstractMechTest {
    protected fun loadOverview(toTest: Mech) {
        Assert.assertEquals(testName, toTest.name)
        Assert.assertEquals(testWeight, toTest.tons)
        Assert.assertEquals(testWalk, toTest.walk)
        Assert.assertEquals(testJump, toTest.jump)
    }

    protected fun loadEquipment(toTest: Mech) {
        
        Assert.assertEquals(3, toTest.equipment.size)
        Assert.assertEquals("Custom Item 3", toTest.equipment[0].name)
        Assert.assertEquals(false, toTest.equipment[1].fired)
        Assert.assertEquals(Location.RightArm, toTest.equipment[2].location)
        Assert.assertEquals(false, toTest.equipment[0].destroyed)
    }

    protected fun loadAmmo(toTest: Mech) {
        
        Assert.assertEquals(1, toTest.ammo.size)
        Assert.assertEquals("IS Ammo MG - Full Center Torso", toTest.ammo[0].name)
        Assert.assertEquals(0, toTest.ammo[0].shotsFired)
        Assert.assertEquals(Location.CenterTorso, toTest.ammo[0].location)
    }

    
    protected fun loadHeat(toTest: Mech) {
        
        Assert.assertEquals(10, toTest.currentHeatSinks)
        Assert.assertEquals(10, toTest.maxHeatSinks)
        Assert.assertEquals(0, toTest.heatLevel)
        Assert.assertEquals(0, toTest.heatSinkType)
    }

    
    protected fun loadArmor(toTest: Mech) {
        
        testArmor(toTest.armorCurrent)
        testArmor(toTest.armorMax)
        Assert.assertEquals(9, toTest.armorRearCurrent[Location.LeftTorso.value])
        Assert.assertEquals(10, toTest.armorRearCurrent[Location.RightTorso.value])
        Assert.assertEquals(11, toTest.armorRearCurrent[Location.CenterTorso.value])
        Assert.assertEquals(9, toTest.armorRearMax[Location.LeftTorso.value])
        Assert.assertEquals(10, toTest.armorRearMax[Location.RightTorso.value])
        Assert.assertEquals(11, toTest.armorRearMax[Location.CenterTorso.value])
    }

    
    protected fun loadInternal(toTest: Mech) {
        
        testInternal(toTest.internalCurrent)
        testInternal(toTest.internalMax)
    }

    
    protected fun loadComponentsLimbs(toTest: Mech) {
        
        testArm(toTest.getComponentsFromLocation(Location.LeftArm))
        Assert.assertEquals("Custom Item 1", toTest.getComponentsFromLocation(Location.LeftArm)[2]!!.first)
        Assert.assertEquals(true, toTest.getComponentsFromLocation(Location.LeftArm)[2]!!.second)
        Assert.assertEquals("Custom Item 2", toTest.getComponentsFromLocation(Location.RightArm)[2]!!.first)
        Assert.assertEquals(true, toTest.getComponentsFromLocation(Location.RightArm)[2]!!.second)
        testArm(toTest.getComponentsFromLocation(Location.RightArm))
        testLeg(toTest.getComponentsFromLocation(Location.LeftLeg))
        testLeg(toTest.getComponentsFromLocation(Location.RightLeg))
        Assert.assertEquals("Heat Sink", toTest.getComponentsFromLocation(Location.LeftLeg)[4]!!.first)
        Assert.assertEquals(true, toTest.getComponentsFromLocation(Location.LeftLeg)[4]!!.second)
        Assert.assertEquals("Heat Sink", toTest.getComponentsFromLocation(Location.RightLeg)[5]!!.first)
        Assert.assertEquals(true, toTest.getComponentsFromLocation(Location.RightLeg)[5]!!.second)
    }

    
    protected fun loadComponentsHead(toTest: Mech) {
        
        Assert.assertEquals("Life Support", toTest.getComponentsFromLocation(Location.Head)[0]!!.first)
        Assert.assertEquals("Sensors", toTest.getComponentsFromLocation(Location.Head)[1]!!.first)
        Assert.assertEquals("Cockpit", toTest.getComponentsFromLocation(Location.Head)[2]!!.first)
        Assert.assertEquals("Sensors", toTest.getComponentsFromLocation(Location.Head)[4]!!.first)
        Assert.assertEquals("Life Support", toTest.getComponentsFromLocation(Location.Head)[5]!!.first)
        Assert.assertEquals(true, toTest.getComponentsFromLocation(Location.Head)[0]!!.second)
        Assert.assertEquals(true, toTest.getComponentsFromLocation(Location.Head)[1]!!.second)
        Assert.assertEquals(true, toTest.getComponentsFromLocation(Location.Head)[2]!!.second)
        Assert.assertEquals(true, toTest.getComponentsFromLocation(Location.Head)[4]!!.second)
        Assert.assertEquals(true, toTest.getComponentsFromLocation(Location.Head)[5]!!.second)
    }

    
    protected fun loadComponentsTorso(toTest: Mech) {
        
        Assert.assertEquals("Jump Jet", toTest.getComponentsFromLocation(Location.LeftTorso)[0]!!.first)
        Assert.assertEquals(true, toTest.getComponentsFromLocation(Location.LeftTorso)[0]!!.second)
        Assert.assertEquals("Jump Jet", toTest.getComponentsFromLocation(Location.RightTorso)[1]!!.first)
        Assert.assertEquals(true, toTest.getComponentsFromLocation(Location.RightTorso)[1]!!.second)


        Assert.assertEquals("Fusion Engine", toTest.getComponentsFromLocation(Location.CenterTorso)[0]!!.first)
        Assert.assertEquals("Fusion Engine", toTest.getComponentsFromLocation(Location.CenterTorso)[1]!!.first)
        Assert.assertEquals("Fusion Engine", toTest.getComponentsFromLocation(Location.CenterTorso)[2]!!.first)
        Assert.assertEquals("Gyro", toTest.getComponentsFromLocation(Location.CenterTorso)[3]!!.first)
        Assert.assertEquals("Gyro", toTest.getComponentsFromLocation(Location.CenterTorso)[4]!!.first)
        Assert.assertEquals("Gyro", toTest.getComponentsFromLocation(Location.CenterTorso)[5]!!.first)
        Assert.assertEquals("Gyro", toTest.getComponentsFromLocation(Location.CenterTorso)[6]!!.first)
        Assert.assertEquals("Fusion Engine", toTest.getComponentsFromLocation(Location.CenterTorso)[7]!!.first)
        Assert.assertEquals("Fusion Engine", toTest.getComponentsFromLocation(Location.CenterTorso)[8]!!.first)
        Assert.assertEquals("Fusion Engine", toTest.getComponentsFromLocation(Location.CenterTorso)[9]!!.first)
        Assert.assertEquals(true, toTest.getComponentsFromLocation(Location.CenterTorso)[0]!!.second)
        Assert.assertEquals(true, toTest.getComponentsFromLocation(Location.CenterTorso)[1]!!.second)
        Assert.assertEquals(true, toTest.getComponentsFromLocation(Location.CenterTorso)[2]!!.second)
        Assert.assertEquals(true, toTest.getComponentsFromLocation(Location.CenterTorso)[4]!!.second)
        Assert.assertEquals(true, toTest.getComponentsFromLocation(Location.CenterTorso)[5]!!.second)
        Assert.assertEquals(true, toTest.getComponentsFromLocation(Location.CenterTorso)[6]!!.second)
        Assert.assertEquals(true, toTest.getComponentsFromLocation(Location.CenterTorso)[7]!!.second)
        Assert.assertEquals(true, toTest.getComponentsFromLocation(Location.CenterTorso)[8]!!.second)
        Assert.assertEquals(true, toTest.getComponentsFromLocation(Location.CenterTorso)[9]!!.second)

        Assert.assertEquals("Custom Item 3", toTest.getComponentsFromLocation(Location.CenterTorso)[10]!!.first)
        Assert.assertEquals(true, toTest.getComponentsFromLocation(Location.CenterTorso)[10]!!.second)
        Assert.assertEquals("IS Ammo MG - Full", toTest.getComponentsFromLocation(Location.CenterTorso)[11]!!.first)
        Assert.assertEquals(true, toTest.getComponentsFromLocation(Location.CenterTorso)[11]!!.second)
    }

    private fun testArm(location: Array<Pair<String, Boolean>?>) {
        Assert.assertEquals("Shoulder", location[0]!!.first)
        Assert.assertEquals("Upper Arm Actuator", location[1]!!.first)
        Assert.assertEquals(true, location[0]!!.second)
        Assert.assertEquals(true, location[1]!!.second)
    }

    private fun testLeg(location: Array<Pair<String, Boolean>?>) {
        Assert.assertEquals("Hip", location[0]!!.first)
        Assert.assertEquals("Upper Leg Actuator", location[1]!!.first)
        Assert.assertEquals("Lower Leg Actuator", location[2]!!.first)
        Assert.assertEquals("Foot Actuator", location[3]!!.first)
        Assert.assertEquals(true, location[0]!!.second)
        Assert.assertEquals(true, location[1]!!.second)
        Assert.assertEquals(true, location[2]!!.second)
        Assert.assertEquals(true, location[3]!!.second)
    }

    private fun testArmor(array: IntArray) {
        Assert.assertEquals(1, array[Location.LeftArm.value])
        Assert.assertEquals(2, array[Location.LeftLeg.value])
        Assert.assertEquals(3, array[Location.LeftTorso.value])
        Assert.assertEquals(4, array[Location.RightArm.value])
        Assert.assertEquals(5, array[Location.RightLeg.value])
        Assert.assertEquals(6, array[Location.RightTorso.value])
        Assert.assertEquals(7, array[Location.CenterTorso.value])
        Assert.assertEquals(8, array[Location.Head.value])
    }

    private fun testInternal(array: IntArray) {
        Assert.assertEquals(3, array[Location.LeftArm.value])
        Assert.assertEquals(4, array[Location.LeftLeg.value])
        Assert.assertEquals(5, array[Location.LeftTorso.value])
        Assert.assertEquals(3, array[Location.RightArm.value])
        Assert.assertEquals(4, array[Location.RightLeg.value])
        Assert.assertEquals(5, array[Location.RightTorso.value])
        Assert.assertEquals(6, array[Location.CenterTorso.value])
        Assert.assertEquals(3, array[Location.Head.value])
    }
}

