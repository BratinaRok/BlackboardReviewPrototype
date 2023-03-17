package org.hyperskill.blackboard.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.ktor.utils.io.errors.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import org.json.JSONObject

class LoginViewModel : ViewModel() {
    private val _loginResult = MutableLiveData<LoginResult>()
    val loginResult: LiveData<LoginResult>
        get() = _loginResult
    private lateinit var credential: String

    fun login(username: String, password: String) {
        val jsonPayload = createLoginPayload(username, password)

        CoroutineScope(Dispatchers.IO).launch {
            try {
                credential = Credentials.basic(username, password)
                val requestBody =
                    RequestBody.create("application/json".toMediaTypeOrNull(), jsonPayload)

                val client = OkHttpClient.Builder().authenticator(object : Authenticator {
                    @Throws(IOException::class)
                    override fun authenticate(route: Route?, response: Response): Request? {
                        if (response.request.header("Authorization") != null) {
                            return null
                        }
                        return response.request.newBuilder()
                            .header("Authorization", credential).build()
                    }
                }).build()

                val request = Request.Builder().url("http://10.0.2.2:8080/login")
                    .header("Authorization", credential).post(requestBody).build()

                val response = client.newCall(request).execute()
                val isAuthenticationSuccessful = response.isSuccessful

                if (isAuthenticationSuccessful) {
                    val responseBody = response.body?.string()
                    val json = responseBody?.let { JSONObject(it) }
                    val role = json?.getString("role")
                    val token = json?.getString("token")
                    withContext(Dispatchers.Main) {
                        _loginResult.value = LoginResult(isAuthenticationSuccessful, role, token)
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
        json.put("password", password)
        return json.toString()
    }

}

data class LoginResult(val success: Boolean, val role: String?, val token: String?) {

}