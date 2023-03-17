package org.hyperskill.blackboard

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import io.ktor.utils.io.errors.*
import okhttp3.OkHttpClient
import okhttp3.Request
import org.hyperskill.blackboard.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {

    lateinit var mainBinding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mainBinding.root)
    }

}