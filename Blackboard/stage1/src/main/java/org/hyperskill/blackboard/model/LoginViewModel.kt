package org.hyperskill.blackboard.model

import android.app.Application
import androidx.lifecycle.*
import io.ktor.utils.io.errors.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import org.hyperskill.blackboard.BaseClient
import org.hyperskill.blackboard.BlackboardApplication
import org.json.JSONObject

class LoginViewModel(application: Application) : AndroidViewModel(application) {

    private val _loginResult = MutableLiveData<LoginResult>()
    val loginResult: LiveData<LoginResult>
        get() = _loginResult
    private lateinit var credential: String
    private val client =
        (application as BlackboardApplication).blackboardClient

    fun login(username: String, password: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val jsonPayload = createLoginPayload(username, password)
            try {
                credential = Credentials.basic(username, password)
                val requestBody =
                    RequestBody.create("application/json".toMediaTypeOrNull(), jsonPayload)

                client.newBuilder().authenticator(object : Authenticator {
                    @Throws(IOException::class)
                    override fun authenticate(route: Route?, response: Response): Request? {
                        if (response.request.header("Authorization") != null) {
                            return null
                        }
                        return response.request.newBuilder()
                            .header("Authorization", credential).build()
                    }
                }).build()

                val request = Request.Builder().url(BaseClient.baseurl + "login")
                    .header("Authorization", credential).post(requestBody).build()


                val response = client.newCall(request).execute()
                val isAuthenticationSuccessful = response.isSuccessful

                if (isAuthenticationSuccessful) {
                    val responseBody = response.body?.string()
                    val json = responseBody?.let { JSONObject(it) }
                    val role = json?.getString("role")
                    val token = json?.getString("token")
                    withContext(Dispatchers.Main) {
                        _loginResult.value =
                            LoginResult(isAuthenticationSuccessful, role, token)
                    }
                } else {
                    withContext(Dispatchers.Main) {
                        _loginResult.value = LoginResult(false, null, null)
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    private fun createLoginPayload(username: String, password: String): String {
        val json = JSONObject()
        json.put("username", username)
        json.put("pass", password)
        return json.toString()
    }

}

data class LoginResult(val success: Boolean, val role: String?, val token: String?) {

}