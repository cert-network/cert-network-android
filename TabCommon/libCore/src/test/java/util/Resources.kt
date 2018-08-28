package util

import com.google.gson.Gson

import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.util.stream.Collectors

object Resources {

    fun <T> loadJsonResource(resourceName: String, classOfT: Class<T>): T {
        try {
            InputStreamReader(ClassLoader.getSystemClassLoader().getResourceAsStream(resourceName)).use { `in` ->
                val json = BufferedReader(`in`)
                        .lines()
                        .collect(Collectors.joining("\n"))
                return Gson().fromJson(json, classOfT)
            }
        } catch (e: IOException) {
            throw RuntimeException(e)
        }

    }
}
