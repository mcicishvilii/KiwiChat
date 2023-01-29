package com.example.kiwichat

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.kiwichat.databinding.ActivityMainBinding
import com.google.firebase.BuildConfig
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // When running in debug mode, connect to the Firebase Emulator Suite.
// "10.0.2.2" is a special IP address which allows the Android Emulator
// to connect to "localhost" on the host computer. The port values (9xxx)
// must match the values defined in the firebase.json file.
        if (BuildConfig.DEBUG) {
            Firebase.database.useEmulator("10.0.2.2", 4500)
            Firebase.auth.useEmulator("10.0.2.2", 4500)
            Firebase.storage.useEmulator("10.0.2.2", 4500)
        }

    }
}