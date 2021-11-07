package com.poison.poison.common.notification

import android.content.Intent
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.poison.poison.common.Constants.PUSH_INTENT_FILTER

class PushService : FirebaseMessagingService() {
    override fun onNewToken(newToken: String) {
        super.onNewToken(newToken)

    }

    override fun onMessageReceived(remotemessage: RemoteMessage) {
        super.onMessageReceived(remotemessage)

        val intent = Intent(PUSH_INTENT_FILTER)

        remotemessage.data.forEach{ entity ->
            intent.putExtra(entity.key, entity.value)
        }

        sendBroadcast(intent)
    }
}