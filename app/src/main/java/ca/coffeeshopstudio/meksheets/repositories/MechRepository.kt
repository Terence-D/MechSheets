package ca.coffeeshopstudio.meksheets.repositories

import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.collection.ArrayMap
import ca.coffeeshopstudio.meksheets.models.Mech
import ca.coffeeshopstudio.meksheets.utils.FileOperations
import ca.coffeeshopstudio.meksheets.views.ActivityMain
import ca.coffeeshopstudio.meksheets.views.FragmentOverview
import com.google.gson.Gson
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.*
import java.util.*

object MechRepository {
//    val MECH_LIST: ArrayList<Mech> = ArrayList()
//    val MECH_MAP: ArrayMap<String, Mech> = ArrayMap()

//    fun init(context: Context)  {
//        CoroutineScope(Dispatchers.IO).launch {
//            loadJson(context)
//        }
//    }
//
//    private fun loadJson(context: Context) {
//        val files = context.filesDir
//        MECH_LIST.clear()
//        MECH_MAP.clear()
//        for (file in Objects.requireNonNull(files?.listFiles())) {
//            if (file.name.endsWith(".json")) {
//                val mech = readFile(file.name, context)
//                if (mech != null) {
//                    MECH_LIST.add(mech)
//                    MECH_MAP[mech.name] = mech
//                }
//            }
//        }
//    }
//
//    private fun readFile(fileName: String, context: Context): Mech? {
//        val fis: FileInputStream
//        return try {
//            fis = context.openFileInput(fileName)
//            val isr = InputStreamReader(fis)
//            val bufferedReader = BufferedReader(isr)
//            val sb = StringBuilder()
//            var line: String?
//            while (bufferedReader.readLine().also { line = it } != null) {
//                sb.append(line)
//            }
//            val json = sb.toString()
//            val gson = Gson()
//            gson.fromJson(json, Mech::class.java)
//        } catch (e: Exception) {
//            e.printStackTrace()
//            null
//        }
//    }
//
//    fun addMech(uri: Uri, context: Context): String? {
//        val inputStream: InputStream? = context.contentResolver.openInputStream(uri)
//        val reader = BufferedReader(InputStreamReader(inputStream))
//
//        val mech = Mech()
//        try {
//            mech.readMTF(reader)
//            FileOperations.writeFile(context, mech)
//            loadJson(context)
//        } catch (ioe: IOException) {
//            return ioe.localizedMessage
//        }
//        return null
//    }
}