package com.shivamkumarjha.oneplusfirmware.persistence

import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.*

class FileCommon(private val file: File) {

    private val tag = "FileCommon"

    suspend fun fileExists(): Boolean =
        withContext(Dispatchers.Default) {
            if (!file.exists())
                return@withContext false
            return@withContext true
        }

    suspend fun readFromFile(): String? =
        withContext(Dispatchers.IO) {
            var jsonString: String? = null
            try {
                val inputStream: FileInputStream = file.inputStream()
                val inputStreamReader = InputStreamReader(inputStream)
                val bufferedReader = BufferedReader(inputStreamReader)
                jsonString = bufferedReader.use { it.readText() }
                inputStream.close()
            } catch (e: FileNotFoundException) {
                Log.e(tag, "File not found: $e")
            } catch (e: IOException) {
                Log.e(tag, "Can not read file: $e")
            }
            return@withContext jsonString
        }
}