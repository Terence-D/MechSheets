package ca.coffeeshopstudio.meksheets.viewmodels

import android.content.Context
import android.net.Uri
import androidx.collection.ArrayMap
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ca.coffeeshopstudio.meksheets.models.Mech
import ca.coffeeshopstudio.meksheets.repositories.MechRepository
import ca.coffeeshopstudio.meksheets.utils.FileOperations
import com.google.gson.Gson
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.io.*
import java.util.*
import kotlin.collections.ArrayList

data class MechState(
    val firstDieValue: Int? = null,
    val secondDieValue: Int? = null,
    val numberOfRolls: Int = 0,
)

class MechViewModel : ViewModel() {

    val mechList: MutableLiveData<List<Mech>> by lazy {
        MutableLiveData<List<Mech>>()
    }
    val mechSet: MutableLiveData<ArrayMap<String, Mech>> by lazy {
        MutableLiveData<ArrayMap<String, Mech>>()
    }

    fun init(context: Context)  {
        CoroutineScope(Dispatchers.IO).launch {
            loadJson(context)
        }
    }

    fun addMech(uri: Uri, context: Context): String? {
        val inputStream: InputStream? = context.contentResolver.openInputStream(uri)
        val reader = BufferedReader(InputStreamReader(inputStream))

        val mech = Mech()
        try {
            mech.readMTF(reader)
            FileOperations.writeFile(context, mech)
            loadJson(context)
        } catch (ioe: IOException) {
            return ioe.localizedMessage
        }
        return null
    }

    private fun loadJson(context: Context) {
        val files = context.filesDir
        val list: ArrayList<Mech> = ArrayList()
        val set: ArrayMap<String, Mech>  = ArrayMap()
        for (file in Objects.requireNonNull(files?.listFiles())) {
            if (file.name.endsWith(".json")) {
                val mech = readFile(file.name, context)
                if (mech != null) {
                    list.add(mech)
                    set[mech.name] = mech
                }
            }
        }
        mechList.postValue(list)
        mechSet.postValue(set)
    }

    private fun readFile(fileName: String, context: Context): Mech? {
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
            gson.fromJson(json, Mech::class.java)
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }


    //private val _uiState = MutableStateFlow(MechState())
    //val uiState: StateFlow<MechState> = _uiState.asStateFlow()

    // Handle business logic
//    fun rollDice() {
//        _uiState.update { currentState ->
//            currentState.copy(
//                numberOfRolls = 1,
//            )
//        }
//    }
}