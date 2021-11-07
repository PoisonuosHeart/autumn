package com.poison.poison

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.widget.AppCompatButton
import com.google.firebase.analytics.FirebaseAnalytics

class MainActivity : AppCompatActivity() {
    private val mFirebaseAnalytics = FirebaseAnalytics.getInstance(this)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViewById<AppCompatButton>(R.id.send).apply {
            setOnClickListener {
                mFirebaseAnalytics.logEvent("mishka_event",
                Bundle().apply {
                    putString("test", "mishka")
                })
            }
        }
    }
}