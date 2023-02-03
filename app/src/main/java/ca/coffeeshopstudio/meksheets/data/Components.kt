package ca.coffeeshopstudio.meksheets.data

class Components {
    private var laComponents = arrayOfNulls<Pair<String, Boolean>>(12)
    private val raComponents = arrayOfNulls<Pair<String, Boolean>>(12)
    private val ctComponents = arrayOfNulls<Pair<String, Boolean>>(12)
    private val hComponents = arrayOfNulls<Pair<String, Boolean>>(6)
    private val ltComponents = arrayOfNulls<Pair<String, Boolean>>(12)
    private val rtComponents = arrayOfNulls<Pair<String, Boolean>>(12)
    private val llComponents = arrayOfNulls<Pair<String, Boolean>>(6)
    private val rlComponents = arrayOfNulls<Pair<String, Boolean>>(6)

    init {
        initComponent(laComponents)
        initComponent(raComponents)
        initComponent(ctComponents)
        initComponent(hComponents)
        initComponent(ltComponents)
        initComponent(rtComponents)
        initComponent(llComponents)
        initComponent(rlComponents)
    }

    fun locationDestroyed(location: Location) {
        when (location) {
            Location.LeftTorso -> destroyLocationsComponent(ltComponents)
            Location.CenterTorso -> destroyLocationsComponent(ctComponents)
            Location.RightTorso -> destroyLocationsComponent(rtComponents)
            Location.LeftArm -> destroyLocationsComponent(laComponents)
            Location.RightArm -> destroyLocationsComponent(raComponents)
            Location.LeftLeg -> destroyLocationsComponent(llComponents)
            Location.RightLeg -> destroyLocationsComponent(rlComponents)
            Location.Head -> destroyLocationsComponent(hComponents)
        }
    }

    fun location(location: Location): Array<Pair<String, Boolean>?> {
        return when (location) {
            Location.LeftArm -> laComponents
            Location.RightArm -> raComponents
            Location.CenterTorso -> ctComponents
            Location.Head -> hComponents
            Location.LeftTorso -> ltComponents
            Location.RightTorso -> rtComponents
            Location.LeftLeg -> llComponents
            Location.RightLeg -> rlComponents
        }
    }

    private fun destroyLocationsComponent(array: Array<Pair<String, Boolean>?>) {
        for (i in array.indices) {
            val newText = array[i]!!.first
            array[i] = Pair(newText, false)
        }
    }

    private fun initComponent(array: Array<Pair<String, Boolean>?>) {
        for (i in array.indices) {
            val newText = MTF_EMPTY
            array[i] = Pair(newText, true)
        }
    }
}