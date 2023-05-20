package org.hyperskill.blackboard.model

import android.app.Application
import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.lifecycle.*
import io.ktor.utils.io.errors.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.internal.wait
import org.hyperskill.blackboard.network.BaseClient
import org.hyperskill.blackboard.BlackboardApplication
import org.hyperskill.blackboard.data.User
import org.json.JSONObject
import java.nio.charset.StandardCharsets
import java.security.MessageDigest
import java.util.*
import java.util.concurrent.TimeUnit

class LoginViewModel(application: Application) : AndroidViewModel(application) {

    private val _loginResult = MutableLiveData<LoginResult>()
    val loginResult: LiveData<LoginResult>
        get() = _loginResult
    private lateinit var credential: String
    private val client =
        (application as BlackboardApplication).blackboardClient

    @RequiresApi(Build.VERSION_CODES.O)
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
                })
                    .build()

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
                            LoginResult.Success(username, token, role)
                        User.username = username
                    }
                } else {
                    withContext(Dispatchers.Main) {
                        _loginResult.value = LoginResult.Failure(errorMsg = "Wrong credentials")
                    }
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    println(e.printStackTrace())
                    _loginResult.value = LoginResult.Failure("Server error")
                    e.printStackTrace()
                }
            }
        }


    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun createLoginPayload(username: String, password: String): String {
        val json = JSONObject()
        json.put("username", username)
        val rawPassBytes = password.toByteArray(StandardCharsets.UTF_8)
        val messageDigest = MessageDigest.getInstance("SHA-256")
        val sha256HashPass = messageDigest.digest(rawPassBytes)
        val base64sha256HashPass = Base64.getEncoder().encodeToString(sha256HashPass)
        json.put("pass", base64sha256HashPass)
        return json.toString()
    }

}

sealed class LoginResult {
    data class Success(val username: String?, val token: String?, val role: String?) :
        LoginResult() {
        val success: Boolean
            get() = username != null && token != null && role != null
    }

    data class Failure(val errorMsg: String) : LoginResult() {
        val error: String
            get() = errorMsg
    }
}


