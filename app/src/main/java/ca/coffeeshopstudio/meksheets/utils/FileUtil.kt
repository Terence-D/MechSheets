package ca.coffeeshopstudio.meksheets.utils

import android.content.Context
import ca.coffeeshopstudio.meksheets.models.Mech
import com.google.gson.Gson
import java.io.BufferedReader
import java.io.FileInputStream
import java.io.InputStreamReader
import java.util.*

class FileUtil {
    fun getJsonFiles(context: Context): List<Mech>? {
        val meks: MutableList<Mech> = ArrayList()
        val files = context.filesDir
        for (file in Objects.requireNonNull(files.listFiles())) {
            if (file.name.endsWith(".json")) {
                val mek = readFile(context, file.name)
                if (mek != null) meks.add(mek)
            }
        }
        return meks
    }

    private fun readFile(context: Context, fileName: String): Mech? {
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
    }}