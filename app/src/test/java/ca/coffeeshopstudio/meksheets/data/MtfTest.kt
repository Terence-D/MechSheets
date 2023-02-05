package ca.coffeeshopstudio.meksheets.data

import ca.coffeeshopstudio.meksheets.testLoad
import org.junit.Test
import java.io.BufferedReader
import java.io.StringReader

class MtfTest : AbstractMechTest() {
    private fun loadData(): Mtf {
        val reader = BufferedReader(StringReader(testLoad))
        val toTest = Mtf()
        toTest.readMTF(reader)
        return toTest
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

