package org.hyperskill.blackboard.model

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.hyperskill.blackboard.BlackboardApplication
import org.hyperskill.blackboard.network.userlogout.GetLogout

class LogoutViewModel(application: Application) : AndroidViewModel(application) {
    private val client = (application as BlackboardApplication).blackboardClient

    fun getLogout() {
        viewModelScope.launch(Dispatchers.IO) {
            val logoutCall = GetLogout(client).get()
            if (logoutCall.isSuccess) {

            } else if (logoutCall.isFailure) {
                println("Error logout")
            }
        }
    }

}