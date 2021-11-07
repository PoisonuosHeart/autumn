package com.poison.poison

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.widget.AppCompatButton
import com.google.firebase.analytics.FirebaseAnalytics
import com.poison.poison.common.Constants.PUSH_INTENT_FILTER
import com.poison.poison.common.Constants.PUSH_KEY_ACTION
import com.poison.poison.common.Constants.PUSH_KEY_MESSAGE
import com.poison.poison.common.Constants.PUSH_SHOW_MESSAGE

class MainActivity : AppCompatActivity() {
    private val mFirebaseAnalytics = FirebaseAnalytics.getInstance(this)
    private lateinit var pushBroadcastReceiver: BroadcastReceiver

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

        // push
        pushBroadcastReceiver = object : BroadcastReceiver() {
            override fun onReceive(p0: Context?, p1: Intent?) {
                val extras = p1?.extras

                extras?.keySet()?.firstOrNull {it == PUSH_KEY_ACTION}?.let{ key ->
                    if(extras.getString(key) == PUSH_SHOW_MESSAGE)
                        extras.getString(PUSH_KEY_MESSAGE)?.let{message ->
                            Toast.makeText(applicationContext, message, Toast.LENGTH_LONG).show()
                        }
                }
            }
        }

        val intentFilter = IntentFilter()
        intentFilter.addAction(PUSH_INTENT_FILTER)
        registerReceiver(pushBroadcastReceiver, intentFilter)
    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(pushBroadcastReceiver)
    }
}