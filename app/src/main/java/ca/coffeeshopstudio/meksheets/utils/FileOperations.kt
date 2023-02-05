package ca.coffeeshopstudio.meksheets.utils

import android.content.Context
import android.net.Uri
import ca.coffeeshopstudio.meksheets.data.Mech
import com.google.gson.Gson
import java.io.*
import java.util.*
import java.util.zip.ZipEntry
import java.util.zip.ZipInputStream

private const val MECH_ZIP = "mechs.zip"
private const val ZIP_LIST = "extractedMechs.txt"
private const val FILE_EXTENSION = ".jsn"

class FileOperations {
    fun writeToJson(context: Context, mech: Mech) {
        val gson = Gson()
        val outputStream: FileOutputStream

        if (mech.filename == "")
            mech.filename = mech.name + Calendar.getInstance().timeInMillis.toString() + FILE_EXTENSION

        val input = gson.toJson(mech)
        try {
            outputStream = context.openFileOutput(mech.filename, Context.MODE_PRIVATE)
            outputStream.write(input.toByteArray())
            outputStream.close()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun writeUriToInternal(context: Context, uri: Uri) {
        try {
            val inputStream: InputStream = context.contentResolver.openInputStream(uri)!!
            val fileOutputStream = context.openFileOutput(MECH_ZIP,  Context.MODE_PRIVATE)
            val buffer = ByteArray(1024)
            var length: Int
            while (inputStream.read(buffer).also { length = it } > 0) {
                fileOutputStream.write(buffer, 0, length)
            }
            fileOutputStream.flush()
            fileOutputStream.close()
            inputStream.close()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun findMechsInsideZip(context: Context, uri: Uri) {
        try {
            val inputStream: InputStream = context.contentResolver.openInputStream(uri)!!
            var zipInputStream: ZipInputStream
            var zipEntry: ZipEntry?
            var filename: String
            val files = context.filesDir

            for (file in files.listFiles()!!) {
                zipInputStream = ZipInputStream(BufferedInputStream(inputStream))

                while (zipInputStream.nextEntry.also { zipEntry = it } != null) {
                    filename = zipEntry!!.name
                    if (filename.endsWith("/$MECH_ZIP")) {
                        val buffer = ByteArray(1024)
                        var count: Int
                        val path = "${context.filesDir}${File.separator}$MECH_ZIP"
                        val fileOutputStreamDest = FileOutputStream(path)
                        while (zipInputStream.read(buffer).also { count = it } != -1) {
                            fileOutputStreamDest.write(buffer, 0, count)
                        }
                        fileOutputStreamDest.close()
                        zipInputStream.closeEntry()
                    }
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }


    private fun readFile(context: Context, fileName: String): Mech {
        val fis: FileInputStream
        val isr: InputStreamReader
        var mech = Mech()
        try {
            fis = context.openFileInput(fileName)
            isr = InputStreamReader(fis)
            mech = loadMech(isr)!!
            fis.close()
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
        }
        return mech
    }

    fun loadMech(isr: InputStreamReader): Mech? {
        val bufferedReader = BufferedReader(isr)
        val sb = StringBuilder()
        var line: String?
        while (bufferedReader.readLine().also { line = it } != null) {
            sb.append(line)
        }
        val json = sb.toString()
        val gson = Gson()
        return gson.fromJson(json, Mech::class.java)
    }

    fun deleteFile(context: Context, fileName: String?) {
        context.deleteFile(fileName)
    }

    fun getJsonFiles(context: Context): ArrayList<Mech> {
        val mechs: ArrayList<Mech> = ArrayList<Mech>()
        val files = context.filesDir
        for (file in files.listFiles()!!) {
            if (file.name.endsWith(FILE_EXTENSION)) {
                val mech: Mech = readFile(context, file.name)
                mechs.add(mech)
            }
        }
        return mechs
    }


    fun getFilesInZip(context: Context, filter:String): ArrayList<String> {
        val rv: ArrayList<String> = ArrayList()
        var zipInputStream: ZipInputStream
        var inputStream: InputStream
        var zipEntry: ZipEntry?
        var filename: String
        val files = context.filesDir

        for (file in files.listFiles()!!) {
            if (file.name == MECH_ZIP) {
                inputStream = FileInputStream(file)
                zipInputStream = ZipInputStream(BufferedInputStream(inputStream))

                while (zipInputStream.nextEntry.also { zipEntry = it } != null) {
                    filename = zipEntry!!.name
                    if (filename.contains(filter)) {
                        rv.add(filename)
                    }
                }
            }
        }
        return rv
    }

    fun writeArrayToInternal(context: Context, list: ArrayList<String>) {
        try {
            val writer = FileWriter("${context.filesDir}${File.pathSeparator}$ZIP_LIST")
            for (line in list) {
                writer.write("$line\n")
            }
            writer.close()
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    fun readArrayList(context: Context): ArrayList<String> {
        val list = ArrayList<String>()
        try {
            val reader = FileReader("${context.filesDir}${File.pathSeparator}$ZIP_LIST")
            reader.readLines().forEach() {
                list.add(it)
            }
            reader.close()
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return list
    }

    fun unzip(context: Context, fileToExtract: String): String {
        var zipInputStream: ZipInputStream
        var inputStream: InputStream
        var zipEntry: ZipEntry?
        var filename: String
        val files = context.filesDir
        var path = ""

        for (file in files.listFiles()!!) {
            if (file.name == MECH_ZIP) {
                inputStream = FileInputStream(file)
                zipInputStream = ZipInputStream(BufferedInputStream(inputStream))
                val buffer = ByteArray(1024)
                var count: Int

                while (zipInputStream.nextEntry.also { zipEntry = it } != null) {
                    filename = zipEntry!!.name
                    if (filename == fileToExtract) {
                        val simpleName = File(filename).name
                        path = "${context.cacheDir}${File.separator}$simpleName"
                        val fileOutputStream = FileOutputStream(path)
                        while (zipInputStream.read(buffer).also { count = it } != -1) {
                            fileOutputStream.write(buffer, 0, count)
                        }
                        fileOutputStream.close()
                        zipInputStream.closeEntry()
                    }
                }
            }
        }
        return path
    }
}