package org.hyperskill.blackboard.network.student.get;

import io.ktor.utils.io.errors.*
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonArray
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.decodeFromJsonElement
import okhttp3.OkHttpClient
import org.hyperskill.blackboard.data.AuthToken;
import org.hyperskill.blackboard.network.BaseClient;

import kotlin.Result;
import okhttp3.Request;
import org.hyperskill.blackboard.data.Grades

class StudentGrades(val client: OkHttpClient) {
    private val authToken: String? = AuthToken.Token.getCurrentToken()
    private lateinit var grades: Any
    fun getGrades(username: String): Result<Any> {
        return try {
            val request = Request.Builder()
                .url(BaseClient.baseurl + "student/$username/grade")
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
                val responseData = response.body?.string()
                if (responseData != null) {
                    val jsonElement = Json.parseToJsonElement(responseData)
                   return when(jsonElement) {
                        is JsonArray -> Result.success(Json.decodeFromString<List<Grades>>(responseData))
                        is JsonObject -> Result.success(Json.decodeFromJsonElement<Grades>(jsonElement))
                       else ->  Result.failure(Exception("No parsable json data"))
                   }
                } else {
                    Result.failure(Exception("Empty response"))
                }
            } else {
                Result.failure(IOException("Unsuccessful ${response.code}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}

