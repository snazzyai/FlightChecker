package data

import android.util.Log
import java.net.URL

open class RequestApi(private val url: String) {

    fun makeRequest(): String {
        val data = URL(url).readText()
        return data.toString()


    }


}