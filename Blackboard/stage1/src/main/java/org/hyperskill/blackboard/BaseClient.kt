package org.hyperskill.blackboard

import com.squareup.moshi.Moshi
import okhttp3.OkHttpClient

open class BaseClient(protected val client: OkHttpClient, protected val moshi: Moshi) {
    companion object {
        var baseurl = "http://10.0.2.2:8080/"
    }
}