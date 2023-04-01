package org.hyperskill.blackboard.model

import android.app.Application
import androidx.lifecycle.*
import io.ktor.utils.io.errors.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import okhttp3.Request
import org.hyperskill.blackboard.BlackboardApplication
import org.hyperskill.blackboard.data.AuthToken
import org.hyperskill.blackboard.data.Student
import org.hyperskill.blackboard.ui.teacher.studentlistdata.StudentsCallback

class StudentsListViewModel(application: Application) : AndroidViewModel(application) {

    private val client = (application as BlackboardApplication).blackboardClient
    private var responseData: String? = null
    private val authToken: String? = AuthToken.Token.getCurrentToken()
    private val _students = MutableLiveData<List<Student>>()
    val students: LiveData<List<Student>> = _students
    private var callback: StudentsCallback? = null

    fun getList(callback: StudentsCallback): String? {
        this.callback = callback
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
                    if (responseData != null) {
                        val studentsList = Json.decodeFromString<List<Student>>(responseData!!)
                        callback.onStudentsLoaded(studentsList)
                        withContext(Dispatchers.Main) {
                            _students.value = studentsList
                        }
                    }
                }

            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
        return responseData
    }
}
