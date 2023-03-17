package org.hyperskill.blackboard

import android.app.Application
import okhttp3.OkHttpClient


class BlackboardApplication : Application() {

    val blackboardClient by lazy {
        OkHttpClient()
    }
}