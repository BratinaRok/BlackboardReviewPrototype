package org.hyperskill.blackboard.model

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.ktor.utils.io.errors.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.Request
import org.hyperskill.blackboard.BlackboardApplication
import org.hyperskill.blackboard.data.AuthToken

class StudentsListViewModel(application: Application) : AndroidViewModel(application) {

    private val client = (application as BlackboardApplication).blackboardClient
    private var responseData: String? = null
    private val authToken: String? = AuthToken.Token.getCurrentToken()


    fun getList(): String? {
        viewModelScope.launch(Dispatchers.IO) {
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
