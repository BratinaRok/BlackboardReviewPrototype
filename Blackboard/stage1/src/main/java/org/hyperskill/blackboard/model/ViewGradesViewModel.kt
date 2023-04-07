package org.hyperskill.blackboard.model

import android.app.Application
import androidx.lifecycle.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.Callback
import okhttp3.Dispatcher
import okhttp3.OkHttpClient
import org.hyperskill.blackboard.BlackboardApplication
import org.hyperskill.blackboard.data.Grades
import org.hyperskill.blackboard.network.student.GetStudentGrades
import org.hyperskill.blackboard.ui.student.ViewGradesFragment

class ViewGradesViewModel(application: Application) : AndroidViewModel(application) {
    private val client = (application as BlackboardApplication).blackboardClient
    val _grades = MutableLiveData<Result<String>>()
    val grades: LiveData<Result<String>> = _grades
    private var userGrades: List<Grades> = emptyList()

    fun getGrades(username: String, callback: (List<Grades>) -> Unit) {
        viewModelScope.launch(Dispatchers.IO) {
            val getGrades = GetStudentGrades(client).getGrades(username)
            if (getGrades.isSuccess) {
                val grades = getGrades.getOrNull() as List<Grades>
                withContext(Dispatchers.Main) {
                    userGrades = grades
                    callback(grades)
                }
            } else {
                println(getGrades)
            }
        }
    }
}