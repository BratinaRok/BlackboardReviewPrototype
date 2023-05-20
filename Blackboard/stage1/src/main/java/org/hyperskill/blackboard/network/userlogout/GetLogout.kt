package org.hyperskill.blackboard.network.userlogout

import okhttp3.OkHttpClient
import okhttp3.Request
import org.hyperskill.blackboard.data.AuthToken
import org.hyperskill.blackboard.network.BaseClient

class GetLogout(private val client: OkHttpClient) {
    private val authToken: String? = AuthToken.Token.getCurrentToken()

    fun get(): Result<Any> {
        try {
            val request = Request.Builder()
                .url(BaseClient.baseurl + "logout")
                .apply {
                    if (authToken != null) {
                        header(
                            "Authorization",
                            "Bearer $authToken"
                        )   //when running tests change Bearer to token -> Bearer is for jwt ktor auth
                    }
                }
                .build()
            val response = client.newCall(request).execute()
            if (response.isSuccessful) {
                Result.success("Successful")
            } else {
                Result.failure(Exception("Error at logout"))
            }
        } catch (e: Exception) {
            println(e.printStackTrace())
        }
        return Result.failure(Exception("error"))
    }
}