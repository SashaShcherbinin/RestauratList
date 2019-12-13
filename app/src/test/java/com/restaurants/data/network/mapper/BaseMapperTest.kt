package com.restaurants.data.network.mapper

import com.google.gson.Gson
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.lang.reflect.Type

abstract class BaseMapperTest {

    fun <T> getJsonByResource(path: String, type: Type): T = Gson()
            .fromJson<T>(getJsonContents(path), type)

    open fun getJsonContents(filename: String?): String? {
        val inputStream =
            ClassLoader.getSystemResourceAsStream(filename)
        val reader =
            BufferedReader(InputStreamReader(inputStream))
        val stringBuilder = StringBuilder()
        var done = false
        try {
            while (!done) {
                val line = reader.readLine()
                done = line == null
                if (line != null) {
                    stringBuilder.append(line)
                }
            }
        } catch (e: IOException) {
            e.printStackTrace()
            return null
        } finally {
            try {
                reader.close()
                inputStream.close()
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
        return stringBuilder.toString()
    }
}