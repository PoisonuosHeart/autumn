package com.poison.poison

import android.app.Application
import android.os.Bundle
import android.util.Log
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.messaging.FirebaseMessaging
import com.poison.poison.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.GlobalContext.startKoin

class App : Application() {
    private lateinit var mFirebaseAnalytics: FirebaseAnalytics
    override fun onCreate() {
        super.onCreate()

        startKoin {
            // Koin Android logger
            androidLogger()
            // inject Android context
            androidContext(this@App)
            // use modules
            modules(appModule)
        }

        // Analytics
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this)

        mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.LOGIN,
            Bundle().apply{
                putString(FirebaseAnalytics.Param.METHOD, "Test")
            }
        )

        // Push
        FirebaseMessaging.getInstance().token.addOnCompleteListener { task ->
            if(!task.isSuccessful)
                return@addOnCompleteListener
            Log.w("push: ", "token: ${task.result}")
        }
    }
}