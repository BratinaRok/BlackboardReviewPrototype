package org.hyperskill.blackboard

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import org.hyperskill.blackboard.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {

    lateinit var mainBinding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mainBinding.root)
        BaseClient.baseurl = intent.extras?.getString("baseUrl") ?: BaseClient.baseurl
        println("baseUrl : ${BaseClient.baseurl}")
    }

}