package data

import java.net.URL

class RequestApi(private val url: String) {

    fun makeRequest(): String {
        return URL(url).readText()
    }


}