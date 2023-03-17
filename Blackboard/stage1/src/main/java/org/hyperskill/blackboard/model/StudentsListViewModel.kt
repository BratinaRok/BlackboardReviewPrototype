package org.hyperskill.blackboard.model

import android.content.Context
import androidx.lifecycle.ViewModel
import io.ktor.utils.io.errors.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.Request
import org.hyperskill.blackboard.BlackboardApplication
import org.hyperskill.blackboard.data.AuthToken

class StudentsListViewModel(context: Context) : ViewModel() {

    private val client = (context.applicationContext as BlackboardApplication).blackboardClient
    private var responseData: String? = null
    private val authToken: String? = AuthToken.Token.getCurrentToken()


    fun getList(): String? {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val request = Request.Builder()
                    .url("http://10.0.2.2:8080/students")
                    .apply {
                        if (authToken != null) {
                            header("Authorization", "Bearer $authToken")
                        }
                    }
                    .build()

                client.newCall(request).execute().use { response ->
                    if (!response.isSuccessful) {
                        throw IOException("Unsuccessful $response")
                    }
                    responseData = response.body?.string()
                }

            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
        return responseData
    }
}
